package com.github.goutarouh.notionboost.ui.monthlyWidgetList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.goutarouh.notionboost.repository.GlanceRepository
import com.github.goutarouh.notionboost.repository.NotionDatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MonthlyWidgetListViewModel @Inject constructor(
    notionDatabaseRepository: NotionDatabaseRepository,
    glanceRepository: GlanceRepository,
): ViewModel() {

    private val _uiState = MutableStateFlow(MonthlyWidgetListUiModel(isLoading = true))
    val uiState = _uiState.asStateFlow()

    val monthlyConfiguration = notionDatabaseRepository.monthlyWidgetConfigurationFlow()
        .mapNotNull { configuration ->
            configuration.map { config ->
                val appWidgetId = config.key
                val monthlyWidgetModel = glanceRepository.getMonthlyWidgetModel(appWidgetId)
                    ?: return@mapNotNull null
                MonthlyWidgetModel(
                    databaseId = monthlyWidgetModel.databaseId,
                    title = monthlyWidgetModel.title,
                    url = monthlyWidgetModel.url,
                )
            }
        }
        .onEach { widgets ->
            _uiState.update {
                it.copy(isLoading = false, widgets = widgets)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf())

}