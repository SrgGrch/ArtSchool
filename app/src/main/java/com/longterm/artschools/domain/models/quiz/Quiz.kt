package com.longterm.artschools.domain.models.quiz


data class Quiz(
    val id: Int,
    val question: String,
    val text: String,
    val answers: List<QuizAnswer>,
    val userAnswers: List<Int>? = null
)