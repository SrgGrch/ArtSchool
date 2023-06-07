package com.longterm.artschools.domain.usecase

import com.longterm.artschools.data.repository.QuizRepository
import com.longterm.artschools.domain.models.quiz.QuizAnswerResult

class AnswerQuizUseCase(
    private val quizRepository: QuizRepository
) {
    suspend fun execute(quizId: Int, answerId: Int): Result<QuizAnswerResult> {
        return quizRepository.postAnswer(quizId, answerId) // todo
    }
}