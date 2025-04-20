package jp.co.zaico.codingtest

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun InvtyCreateScreen(onEvent: (AppNavEvent) -> Unit, modifier: Modifier = Modifier) {
    InvtyEditScreenImpl(title = R.string.invty_create_screen, onEvent = onEvent, modifier = modifier)
}
