package com.example.aeoncompose.di.usecase

import com.example.aeoncompose.api.RequestService
import com.example.aeoncompose.api.RequestState
import com.example.aeoncompose.api.UiState
import com.example.aeoncompose.api.process_api.Repo
import com.example.aeoncompose.data.response.LoginResponse
import com.example.aeoncompose.utils.JSON
import kotlinx.coroutines.flow.flow

class LoginUseCase(private val request: RequestService) {

    operator fun invoke(repo: Repo) = flow<UiState<LoginResponse>> {
        request.work(
            onSuccess = {
                emit(UiState(RequestState.SUCCESS, JSON.decode(it.value.data(), LoginResponse::class.java)))
            },
            onError = {
                emit(UiState(RequestState.FAIL, message = it.message))
            }
        ).post(repo)
    }
}