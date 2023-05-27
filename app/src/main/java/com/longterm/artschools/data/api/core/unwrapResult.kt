package com.longterm.artschools.data.api.core

import com.longterm.artschools.data.models.BaseResponse
import io.ktor.client.HttpClient

inline fun <T> HttpClient.withResultUnwrapped(request: HttpClient.() -> BaseResponse<T>): Result<T> {
    return try {
        request().unwrap()
    } catch (e: Exception) {
        Result.failure(e)
    }
}

inline fun <T> HttpClient.withResult(request: HttpClient.() -> T): Result<T> {
    return try {
        Result.success(request())
    } catch (e: Exception) {
        Result.failure(e)
    }
}