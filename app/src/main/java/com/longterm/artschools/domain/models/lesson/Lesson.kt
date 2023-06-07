package com.longterm.artschools.domain.models.lesson

import com.longterm.artschools.domain.models.quiz.Quiz

data class Lesson(
    val id: Int,
    val image: String,
    val number: Int,
    val name: String,
    val description: String,
    val isFree: Boolean,
    val cost: Int,
    val duration: Int,
    val questions: List<Quiz>,
    val viewed: Boolean,
    val passed: Boolean
)
