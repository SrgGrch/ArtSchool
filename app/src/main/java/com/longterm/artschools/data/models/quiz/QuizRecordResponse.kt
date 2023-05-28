package com.longterm.artschools.data.models.quiz

import kotlinx.serialization.Serializable

@Serializable
data class QuizRecordResponse(
    val id: Int,
    val question: String,
)