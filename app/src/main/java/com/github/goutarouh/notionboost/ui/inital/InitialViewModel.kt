package com.github.goutarouh.notionboost.ui.inital

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.goutarouh.notionboost.repository.NotionDatabaseRepository
import com.github.goutarouh.notionboost.ui.inital.model.InitialNavAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InitialViewModel @Inject constructor(
    private val notionDatabaseRepository: NotionDatabaseRepository,
) : ViewModel() {

    private val _initialNavAction = MutableStateFlow<InitialNavAction?>(null)
    val initialNavAction = _initialNavAction.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val apiKey = notionDatabaseRepository.getNotionApiKey()
                if (apiKey.isEmpty()) {
                    _initialNavAction.value = InitialNavAction.WelcomeScreen
                    return@launch
                } else {
                    _initialNavAction.value = InitialNavAction.MonthlyWidgetSettingFragment
                    return@launch
                }
            } catch (e: Exception) {
                _initialNavAction.value = InitialNavAction.WelcomeScreen
                return@launch
            }
        }
    }
}