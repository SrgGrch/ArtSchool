package com.longterm.artschools.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//todo
@Serializable
data class User(
    val id: Int,
    @SerialName("vk_id")
    val vkId: Int? = null,
    val level: UserLevel,
    val email: String,
    val age: Int? = null,
    @SerialName("first_name")
    val firstName: String? = null,
    val avatar: String? = null,
    val preferences: List<Preference>? = null,
    val targets: List<Target>? = null
)
