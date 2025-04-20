package jp.co.zaico.codingtest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.zaico.codingtest.InvtyListUiEvent.*
import jp.co.zaico.codingtest.data.repository.InventoryRepository
import jp.co.zaico.codingtest.model.Inventory
import jp.co.zaico.codingtest.usecase.GetInventoriesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.String

data class InvtySearchQuery(
    val title: String? = null,
    val category: String? = null,
    val place: String? = null,
    val code: String? = null,
)

data class InvtyListUiState(
    val isLoading: Boolean = false,
    val inventoryList: List<Inventory> = emptyList(),
    val error: String? = null,
    val searchQuery: InvtySearchQuery = InvtySearchQuery(),
)

sealed class InvtyListEvent {
    data object LoadInvtyList : InvtyListEvent()
    data class UpdateQuery(val searchQuery: InvtySearchQuery) : InvtyListEvent()
    data object ClearSearchQuery : InvtyListEvent()
}

sealed class InvtyListUiEvent {
    data class ShowToast(val message: String) : InvtyListUiEvent()
}

@HiltViewModel
class InvtyListViewModel @Inject constructor(
    private val getInventoriesUseCase: GetInventoriesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(InvtyListUiState())
    val uiState: StateFlow<InvtyListUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<InvtyListUiEvent>(replay = 1)
    val uiEvent: SharedFlow<InvtyListUiEvent> = _uiEvent.asSharedFlow()

    fun onEvent(event: InvtyListEvent, scope: CoroutineScope = viewModelScope) {
        when (event) {
            is InvtyListEvent.LoadInvtyList -> {
                scope.launch {
                    _uiState.update {
                        it.copy(
                            isLoading = true,
                            error = null,
                        )
                    }

                    getInventoriesUseCase(
                        title = uiState.value.searchQuery.title,
                        category = uiState.value.searchQuery.category,
                        place = uiState.value.searchQuery.place,
                        code = uiState.value.searchQuery.code)
                        .collect {
                            it.fold(
                                onSuccess = { inventoryList ->
                                    _uiState.update {
                                        it.copy(isLoading = false, inventoryList = inventoryList)
                                    }
                                },
                                onFailure = { exception ->
                                    _uiState.update {
                                        it.copy(isLoading = false, error = exception.message)
                                    }
                                    _uiEvent.emit(
                                        ShowToast(
                                            exception.message ?: "Unknown error"
                                        )
                                    )
                                }
                            )
                        }
                }
            }

            is InvtyListEvent.UpdateQuery -> {
                _uiState.update {
                    val current = it.searchQuery
                    val incoming = event.searchQuery

                    val merged = current.copy(
                        title = incoming.title ?: current.title,
                        category = incoming.category ?: current.category,
                        place = incoming.place ?: current.place,
                        code = incoming.code ?: current.code,
                    )

                    it.copy(searchQuery = merged)
                }
            }

            is InvtyListEvent.ClearSearchQuery -> {
                _uiState.update {
                    it.copy(
                        isLoading = true,
                        error = null,
                        searchQuery = InvtySearchQuery()
                    )
                }

                onEvent(InvtyListEvent.LoadInvtyList, scope)
            }
        }
    }
}