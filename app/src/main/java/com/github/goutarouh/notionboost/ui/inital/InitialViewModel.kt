package com.github.goutarouh.notionboost.ui.inital

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.goutarouh.notionboost.ui.inital.model.InitialDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InitialViewModel @Inject constructor(

) : ViewModel() {

    private val _initialDestination = MutableStateFlow<InitialDestination?>(null)
    val initialDestination = _initialDestination.asStateFlow()

    init {
        viewModelScope.launch {
            delay(1000)
            _initialDestination.value = InitialDestination.WelcomeScreen
        }
    }
}