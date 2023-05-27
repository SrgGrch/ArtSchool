package com.longterm.artschools.ui.core


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
