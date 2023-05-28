package com.longterm.artschools.ui.components.coursesList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.longterm.artschools.data.repository.CoursesRepository
import com.longterm.artschools.ui.components.coursesList.models.CoursePreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoursesListViewModel(private val coursesRepository: CoursesRepository) : ViewModel() {
    val state: StateFlow<State>
        get() = _state
    private val _state = MutableStateFlow<State>(State.Loading)

    init {
        getAll()
    }

    private fun getAll() {
        viewModelScope.launch {
            _state.update {
                getAllCourses().getOrNull()
                    ?.let { State.Data(it) } ?: State.Error
            }
        }
    }

    fun retry() {
        getAll()
    }

    private suspend fun getAllCourses() = coursesRepository.getAll()
        .map { courses ->
            courses.map { course ->
                CoursePreview(
                    id = course.id,
                    title = course.name,
                    description = "${course.totalLessons} уроков\n${course.freeLessons} пробный урок",
                    imageUrl = "https://img3.akspic.ru/crops/3/0/8/0/5/150803/150803-zhivopis-krasnyjcvet-kraska-art-sovremennoeiskusstvo-3840x2160.jpg",
                    reward = 10,
                    tags = course.tags.map {
                        CoursePreview.Tag(it, 0xFFEFB8C8)
                    } + CoursePreview.Tag(createDifficultyString(course.difficulty), 0xFFD0BCFF)
                )
            }
        }

    private fun createDifficultyString(difficulty: Int) = buildString {
        repeat(difficulty) {
            append("⭐ ")
        }
        append("Сложность")
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