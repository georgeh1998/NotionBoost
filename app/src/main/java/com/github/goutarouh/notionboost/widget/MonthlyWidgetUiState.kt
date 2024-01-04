package com.github.goutarouh.notionboost.widget

sealed class MonthlyWidgetUiState {

    data object Loading : MonthlyWidgetUiState()
    data object NoData : MonthlyWidgetUiState()
    data class Success(
        val monthlyWidgetModel: MonthlyWidgetModel
    ) : MonthlyWidgetUiState()
}