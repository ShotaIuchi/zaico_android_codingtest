package jp.co.zaico.codingtest

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun InvtyListScreen(onEvent: (AppNavEvent) -> Unit, modifier: Modifier = Modifier) {
    Column {
        Button(onClick = { onEvent(AppNavEvent.ToDetail("1")) }) {
            Text(
                text = "Go To Detail Screen",
                modifier = modifier
            )
        }
        Button(onClick = { onEvent(AppNavEvent.ToCreate) }) {
            Text(
                text = "Go To Create Screen",
                modifier = modifier
            )
        }
        Button(onClick = { onEvent(AppNavEvent.ToEdit("2")) }) {
            Text(
                text = "Go To Edit Screen",
                modifier = modifier
            )
        }
    }
}