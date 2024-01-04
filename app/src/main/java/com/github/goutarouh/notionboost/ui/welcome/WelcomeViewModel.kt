package com.github.goutarouh.notionboost.ui.welcome

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
class WelcomeViewModel @Inject constructor(
    private val repository: NotionDatabaseRepository
) : ViewModel() {

    private val _uiModel = MutableStateFlow(
        WelcomeUiModel(
            updateInputApiKey = ::updateInputApiKey,
            saveApiKey = ::saveApiKey
        )
    )
    val uiModel = _uiModel.asStateFlow()

    private fun updateInputApiKey(inputApiKey: String) {
        _uiModel.update { it.copy(inputApiKey = inputApiKey) }
    }

    private fun saveApiKey(apiKey: String) {
        viewModelScope.launch {
            repository.saveNotionApiKey(apiKey)
            _uiModel.update { it.copy(apiKeySaved = true) }
        }
    }

}