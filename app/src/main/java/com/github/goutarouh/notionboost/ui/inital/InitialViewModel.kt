package com.github.goutarouh.notionboost.ui.inital

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.goutarouh.notionboost.ui.inital.model.InitialNavAction
import com.github.goutarouh.notionboost.ui.inital.model.InitialUiState
import com.github.goutarouh.notionboost.repository.DataStoreException
import com.github.goutarouh.notionboost.repository.repository.NotionDatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InitialViewModel @Inject constructor(
    private val notionDatabaseRepository: NotionDatabaseRepository,
) : ViewModel() {

    private val _initialUiState = MutableStateFlow<InitialUiState>(InitialUiState.Initial)
    val initialUiState = _initialUiState.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val apiKey = notionDatabaseRepository.getNotionApiKey()
                if (apiKey.isEmpty()) {
                    updateUiState(InitialUiState.NavAction(InitialNavAction.WelcomeScreen))
                } else {
                    updateUiState(InitialUiState.NavAction(InitialNavAction.MonthlyWidgetListFragment))
                }
            } catch (e: DataStoreException) {
                when (e) {
                    is DataStoreException.NotSetException -> {
                        updateUiState(InitialUiState.NavAction(InitialNavAction.WelcomeScreen))
                    }
                    is DataStoreException.TimeOutException -> {
                        updateUiState(InitialUiState.Unexpected(e))
                    }
                    is DataStoreException.UnExpectedException -> {
                        updateUiState(InitialUiState.Unexpected(e))
                    }
                }
            } catch (e: Exception) {
                updateUiState(InitialUiState.Unexpected(e))
                return@launch
            }
        }
    }

    private suspend fun updateUiState(initialUiState: InitialUiState) {
        _initialUiState.update { initialUiState }
    }
}