package com.longterm.artschools.domain.usecase

import com.longterm.artschools.data.repository.QuizRepository
import com.longterm.artschools.domain.models.quiz.QuizAnswer
import com.longterm.artschools.domain.models.quiz.QuizAnswerResult

class AnswerQuizUseCase(
    private val quizRepository: QuizRepository
) {
    suspend fun execute(quizId: Int, answerId: Int): Result<QuizAnswerResult> {
//        return quizRepository.postAnswer(quizId, answerId) // todo

        return QuizAnswerResult(
            0,
            listOf(QuizAnswer(answerId, "")),
            "✅ Правильный ответ, так держать! «Лунная ночь на Днепре» — одна из самых известных картин Архипа Куинджи."
        ).let { Result.success(it) }
    }
}