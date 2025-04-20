package jp.co.zaico.codingtest

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.zaico.codingtest.data.repository.InventoryRepository
import jp.co.zaico.codingtest.model.Inventory
import jp.co.zaico.codingtest.model.InventoryInput
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface InvtyEditCreateUiState {
    object Loading : InvtyEditCreateUiState
    data class Saving(val inventory: InventoryInput) : InvtyEditCreateUiState
    data class Editor(val inventory: InventoryInput) : InvtyEditCreateUiState
    data class Saved(val inventory: Inventory) : InvtyEditCreateUiState
    data class Warning(val inventory: InventoryInput, val message: String) : InvtyEditCreateUiState
    data class Error(val message: String) : InvtyEditCreateUiState
}

sealed class InvtyEditCreateUiEvent {
    data object LoadInvty : InvtyEditCreateUiEvent()
    data class EditInvty(val inventory: InventoryInput) : InvtyEditCreateUiEvent()
    data class SaveInvty(val inventory: InventoryInput) : InvtyEditCreateUiEvent()
}

@HiltViewModel
class InvtyEditCreateViewModel @Inject constructor(
    private val inventoryRepository: InventoryRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val inventoryId: String? = savedStateHandle["id"]

    private val _uiState = MutableStateFlow<InvtyEditCreateUiState>(InvtyEditCreateUiState.Loading)
    val uiState: StateFlow<InvtyEditCreateUiState> = _uiState.asStateFlow()

    fun onEvent(event: InvtyEditCreateUiEvent, scope: CoroutineScope = viewModelScope) {
        when (event) {
            is InvtyEditCreateUiEvent.LoadInvty -> {
                scope.launch {
                    _uiState.update { InvtyEditCreateUiState.Loading }
                    if (inventoryId == null) {
                        _uiState.value = InvtyEditCreateUiState.Editor(InventoryInput(title = ""))
                    } else {
                        try {
                            val item = inventoryRepository.getInventory(inventoryId)
                            item.fold(
                                onSuccess = {
                                    val inventoryInput = inventoryToInventoryInput(it)
                                    _uiState.value = InvtyEditCreateUiState.Editor(inventoryInput)
                                },
                                onFailure = {
                                    _uiState.value =
                                        InvtyEditCreateUiState.Error(it.message ?: "Unknown error")
                                }
                            )
                        } catch (e: Exception) {
                            _uiState.value =
                                InvtyEditCreateUiState.Error(e.message ?: "Unknown error")
                        }
                    }
                }
            }

            is InvtyEditCreateUiEvent.EditInvty -> {
                _uiState.update { InvtyEditCreateUiState.Editor(event.inventory) }
            }

            is InvtyEditCreateUiEvent.SaveInvty -> {
                scope.launch {
                    val inventoryInput = event.inventory
                    _uiState.update { InvtyEditCreateUiState.Saving(inventoryInput) }
                    try {
                        val item = if (inventoryId == null) {
                            inventoryRepository.createInventory(inventoryInput)
                        } else {
                            inventoryRepository.editInventory(inventoryId, inventoryInput)
                        }
                        item.fold(
                            onSuccess = {
                                _uiState.value = InvtyEditCreateUiState.Saved(it)
                            },
                            onFailure = {
                                _uiState.value =
                                    InvtyEditCreateUiState.Warning(inventoryInput, it.message ?: "Unknown error")
                            }
                        )
                    } catch (e: Exception) {
                        _uiState.value =
                            InvtyEditCreateUiState.Error(e.message ?: "Unknown error")

                    }
                }
            }
        }
    }

    private fun inventoryToInventoryInput(inventory: Inventory): InventoryInput {
        return InventoryInput(
            title = inventory.title,
            quantity = inventory.quantity,
            unit = inventory.unit,
            category = inventory.category,
            place = inventory.category,
            state = inventory.state,
            code = inventory.category
        )
    }
}