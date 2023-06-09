package com.longterm.artschools.domain.usecase

import com.longterm.artschools.data.repository.CoursesRepository
import com.longterm.artschools.data.repository.QuizRepository
import com.longterm.artschools.domain.MediaPathResolver
import com.longterm.artschools.domain.models.lesson.Lesson
import com.longterm.artschools.ui.components.main.models.MainListItem
import com.longterm.artschools.ui.core.withResult

class LessonUseCase(
    private val quizRepository: QuizRepository,
    private val coursesRepository: CoursesRepository
) {
    suspend fun execute(id: Int): Result<Lesson> {
        return withResult {
            val lesson = coursesRepository.getLesson(id).getOrThrow()
            val quizzes = lesson.questions.map {
                quizRepository.getQuiz(it).getOrThrow()
            }.map { quiz ->
                MainListItem.QuizItem(
                    quiz.id,
                    (1..3).random(),
                    quiz.question,
                    quiz.text,
                    quiz.image,
                    quiz.answers.map { a ->
                        MainListItem.QuizItem.Answer(
                            a.id,
                            a.text
                        )
                    }
                )
            }

            Lesson(
                id = lesson.id,
                image = MediaPathResolver.resolve(lesson.image),
                video = lesson.video?.let(MediaPathResolver::resolve),
                number = lesson.number,
                name = lesson.name,
                description = lesson.description,
                isFree = lesson.isFree,
                cost = lesson.cost,
                duration = lesson.duration,
                quizes = quizzes,
                viewed = lesson.viewed,
                passed = lesson.passed
            )
        }
    }
}