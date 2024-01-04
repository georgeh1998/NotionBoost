package com.github.goutarouh.notionboost.widget.configuration

import android.appwidget.AppWidgetManager
import android.content.Context
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.goutarouh.notionboost.repository.NotionDatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MonthlyWidgetConfigurationViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val notionDatabaseRepository: NotionDatabaseRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiModel = MutableStateFlow(MonthlyWidgetConfigurationUiModel(
        appWidgetId = savedStateHandle[AppWidgetManager.EXTRA_APPWIDGET_ID] ?: AppWidgetManager.INVALID_APPWIDGET_ID,
        updateInputDatabaseId = ::updateInputDatabaseId,
        createMonthlyWidget = ::createMonthlyWidget
    ))
    val uiModel = _uiModel.asStateFlow()

    private fun updateInputDatabaseId(inputDatabaseId: String) {
        viewModelScope.launch {
            _uiModel.update { it.copy(inputDatabaseId = inputDatabaseId) }
        }
    }

    private fun createMonthlyWidget(
        appWidgetId: Int,
        databaseId: String,
    ) {
        val map = mapOf(appWidgetId to databaseId)
        Log.i("hasegawa", "createMonthlyWidget: $databaseId")
        viewModelScope.launch {
            try {
                notionDatabaseRepository.saveMonthlyWidgetConfiguration(map)
            } catch (e: Exception) {
                Log.e("MonthlyWidgetConfigurationViewModel", "createMonthlyWidget: $e")
            }
            _uiModel.update { it.copy(finishConfiguration = true) }
        }
    }

}