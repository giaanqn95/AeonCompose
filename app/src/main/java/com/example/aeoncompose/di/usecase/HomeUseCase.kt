package com.example.aeoncompose.di.usecase

import com.example.aeoncompose.api.Repo
import com.example.aeoncompose.api.RequestService
import com.example.aeoncompose.api.RequestState
import com.example.aeoncompose.api.UiState
import com.example.aeoncompose.data.response.PromotionResponse
import com.example.aeoncompose.utils.JSON
import kotlinx.coroutines.flow.flow

class HomeUseCase(val getBanner: GetBanner) {
}

class GetBanner(private val request: RequestService) {

    operator fun invoke(repo: Repo) = flow<UiState<PromotionResponse>> {
        request.work(
            onSuccess = {
                emit(UiState(RequestState.SUCCESS, JSON.decode(it.value.data(), PromotionResponse::class.java)))
            },
            onError = {
                emit(UiState(RequestState.ERROR, message = it.message))
            }
        ).request(repo)
    }
}