package com.longterm.artschools.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Preference(
    val code: String,
    val name: String
)
