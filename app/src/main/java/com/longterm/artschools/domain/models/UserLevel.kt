package com.longterm.artschools.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserLevel(
    @SerialName("current_level")
    val currentLevel: Level? = null, //O'rly?
    @SerialName("total_exp")
    val totalExp: Double? = null //O'rly?
) {
    @Serializable
    data class Level(
        val id: Int?, //O'rly?
        val level: Int,
        val exp: Int,
        val description: String
    )
}