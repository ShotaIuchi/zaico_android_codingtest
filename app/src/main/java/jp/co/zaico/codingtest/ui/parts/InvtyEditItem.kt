package jp.co.zaico.codingtest.ui.parts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import jp.co.zaico.codingtest.InvtyEditCreateUiState
import jp.co.zaico.codingtest.InvtyEditCreateViewModel
import jp.co.zaico.codingtest.model.Inventory
import jp.co.zaico.codingtest.model.InventoryInput

@Composable
fun InvtyEditItem(
    inventory: InventoryInput,
    onValueChange: (InventoryInput) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        OutlinedTextField(
            value = inventory.title,
            onValueChange = { onValueChange(inventory.copy(title = it)) },
            label = { Text("タイトル") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = inventory.category ?: "",
            onValueChange = { onValueChange(inventory.copy(category = it)) },
            label = { Text("カテゴリー") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = inventory.quantity ?: "",
            onValueChange = { onValueChange(inventory.copy(quantity = it)) },
            label = { Text("数") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}