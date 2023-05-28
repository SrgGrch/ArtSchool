package com.longterm.artschools.data.models.quiz

import kotlinx.serialization.Serializable

@Serializable
data class PostAnswerResponse(
    val score: Int,
    val answers: List<Answers>,
    val reaction: String
) {
    @Serializable
    data class Answers(
        val id: Int,
        val text: String
    )
}