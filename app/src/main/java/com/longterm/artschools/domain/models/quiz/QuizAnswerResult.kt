package com.longterm.artschools.domain.models.quiz

data class QuizAnswerResult(
    val score: Int,
    val answers: List<QuizAnswer>,
    val reaction: String
) {
    val correct: Boolean
        get() = score != 0

    val answer: QuizAnswer
        get() = answers.first()
}