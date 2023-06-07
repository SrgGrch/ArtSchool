package com.longterm.artschools.domain.usecase

import com.longterm.artschools.data.repository.CoursesRepository
import com.longterm.artschools.data.repository.QuizRepository
import com.longterm.artschools.domain.models.lesson.Lesson
import com.longterm.artschools.ui.core.withResult

class LessonUseCase(
    private val quizRepository: QuizRepository,
    private val coursesRepository: CoursesRepository
) {
    suspend fun execute(id: Int): Result<Lesson> {
        return withResult {
            val lesson = coursesRepository.getLesson(id).getOrThrow()
            val quizes = lesson.questions.map {
                quizRepository.getQuiz(it).getOrThrow()
            }

            Lesson(
                id = lesson.id,
                image = lesson.image,
                number = lesson.number,
                name = lesson.name,
                description = lesson.description,
                isFree = lesson.isFree,
                cost = lesson.cost,
                duration = lesson.duration,
                questions = quizes,
                viewed = lesson.viewed,
                passed = lesson.passed
            )
        }
    }
}