package com.example.aeoncompose.api

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class Error(val error: String, val message: String? = null) : ResultWrapper<Nothing>()
    data class ErrorThrowable(val error: String, val message: String? = null) : ResultWrapper<Nothing>()
}