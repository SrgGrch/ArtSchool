package com.longterm.artschools.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(val status: String, val result: T)
