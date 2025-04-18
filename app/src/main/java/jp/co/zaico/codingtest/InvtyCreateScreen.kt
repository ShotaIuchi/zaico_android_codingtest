package jp.co.zaico.codingtest

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun InvtyCreateScreen(onEvent: (AppNavEvent) -> Unit, modifier: Modifier = Modifier) {
    InvtyEditScreen(id = "-1", onEvent = onEvent, modifier = modifier)
}