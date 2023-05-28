package com.longterm.artschools.data.models.quiz

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuizResponse(
    @SerialName("question")
    val question: String,
    @SerialName("text")
    val text: String,
    val image: String? = null,
    @SerialName("answers")
    val answers: List<Answers>,
    @SerialName("user_answers")
    val userAnswers: List<Int>? = null
) {
    @Serializable
    data class Answers(
        @SerialName("id")
        val id: Int,
        @SerialName("text")
        val text: String
    )
}