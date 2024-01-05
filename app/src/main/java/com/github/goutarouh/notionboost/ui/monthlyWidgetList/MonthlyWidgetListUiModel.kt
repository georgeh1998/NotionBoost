package com.github.goutarouh.notionboost.ui.monthlyWidgetList

data class MonthlyWidgetListUiModel(
    val isLoading: Boolean = false,
    val widgets: List<MonthlyWidgetModel> = listOf(),
)

data class MonthlyWidgetModel(
    val databaseId: String = "",
    val title: String = "",
    val url: String = "",
)
