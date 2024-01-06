package com.github.goutarouh.notionboost.widget

import com.github.goutarouh.notionboostrepository.repository.model.MonthlyWidgetModel

sealed class MonthlyWidgetUiState {

    data object Preparing : MonthlyWidgetUiState()
    data class Success(
        val monthlyWidgetModel: MonthlyWidgetModel
    ) : MonthlyWidgetUiState()
}