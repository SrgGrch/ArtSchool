package com.longterm.artschools.ui.components.course.models

data class Course(
    val id: Int,
    val title: String,
    val authorName: String,
    val authorImageUrl: String,
    val authorDescription: String,
    val reward: Int,
    val lessons: List<Lesson>
) {
    data class Lesson(
        val id: Int,
        val title: String,
        val cost: String,
        val isFree: Boolean,
        val duration: String,
        val unlocked: Boolean
    )
}