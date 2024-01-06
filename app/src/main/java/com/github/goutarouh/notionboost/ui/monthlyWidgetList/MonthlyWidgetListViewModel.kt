package com.github.goutarouh.notionboost.ui.monthlyWidgetList

import android.app.Application
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.goutarouh.notionboost.repository.repository.GlanceRepository
import com.github.goutarouh.notionboost.repository.repository.NotionDatabaseRepository
import com.github.goutarouh.notionboost.repository.usecase.MonthlyWidgetInitialUseCase
import com.github.goutarouh.notionboost.widget.glanceMonthlyWidget
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MonthlyWidgetListViewModel @Inject constructor(
    private val application: Application,
    notionDatabaseRepository: NotionDatabaseRepository,
    private val glanceRepository: GlanceRepository,
    private val monthlyWidgetInitialUseCase: MonthlyWidgetInitialUseCase,
): ViewModel() {

    private val _uiState = MutableStateFlow(MonthlyWidgetListUiModel(isLoading = true))
    val uiState = _uiState.asStateFlow()

    val monthlyConfiguration = notionDatabaseRepository.monthlyWidgetConfigurationFlow()
        .mapNotNull { configuration ->
            configuration.map { config ->
                val appWidgetId = config.key
                val monthlyWidgetModel = glanceRepository.getMonthlyWidgetModel(appWidgetId)
                    ?: return@mapNotNull null
                MonthlyWidgetListItemModel(
                    appWidgetId = appWidgetId,
                    databaseId = monthlyWidgetModel.databaseId,
                    title = monthlyWidgetModel.title,
                    url = monthlyWidgetModel.url,
                    updateWidget = ::updateWidget,
                )
            }
        }
        .onEach { widgets ->
            _uiState.update {
                it.copy(isLoading = false, widgets = widgets)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf())

    private fun updateWidget(monthlyWidgetListItemModel: MonthlyWidgetListItemModel) {
        viewModelScope.launch {
            _uiState.update { it.copy(isUpdating = monthlyWidgetListItemModel) }
            monthlyWidgetInitialUseCase.invoke(
                databaseId = monthlyWidgetListItemModel.databaseId,
                appWidgetId = monthlyWidgetListItemModel.appWidgetId,
                afterStateUpdate = { appWidgetId ->
                    val glanceId = GlanceAppWidgetManager(application).getGlanceIdBy(appWidgetId)
                    glanceMonthlyWidget.update(application, glanceId)
                }
            )
            _uiState.update { it.copy(isUpdating = null) }
        }
    }

}