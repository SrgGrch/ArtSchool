package com.longterm.artschools.ui.components.course

import androidx.lifecycle.ViewModel
import com.longterm.artschools.data.repository.CoursesRepository
import com.longterm.artschools.ui.components.course.models.Course
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CourseViewModel(private val coursesRepository: CoursesRepository) : ViewModel() {
    val state: StateFlow<State>
        get() = _state
    private val _state = MutableStateFlow<State>(State.Loading)

//    init {
//        viewModelScope.launch {
//            _state.update {
//                getAllCourses().getOrNull()
//                    ?.let { State.Data(it) } ?: State.Error
//            }
//        }
//    }

    sealed interface State {
        object Loading : State
        object Error : State
        data class Data(val course: Course) : State
    }
}


//Course(
//    id = 1,
//    title = "Музыкальные инструменты эпохи Возрождения",
//    authorName = "Виктория Игоревна Саранская",
//    authorImageUrl = "",
//    authorDescription = "Заведующая хоровым отделом “Детской музыкальной школы им. Шебалина”\n" +
//    "\n" +
//    "Окончила МГК им. П.И. Чайковского (2020 г.)\n" +
//    "по специальности «Художественное руководство оперно-симфоническим оркестром и академическим хором»",
//    reward = 100,
//    lessons = listOf(Course.Lesson(0, "3 основные группы инструментов", "Бесплатно", "5 мин"))
//)
