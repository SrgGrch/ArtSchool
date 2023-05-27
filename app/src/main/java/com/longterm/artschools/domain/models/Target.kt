package com.longterm.artschools.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Target(
    val code: String,
    val name: String
)