package com.github.goutarouh.notionboost.widget

sealed class MonthlyWidgetUiState {

    data object Preparing : MonthlyWidgetUiState()
    data object NoData : MonthlyWidgetUiState()
    data class Success(
        val monthlyWidgetModel: MonthlyWidgetModel
    ) : MonthlyWidgetUiState()
}