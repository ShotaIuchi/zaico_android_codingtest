package jp.co.zaico.codingtest

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun InvtyEditScreen(id: String, onEvent: (AppNavEvent) -> Unit, modifier: Modifier = Modifier) {
    Button(onClick = { onEvent(AppNavEvent.Back) }) {
        Text(
            text = "($id) Go To Back",
            modifier = modifier
        )
    }
}