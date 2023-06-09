package com.longterm.artschools.data.models.lesson

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LessonResponse(
    val id: Int,
    val image: String,
    val video: String? = null,
    val number: Int,
    val name: String,
    val description: String,
    @SerialName("is_free")
    val isFree: Boolean,
    val cost: Int,
    val duration: Int,
    val questions: List<Int>,
    val viewed: Boolean,
    val passed: Boolean
)
