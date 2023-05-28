package com.longterm.artschools.data.models.quiz

import kotlinx.serialization.Serializable

@Serializable
data class PostAnswerRequest(
    val answers: List<Int>
)