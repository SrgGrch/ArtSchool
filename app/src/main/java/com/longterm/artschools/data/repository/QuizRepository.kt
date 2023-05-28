package com.longterm.artschools.data.repository

import com.longterm.artschools.data.api.QuizApi
import com.longterm.artschools.domain.models.quiz.Quiz
import com.longterm.artschools.domain.models.quiz.QuizAnswer
import com.longterm.artschools.domain.models.quiz.QuizAnswerResult
import com.longterm.artschools.domain.models.quiz.QuizRecord

class QuizRepository(
    private val quizApi: QuizApi
) {
    suspend fun getQuizzes(): Result<List<QuizRecord>> = quizApi.getQuizzes().map { response ->
        response.map { QuizRecord(it.id, it.question) }
    }

    suspend fun getQuiz(id: Int): Result<Quiz> = quizApi.getQuiz(id).map {
        Quiz(
            id,
            it.question,
            it.text,
            it.answers.map { ans -> QuizAnswer(ans.id, ans.text) },
            it.userAnswers,
        )
    }

    suspend fun postAnswer(id: Int, answerId: Int): Result<QuizAnswerResult> = quizApi.postAnswer(id, answerId).map {
        QuizAnswerResult(
            it.score,
            it.answers.map { ans -> QuizAnswer(ans.id, ans.text) },
            it.reaction
        )
    }
}

