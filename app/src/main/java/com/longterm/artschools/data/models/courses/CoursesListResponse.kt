package com.longterm.artschools.data.models.courses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoursesListResponse(
    val id: Int,
    val name: String,
    val difficulty: Int,
    val image: String?,
    val tags: List<Int>,
    @SerialName("total_lessons")
    val totalLessons: Int,
    @SerialName("free_lessons")
    val freeLessons: Int,
    @SerialName("lessons_passed")
    val lessonsPassed: Int
)