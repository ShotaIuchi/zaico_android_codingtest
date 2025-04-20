package jp.co.zaico.codingtest.ui.parts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import jp.co.zaico.codingtest.InvtySearchQuery

@Composable
fun BoxScope.InvtySearchOverlay(
    query: InvtySearchQuery,
    onQueryChange: (InvtySearchQuery) -> Unit,
    onSearch: () -> Unit,
    onClear: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.3f))
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {}
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
            .align(Alignment.TopCenter)
    ) {
        OutlinedTextField(
            value = query.title.orEmpty(),
            onValueChange = { onQueryChange(query.copy(title = it)) },
            label = { Text("タイトル") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = query.category.orEmpty(),
            onValueChange = { onQueryChange(query.copy(category = it)) },
            label = { Text("カテゴリ") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = query.place.orEmpty(),
            onValueChange = { onQueryChange(query.copy(place = it)) },
            label = { Text("ステート") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {
                onDismiss()
                onSearch()
            }) {
                Text("検索")
            }

            OutlinedButton(onClick = onClear) {
                Text("クリア")
            }
        }
    }
}
