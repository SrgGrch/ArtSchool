package com.longterm.artschools.data.models.account

import kotlinx.serialization.Serializable

@Serializable
data class SetTargetsRequest(
    val targets: List<String>
)