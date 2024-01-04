package com.github.goutarouh.notionboost.ui.inital.model

sealed interface InitialUiState {

    data object Initial : InitialUiState
    data class NavAction(val initialNavAction: InitialNavAction) : InitialUiState
    data class Unexpected(val exception: Exception) : InitialUiState
}