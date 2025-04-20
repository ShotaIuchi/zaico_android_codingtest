package jp.co.zaico.codingtest


import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import jp.co.zaico.codingtest.model.InventoryInput
import jp.co.zaico.codingtest.ui.parts.InvtyDetailItem
import jp.co.zaico.codingtest.ui.parts.InvtyEditItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvtyEditScreenImpl(
    title: Int,
    viewModel: InvtyEditCreateViewModel = hiltViewModel(),
    onEvent: (AppNavEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var inventory by remember { mutableStateOf<InventoryInput?>(null) }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.onEvent(InvtyEditCreateUiEvent.LoadInvty)
    }

    LaunchedEffect(uiState) {
        if (uiState is InvtyEditCreateUiState.Editor) {
            inventory = (uiState as InvtyEditCreateUiState.Editor).inventory
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(context.getString(title)) },
                navigationIcon = {
                    IconButton(onClick = { onEvent(AppNavEvent.Back) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                when (uiState) {
                    is InvtyEditCreateUiState.Editor -> {
                        inventory?.let {
                            viewModel.onEvent(InvtyEditCreateUiEvent.SaveInvty(inventory = it))
                        }
                    }
                    else -> {}
                }
            }) {
                Icon(Icons.Default.Check, contentDescription = "Add")
            }
        }
    ) { padding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(padding)) {
            when (uiState) {
                is InvtyEditCreateUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is InvtyEditCreateUiState.Saving -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is InvtyEditCreateUiState.Editor -> {
                    inventory?.let {
                        InvtyEditItem(
                            inventory = it,
                            onValueChange = {
                                inventory = it
                            }
                        )
                    }
                }

                is InvtyEditCreateUiState.Saved -> {
                    Toast.makeText(context, "保存しました。", Toast.LENGTH_SHORT).show()
                    onEvent(AppNavEvent.Back)
                }

                is InvtyEditCreateUiState.Warning -> {
                    Toast.makeText(context, "保存できませんでした。", Toast.LENGTH_SHORT).show()
                    inventory?.let {
                        viewModel.onEvent(InvtyEditCreateUiEvent.EditInvty(it))
                    } ?: {
                        onEvent(AppNavEvent.Back)
                    }
                }

                is InvtyEditCreateUiState.Error -> {
                    Toast.makeText(context, "保存できませんでした。エラーが発生しました。", Toast.LENGTH_SHORT).show()
                    onEvent(AppNavEvent.Back)
                }
            }
        }
    }
}