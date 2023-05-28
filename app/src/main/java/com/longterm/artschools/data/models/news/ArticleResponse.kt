package com.longterm.artschools.data.models.news

import kotlinx.serialization.Serializable

@Serializable
data class ArticleResponse(
    val id: Int? = null,
    val image: String? = null,
    val title: String,
    val text: String,
    val tags: List<Int>
)