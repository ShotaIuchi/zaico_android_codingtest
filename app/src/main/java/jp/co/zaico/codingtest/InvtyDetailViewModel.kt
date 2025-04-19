package jp.co.zaico.codingtest

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.zaico.codingtest.data.repository.InventoryRepository
import jp.co.zaico.codingtest.model.Inventory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface InvtyDetailUiState {
    object Loading : InvtyDetailUiState
    data class Success(val inventory: Inventory) : InvtyDetailUiState
    data class Error(val message: String) : InvtyDetailUiState
}

sealed class InvtyDetailEvent {
    data object LoadInvty : InvtyDetailEvent()
}

@HiltViewModel
class InvtyDetailViewModel @Inject constructor(
    private val inventoryRepository: InventoryRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val inventoryId: String = checkNotNull(savedStateHandle["id"])

    private val _uiState = MutableStateFlow<InvtyDetailUiState>(InvtyDetailUiState.Loading)
    val uiState: StateFlow<InvtyDetailUiState> = _uiState.asStateFlow()

    fun onEvent(event: InvtyDetailEvent, scope: CoroutineScope = viewModelScope) {
        when (event) {
            is InvtyDetailEvent.LoadInvty -> {
                scope.launch {
                    _uiState.update { InvtyDetailUiState.Loading }
                    try {
                        val item = inventoryRepository.getInventory(inventoryId)
                        item.fold(
                            onSuccess = {
                                _uiState.value = InvtyDetailUiState.Success(it)
                            },
                            onFailure = {
                                _uiState.value =
                                    InvtyDetailUiState.Error(it.message ?: "Unknown error")
                            }
                        )
                    } catch (e: Exception) {
                        _uiState.value = InvtyDetailUiState.Error(e.message ?: "Unknown error")
                    }
                }
            }
        }
    }
}