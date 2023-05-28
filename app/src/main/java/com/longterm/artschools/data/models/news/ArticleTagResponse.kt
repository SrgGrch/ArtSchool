package com.longterm.artschools.data.models.news

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleTagResponse(
    val id: Int,
    val title: String,
    @SerialName("background_color")
    val color: String
)