package com.longterm.artschools.data.models.account

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val email: String,
    val password: String,
    @SerialName("password2")
    val repeatPassword: String
)