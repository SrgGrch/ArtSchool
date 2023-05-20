package com.longterm.artschools.ui.components.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class MainViewModel : ViewModel() {
    val state: StateFlow<String>
        get() = _state
    private val _state = MutableStateFlow("String")

    init {
        viewModelScope.launch {
            delay(10.seconds)

            _state.value = "Хун"
        }
    }
}