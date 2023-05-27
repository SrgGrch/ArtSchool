package com.longterm.artschools.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class UserLevel(
    val level: Level?, //O'rly?
    val total_exp: Int? //O'rly?
) {
    @Serializable
    data class Level(
        val id: Int?, //O'rly?
        val level: Int,
        val exp: Int,
        val description: String
    )
}