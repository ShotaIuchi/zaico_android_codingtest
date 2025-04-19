package jp.co.zaico.codingtest

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import jp.co.zaico.codingtest.ui.parts.InvtyDetailItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvtyDetailScreen(
    viewModel: InvtyDetailViewModel = hiltViewModel(),
    onEvent: (AppNavEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.onEvent(InvtyDetailEvent.LoadInvty)
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(context.getString(R.string.invty_detail_screen)) },
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
                    is InvtyDetailUiState.Success -> {
                        val inventory = (uiState as InvtyDetailUiState.Success).inventory
                        onEvent(AppNavEvent.ToEdit(inventory.id.toString()))
                    }
                    else -> {}
                }
            }) {
                Icon(Icons.Default.Edit, contentDescription = "Add")
            }
        }
    ) { padding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(padding)) {
            when (uiState) {
                is InvtyDetailUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is InvtyDetailUiState.Success -> {
                    val inventory = (uiState as InvtyDetailUiState.Success).inventory
                    InvtyDetailItem(inventory = inventory)
                }

                is InvtyDetailUiState.Error -> {
                    onEvent(AppNavEvent.Back)
                }
            }
        }
    }

}

