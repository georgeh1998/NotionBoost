package com.github.goutarouh.notionboost.ui.monthlyWidgetList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.goutarouh.notionboost.data.datastore.DataStoreApi
import com.github.goutarouh.notionboost.repository.NotionDatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MonthlyWidgetListViewModel @Inject constructor(
    notionDatabaseRepository: NotionDatabaseRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(MonthlyWidgetListUiModel(isLoading = true))
    val uiState = _uiState.asStateFlow()

    private val monthlyConfiguration = notionDatabaseRepository.monthlyWidgetConfigurationFlow()
        .onEach {
            val widgets = it.values.map { databaseId ->
                MonthlyWidgetModel(
                    databaseId = databaseId,
                    title = "",
                    url = "",
                )
            }
            _uiState.update {
                it.copy(isLoading = false, widgets = widgets)
            }
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, mapOf())

}