package com.github.goutarouh.notionboost.ui.inital

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.goutarouh.notionboost.repository.NotionDatabaseRepository
import com.github.goutarouh.notionboost.ui.inital.model.InitialDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InitialViewModel @Inject constructor(
    private val notionDatabaseRepository: NotionDatabaseRepository,
) : ViewModel() {

    private val _initialDestination = MutableStateFlow<InitialDestination?>(null)
    val initialDestination = _initialDestination.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val apiKey = notionDatabaseRepository.getNotionApiKey()
                if (apiKey.isEmpty()) {
                    _initialDestination.value = InitialDestination.WelcomeScreen
                    return@launch
                } else {
                    _initialDestination.value = InitialDestination.MonthlyWidgetSettingFragment
                    return@launch
                }
            } catch (e: Exception) {
                _initialDestination.value = InitialDestination.WelcomeScreen
                return@launch
            }
        }
    }
}