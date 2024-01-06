package com.github.goutarouh.notionboost.widget.configuration

import android.appwidget.AppWidgetManager
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.goutarouh.notionboostrepository.repository.ApiException
import com.github.goutarouh.notionboost.usecase.MonthlyWidgetInitialUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.github.goutarouh.notionboost.widget.configuration.MonthlyWidgetConfigurationUiModel.ConfigurationResult
import kotlinx.coroutines.CancellationException

@HiltViewModel
class MonthlyWidgetConfigurationViewModel @Inject constructor(
    private val monthlyWidgetInitialUseCase: MonthlyWidgetInitialUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiModel = MutableStateFlow(MonthlyWidgetConfigurationUiModel(
        appWidgetId = savedStateHandle[AppWidgetManager.EXTRA_APPWIDGET_ID] ?: AppWidgetManager.INVALID_APPWIDGET_ID,
        updateInputDatabaseId = ::updateInputDatabaseId,
        createMonthlyWidget = ::createMonthlyWidget,
        clearConfigurationResult = ::clearConfigurationResult,
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
        viewModelScope.launch {
            try {
                _uiModel.update { it.copy(configurationResult = ConfigurationResult.Loading) }
                monthlyWidgetInitialUseCase.invoke(
                    databaseId = databaseId,
                    appWidgetId = appWidgetId,
                )
                _uiModel.update { it.copy(configurationResult = ConfigurationResult.Success) }
            } catch (e: com.github.goutarouh.notionboostrepository.repository.ApiException) {
                _uiModel.update { it.copy(configurationResult = ConfigurationResult.Failure(e)) }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiModel.update { it.copy(configurationResult = ConfigurationResult.Failure(e)) }
            }
        }
    }

    private fun clearConfigurationResult() {
        viewModelScope.launch {
            _uiModel.update { it.copy(configurationResult = ConfigurationResult.Initial) }
        }
    }

}