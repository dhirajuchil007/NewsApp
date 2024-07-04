package com.velocityappsdj.sortlymobileproject.common

sealed interface Error


public enum class NetworkError : Error {
    BAD_REQUEST,
    UNAUTHORIZED,
    FORBIDDEN,
    NOT_FOUND,
    METHOD_NOT_ALLOWED,
    REQUEST_TIMEOUT,
    UNKNOWN,
    SERVER_ERROR,
    CHECK_CONNECTION,
    MAX_RESULTS_REACHED,
    TOO_MANY_REQUESTS
}

fun getNetworkErrorFromCode(code: Int): NetworkError {
    return when (code) {
        400 -> NetworkError.BAD_REQUEST
        401 -> NetworkError.UNAUTHORIZED
        403 -> NetworkError.FORBIDDEN
        404 -> NetworkError.NOT_FOUND
        405 -> NetworkError.METHOD_NOT_ALLOWED
        408 -> NetworkError.REQUEST_TIMEOUT
        426 -> NetworkError.MAX_RESULTS_REACHED
        429 -> NetworkError.TOO_MANY_REQUESTS
        500, 502, 503 -> NetworkError.SERVER_ERROR
        else -> NetworkError.UNKNOWN
    }
}
