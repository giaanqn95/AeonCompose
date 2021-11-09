package com.example.aeoncompose.di.usecase

import com.example.aeoncompose.api.Repo
import com.example.aeoncompose.api.RequestService
import com.example.aeoncompose.api.RequestState
import com.example.aeoncompose.api.UiState
import com.example.aeoncompose.api.repository.AuthenRepository
import com.example.aeoncompose.data.response.ProvinceResponse
import com.example.aeoncompose.data.response.ResourceResponse
import com.example.aeoncompose.data.response.SyncResponse
import com.example.aeoncompose.utils.JSON
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow

class PreloadUseCase(
    val sync: GetSync,
    val resource: GetResource,
    val province: GetProvince,
    val getAuthentic: GetAuthentic
)

class GetSync(private val request: RequestService) {
    operator fun invoke(repo: Repo) = channelFlow<UiState<SyncResponse>> {
        request.work(
            onSuccess = {
                send(UiState(RequestState.SUCCESS, JSON.decode(it.value.data(), SyncResponse::class.java)))
            },
            onError = {
                send(UiState(RequestState.FAIL, message = it.message))
            }
        ).get(repo)
    }
}

class GetResource(private val request: RequestService) {
    operator fun invoke(repo: Repo) = channelFlow<UiState<ResourceResponse>> {
        request.work(
            onSuccess = {
                send(UiState(RequestState.SUCCESS, JSON.decode(it.value.data(), ResourceResponse::class.java)))
            },
            onError = {
                send(UiState(RequestState.FAIL, message = it.message))
            }
        ).get(repo)
    }
}

class GetProvince(private val request: RequestService) {
    operator fun invoke(repo: Repo) = flow<UiState<ProvinceResponse>> {
        request.work(
            onSuccess = {
                emit(UiState(RequestState.SUCCESS, JSON.decode(it.value.data(), ProvinceResponse::class.java)))
            },
            onError = {
                emit(UiState(RequestState.FAIL, message = it.message))
            }
        ).get(repo)
    }
}

class GetAuthentic(private val authenRepository: AuthenRepository) {
    operator fun invoke() = flow {
        emit(UiState(RequestState.SUCCESS, authenRepository.getAuthen()))
    }
}