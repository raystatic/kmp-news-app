package org.raystatic.newsapp.util

sealed class Result<out S, out E> {
    data class Success<out S>(val data: S) : Result<S, Nothing>()
    data class Error<out E>(val exception: E) : Result<Nothing, E>()
}

// Optional: Extension functions for easier handling
inline fun <S, E, R> Result<S, E>.map(transform: (S) -> R): Result<R, E> {
    return when (this) {
        is Result.Success -> Result.Success(transform(data))
        is Result.Error -> this
    }
}

inline fun <S, E> Result<S, E>.onSuccess(action: (S) -> Unit): Result<S, E> {
    if (this is Result.Success) {
        action(data)
    }
    return this
}

inline fun <S, E> Result<S, E>.onError(action: (E) -> Unit): Result<S, E> {
    if (this is Result.Error) {
        action(exception)
    }
    return this
}