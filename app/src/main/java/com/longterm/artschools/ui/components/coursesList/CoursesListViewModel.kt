package com.longterm.artschools.ui.components.coursesList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.longterm.artschools.data.repository.CoursesRepository
import com.longterm.artschools.data.repository.NewsRepository
import com.longterm.artschools.domain.MediaPathResolver
import com.longterm.artschools.domain.models.Tag
import com.longterm.artschools.ui.components.coursesList.models.CoursePreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoursesListViewModel(
    private val coursesRepository: CoursesRepository,
    private val newsRepository: NewsRepository
) : ViewModel() {
    val state: StateFlow<State>
        get() = _state
    private val _state = MutableStateFlow<State>(State.Loading)

    init {
        getAll()
    }

    private fun getAll() {
        viewModelScope.launch {

            getAllCourses()
                .onSuccess {
                    _state.update { _ ->
                        State.Data(it)
                    }
                }
                .onFailure {
                    Log.e("CoursesListViewModel", it.stackTraceToString())
                    _state.update {
                        State.Error
                    }
                }
        }
    }

    fun retry() {
        getAll()
    }

    private suspend fun getAllCourses(): Result<List<CoursePreview>> {
        val tags = newsRepository.getTagsMap()

        return coursesRepository.getAll()
            .map { courses ->
                courses.map { course ->
                    CoursePreview(
                        id = course.id,
                        title = course.name,
                        description = "${course.totalLessons} уроков\n${course.freeLessons} пробный урок",
                        imageUrl = course.image?.let(MediaPathResolver::resolve)
                            ?: "https://img3.akspic.ru/crops/3/0/8/0/5/150803/150803-zhivopis-krasnyjcvet-kraska-art-sovremennoeiskusstvo-3840x2160.jpg",
                        reward = 10,
                        tags = course.tags.mapNotNull {
                            tags[it]
                        } + Tag(createDifficultyString(course.difficulty), "#FFD0BCFF")
                    )
                }
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