package com.longterm.artschools.domain.models.quiz

data class QuizAnswerResult(
    val score: Float,
    val answers: List<QuizAnswer>,
    val reaction: String
) {
    val correct: Boolean
        get() = score > 0.1

    val answer: QuizAnswer
        get() = answers.first()
}