package com.longterm.artschools.ui.components.course

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.longterm.artschools.data.repository.CoursesRepository
import com.longterm.artschools.ui.components.course.models.Course
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CourseViewModel(private val id: Int, private val coursesRepository: CoursesRepository) : ViewModel() {
    val state: StateFlow<State>
        get() = _state
    private val _state = MutableStateFlow<State>(State.Loading)

    init {
        get(id)
    }

    fun retry() {
        get(id)
    }

    private fun get(id: Int) {
        viewModelScope.launch {
            _state.update {
                coursesRepository.get(id).getOrNull()?.let {
                    State.Data(
                        Course(
                            id = it.id,
                            title = it.name,
                            authorName = it.authorName,
                            authorImageUrl = "https://shop-cdn1-2.vigbo.tech/shops/19661/products/21612973/images/3-2e7445062f6dbcbde00cb3deee691548.jpg",
                            authorDescription = it.authorDescription,
                            reward = 50,
                            lessons = it.lessons.map { lesson ->
                                Course.Lesson(lesson.id, lesson.name, "${lesson.cost} баллов", "${lesson.duration} мин")
                            }
                        )
                    )
                } ?: State.Error
            }
        }
    }

    sealed interface State {
        object Loading : State
        object Error : State
        data class Data(val course: Course) : State
    }
}
