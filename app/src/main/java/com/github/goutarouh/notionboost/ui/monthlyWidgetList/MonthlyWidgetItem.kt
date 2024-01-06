package com.github.goutarouh.notionboost.ui.monthlyWidgetList

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.goutarouh.notionboost.ui.theme.NotionBoostTheme

@Composable
fun MonthlyWidgetListItem(
    monthlyWidgetListItem: MonthlyWidgetListItemModel,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .height(120.dp)
                .padding(vertical = 32.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = monthlyWidgetListItem.title,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Start,
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = {
                    monthlyWidgetListItem.updateWidget(monthlyWidgetListItem)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    modifier = Modifier.size(32.dp),
                    contentDescription = "",
                )
            }
        }
    }
}

@Preview(
    heightDp = 120
)
@Composable
private fun PreviewMonthlyWidgetItem() {
    NotionBoostTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            MonthlyWidgetListItem(
                monthlyWidgetListItem = MonthlyWidgetListItemModel(
                    title = "Title",
                ),
            )
        }
    }
}