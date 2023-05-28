package com.longterm.artschools.domain.models.news

import com.longterm.artschools.domain.models.Tag

data class Article(
    val id: Int,
    val title: String,
    val text: String,
    val tags: List<Tag>
)