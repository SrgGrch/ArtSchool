package com.longterm.artschools.data.models.points

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PointsResponse(
    val longitude: Double,
    val latitude: Double,
    val name: String,
    val description: String,
    @SerialName("work_time")
    val workTime: String? = null,
    @SerialName("phone_number")
    val phoneNumber: String? = null,
    val preferences: List<Int> = emptyList()
)