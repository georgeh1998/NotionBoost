package com.github.goutarouh.notionboost.widget.configuration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MonthlyWidgetConfigurationViewModel @Inject constructor() : ViewModel() {

    private val _uiModel = MutableStateFlow<MonthlyWidgetConfigurationUiModel>(MonthlyWidgetConfigurationUiModel(
        updateInputDatabaseId = ::updateInputDatabaseId,
        createMonthlyWidget = ::createMonthlyWidget
    ))
    val uiModel = _uiModel.asStateFlow()

    private fun updateInputDatabaseId(inputDatabaseId: String) {
        viewModelScope.launch {
            _uiModel.update { it.copy(inputDatabaseId = inputDatabaseId) }
        }
    }

    private fun createMonthlyWidget() {
        viewModelScope.launch {
            _uiModel.update { it.copy(finishConfiguration = true) }
        }
    }

}