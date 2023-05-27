package com.longterm.artschools.data.models.account

import kotlinx.serialization.Serializable

@Serializable
data class SetPreferencesRequest(
    val preferences: List<String>
)