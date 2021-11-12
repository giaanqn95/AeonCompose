package com.example.aeoncompose.di.usecase

import com.example.aeoncompose.api.Repo
import com.example.aeoncompose.api.RequestService
import com.example.aeoncompose.api.RequestState
import com.example.aeoncompose.api.UiState
import com.example.aeoncompose.api.repository.AuthenRepository
import com.example.aeoncompose.data.response.LoginResponse
import com.example.aeoncompose.data.response.ProfileResponse
import com.example.aeoncompose.data.room_data.Authentic
import com.example.aeoncompose.utils.JSON
import kotlinx.coroutines.flow.flow

class LoginUseCase(private val request: RequestService, private val authenRepository: AuthenRepository) {

    operator fun invoke(repo: Repo) = flow<UiState<LoginResponse>> {
        request.work(
            onSuccess = {
                val response = JSON.decode(it.value.data(), LoginResponse::class.java)
                authenRepository.insertAuthen(Authentic(profileResponse = response?.login_user ?: ProfileResponse(), token = response?.access_token ?: ""))
                emit(UiState(RequestState.SUCCESS, response))
            },
            onError = {
                emit(UiState(RequestState.ERROR, message = it.message))
            }
        ).request(repo)
    }
}