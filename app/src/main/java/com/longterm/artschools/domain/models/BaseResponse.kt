package com.longterm.artschools.domain.models

data class BaseResponse<T>(val status: String, val result: T)
