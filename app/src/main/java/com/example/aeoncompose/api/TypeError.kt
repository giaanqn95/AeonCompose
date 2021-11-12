package com.example.aeoncompose.api

enum class TypeError(val errorName: String = "") {
    NO_INTERNET,
    REDIRECT_RESPONSE_ERROR,
    CLIENT_REQUEST_ERROR,
    SERVER_RESPONSE_ERROR,
    ANOTHER_ERROR;
}