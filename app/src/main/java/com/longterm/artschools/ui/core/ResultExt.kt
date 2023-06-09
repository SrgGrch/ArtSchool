package com.longterm.artschools.ui.core

import android.util.Log


inline fun <T, R> Result<T>.onSuccessMap(block: (T) -> R): Result<R> {
    return if (isSuccess) Result.success(block(getOrThrow()))
    else Result.failure(exceptionOrNull()!!)
}

inline fun <T, R> Result<T>.onSuccessMapResult(block: (T) -> Result<R>): Result<R> {
    return if (isSuccess) {
        block(getOrThrow())
    } else Result.failure(exceptionOrNull()!!)
}

fun <T> Result<T>.mapToUnit(): Result<Unit> {
    return map { }
}

inline fun <T> withResult(block: () -> T): Result<T> {
    return try {
        block().let { Result.success(it) }
    } catch (e: Exception) {
        Result.failure(e)
    }
}

fun <T> Result<T>.onFailureLog(tag: String): Result<T> {
    return onFailure {
        Log.e(tag, it.stackTraceToString())
    }
}