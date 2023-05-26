package com.longterm.artschools.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VkAuthRequest(
    @SerialName("vk_access_token")
    val vkAccessToken: String
)