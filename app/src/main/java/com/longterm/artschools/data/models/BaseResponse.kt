package com.longterm.artschools.data.models

import android.accounts.NetworkErrorException
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val status: String? = null,
    val result: T? = null,
    val details: String? = null
) {
    fun unwrap(): Result<T> {
        return if (status == "ok") result?.let { Result.success(it) }
            ?: Result.failure(IllegalStateException("Status is ok, but result is null"))
        else Result.failure(NetworkErrorException(details))
    }
}
