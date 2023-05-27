package com.longterm.artschools.ui.components.main

import androidx.lifecycle.ViewModel
import com.longterm.artschools.ui.components.main.models.MainListItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {
    val state: StateFlow<State>
        get() = _state
    private val _state = MutableStateFlow<State>(State.Loading)

    fun onQuizAnswer(answer: MainListItem.QuizItem.Answer, position: Int) {

    }

    fun retry() {

    }

    sealed interface State {
        val userPic: String?
            get() = null

        val level: String
            get() = "·"

        object Loading : State
        object Error : State
        data class Data(
            val items: List<MainListItem>,
            val isLoading: Boolean = false,
            override val level: String,
            override val userPic: String?
        ) : State
    }
}