package jp.co.zaico.codingtest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.zaico.codingtest.data.repository.InventoryRepository
import jp.co.zaico.codingtest.model.Inventory
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

data class InvtyListUiState(
    val isLoading: Boolean = false,
    val inventoryList: List<Inventory> = emptyList(),
    val error: String? = null
)

sealed class InvtyListEvent {
    data object LoadInvtyList : InvtyListEvent()
}

sealed class InvtyListUiEvent {
    data class ShowToast(val message: String) : InvtyListUiEvent()
}

@HiltViewModel
class InvtyListViewModel @Inject constructor(
    private val inventoryRepository: InventoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(InvtyListUiState())
    val uiState: StateFlow<InvtyListUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<InvtyListUiEvent>(replay = 1)
    val uiEvent: SharedFlow<InvtyListUiEvent> = _uiEvent.asSharedFlow()

    fun onEvent(event: InvtyListEvent, scope: CoroutineScope = viewModelScope) {
        when (event) {
            is InvtyListEvent.LoadInvtyList -> {
                scope.launch {
                    _uiState.update { it.copy(isLoading = true, error = null) }

                    inventoryRepository.getInventories()
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
                                        InvtyListUiEvent.ShowToast(
                                            exception.message ?: "Unknown error"
                                        )
                                    )
                                }
                            )
                        }
                }
            }
        }
    }
}