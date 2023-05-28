package com.longterm.artschools.ui.components.coursesList.models

data class CoursePreview(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
    val reward: Int,
    val tags: List<Tag>
) {
    data class Tag(
        val text: String,
        val color: Long
    )
}