package com.github.goutarouh.notionboost.ui.monthlyWidgetList

import android.appwidget.AppWidgetManager

data class MonthlyWidgetListUiModel(
    val isLoading: Boolean = false,
    val isUpdating: MonthlyWidgetModel? = null,
    val widgets: List<MonthlyWidgetModel> = listOf(),
)

data class MonthlyWidgetModel(
    val appWidgetId: Int = AppWidgetManager.INVALID_APPWIDGET_ID,
    val databaseId: String = "",
    val title: String = "",
    val url: String = "",

    val updateWidget: (monthlyWidgetModel: MonthlyWidgetModel) -> Unit = {},
)
