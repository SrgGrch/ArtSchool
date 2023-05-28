package com.longterm.artschools.data.models.courses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CourseResponse(
    val id: Int,
    val name: String,
    val difficulty: Int,
    val tags: List<String>,
    @SerialName("total_lessons")
    val totalLessons: Int,
    @SerialName("free_lessons")
    val freeLessons: Int,
    val lessons: List<Lesson>
) {
    @Serializable
    data class Lesson(
        val id: Int,
        val number: Int,
        val name: String,
        val is_free: Boolean,
        val cost: Int,
        val duration: Int,
        val viewed: Boolean,
        val passed: Boolean
    )
}
