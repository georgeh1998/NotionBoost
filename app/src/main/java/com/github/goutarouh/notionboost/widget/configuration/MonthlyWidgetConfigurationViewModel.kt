package com.github.goutarouh.notionboost.widget.configuration

import android.app.Application
import android.appwidget.AppWidgetManager
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.goutarouh.notionboost.repository.ApiException
import com.github.goutarouh.notionboost.repository.DataStoreException
import com.github.goutarouh.notionboost.repository.usecase.MonthlyWidgetInitialUseCase
import com.github.goutarouh.notionboost.widget.configuration.MonthlyWidgetConfigurationUiModel.ConfigurationResult
import com.github.goutarouh.notionboost.widget.glanceMonthlyWidget
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MonthlyWidgetConfigurationViewModel @Inject constructor(
    private val application: Application,
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
                    afterStateUpdate = {
                        val glanceId = GlanceAppWidgetManager(application).getGlanceIdBy(appWidgetId)
                        glanceMonthlyWidget.update(application, glanceId)
                    }
                )
                _uiModel.update { it.copy(configurationResult = ConfigurationResult.Success) }
            } catch (e: ApiException) {
                _uiModel.update { it.copy(configurationResult = ConfigurationResult.Failure(e)) }
            } catch (e: DataStoreException) {
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