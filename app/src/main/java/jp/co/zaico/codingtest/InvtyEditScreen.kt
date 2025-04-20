package jp.co.zaico.codingtest

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvtyEditScreen(onEvent: (AppNavEvent) -> Unit, modifier: Modifier = Modifier) {
    InvtyEditScreenImpl(title = R.string.invty_edit_screen, onEvent = onEvent, modifier = modifier)
}