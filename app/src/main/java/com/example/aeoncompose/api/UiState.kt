package com.example.aeoncompose.api

class UiState<T>(var state: RequestState = RequestState.NON, var result: T? = null, var message: String? = null, var code: Int? = null)

enum class RequestState(val state: Int) {
    NON(-1), SUCCESS(1), FAIL(0);

    companion object {
        fun find(state: Int): RequestState {
            values().forEach {
                if (it.state == state) {
                    return it
                }
            }
            return NON
        }
    }
}