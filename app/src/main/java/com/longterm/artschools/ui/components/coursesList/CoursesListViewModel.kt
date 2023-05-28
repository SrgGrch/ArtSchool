package com.longterm.artschools.ui.components.coursesList

import androidx.lifecycle.ViewModel
import com.longterm.artschools.ui.components.coursesList.models.CoursePreview
import com.longterm.artschools.ui.components.main.models.MainListItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CoursesListViewModel : ViewModel() {
    val state: StateFlow<State>
        get() = _state
    private val _state = MutableStateFlow<State>(State.Loading)

    fun onQuizAnswer(answer: MainListItem.QuizItem.Answer, position: Int) {

    }

    fun retry() {

    }

    sealed interface State {
        object Loading : State
        object Error : State
        data class Data(
            val items: List<CoursePreview>,
            val isLoading: Boolean = false,
        ) : State
    }
}