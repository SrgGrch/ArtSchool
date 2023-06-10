package com.longterm.artschools.ui.components.coursesList.models

import com.longterm.artschools.domain.models.Tag

data class CoursePreview(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
    val reward: Int,
    val tags: List<Tag>
)