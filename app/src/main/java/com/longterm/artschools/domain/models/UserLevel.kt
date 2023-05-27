package com.longterm.artschools.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class UserLevel(
    val level: Level? = null, //O'rly?
    val total_exp: Double? = null //O'rly?
) {
    @Serializable
    data class Level(
        val id: Int?, //O'rly?
        val level: Int,
        val exp: Int,
        val description: String
    )
}