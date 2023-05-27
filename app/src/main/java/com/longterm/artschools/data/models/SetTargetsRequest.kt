package com.longterm.artschools.data.models

import kotlinx.serialization.Serializable

@Serializable
data class SetTargetsRequest(
    val targets: List<String>
)