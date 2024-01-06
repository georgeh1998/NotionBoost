package com.github.goutarouh.notionboost.ui.monthlyWidgetList

import android.appwidget.AppWidgetManager

data class MonthlyWidgetListUiModel(
    val isLoading: Boolean = false,
    val isUpdating: MonthlyWidgetListItemModel? = null,
    val widgets: List<MonthlyWidgetListItemModel> = listOf(),
)

data class MonthlyWidgetListItemModel(
    val appWidgetId: Int = AppWidgetManager.INVALID_APPWIDGET_ID,
    val databaseId: String = "",
    val title: String = "",
    val url: String = "",

    val updateWidget: (monthlyWidgetModel: MonthlyWidgetListItemModel) -> Unit = {},
)
