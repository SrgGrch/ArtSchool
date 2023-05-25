package com.longterm.artschools.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val email: String,
    val password: String,
    @SerialName("password2")
    val repeatPassword: String,
    val age: Int? = null,
    @SerialName("first_name")
    val firstName: String? = null,
    val photoUrl: String? = null,
//    val preferences: List<Any>? = null, // todo пока нет в API
//    val targets: List<Any>? = null, // todo пока нет в API
)