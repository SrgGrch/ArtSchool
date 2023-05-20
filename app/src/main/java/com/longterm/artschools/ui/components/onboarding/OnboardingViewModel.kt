package com.longterm.artschools.ui.components.onboarding

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class OnboardingViewModel : ViewModel() {
    val state: StateFlow<State> get() = _state
    private val _state = MutableStateFlow(State())

    fun nextPage() = _state.update { state ->
        state.nextPage().let {
            it.copy(isPrevButtonVisible = it.currentPage != 0)
        }
    }

    fun prevPage() = _state.update { state ->
        state.prevPage().let {
            it.copy(isPrevButtonVisible = it.currentPage != 0)
        }
    }

    data class State(
        val currentPage: Int = 0,
        val isPrevButtonVisible: Boolean = false
    ) {
        fun nextPage() = copy(currentPage = currentPage + 1)
        fun prevPage() = if (currentPage == 0) this else copy(currentPage = currentPage - 1)
    }
}