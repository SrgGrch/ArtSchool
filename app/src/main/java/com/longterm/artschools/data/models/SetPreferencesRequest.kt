package com.longterm.artschools.data.models

import kotlinx.serialization.Serializable

@Serializable
data class SetPreferencesRequest(
    val preferences: List<String>
)