package com.github.goutarouh.notionboost.widget.configuration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.goutarouh.notionboost.repository.NotionDatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MonthlyWidgetConfigurationViewModel @Inject constructor(
    private val notionDatabaseRepository: NotionDatabaseRepository,
) : ViewModel() {

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

    private fun createMonthlyWidget(databaseId: String) {
        viewModelScope.launch {
            notionDatabaseRepository.addDatabaseId(databaseId)
            _uiModel.update { it.copy(finishConfiguration = true) }
        }
    }

}