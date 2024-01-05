package com.github.goutarouh.notionboost.ui.monthlyWidgetList

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MonthlyWidgetListScreen(
    uiModel: MonthlyWidgetListUiModel,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        items(uiModel.widgets) {
            MonthlyWidgetItem(
                widget = it,
                modifier = Modifier,
            )
        }
    }

}

@Composable
fun MonthlyWidgetItem(
    widget: MonthlyWidgetModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier.padding(16.dp)
    ) {
        Text(
            text = widget.databaseId,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}