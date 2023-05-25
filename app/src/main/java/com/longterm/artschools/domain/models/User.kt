package com.longterm.artschools.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//todo
@Serializable
class User(
    val email: String,
    val age: Int? = null,
    @SerialName("first_name")
    val firstName: String? = null,
    val photoUrl: String? = null,
    //    val preferences: List<Any>? = null, // todo пока нет в API
    //    val targets: List<Any>? = null, // todo пока нет в API
)