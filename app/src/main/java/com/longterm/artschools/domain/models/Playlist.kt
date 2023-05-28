package com.longterm.artschools.domain.models

class Playlist(
    val id: Int,
    val linkToVk: String,
    val imageUrl: String,
    val title: String,
    val description: String,
    val tags: List<Tag>
)