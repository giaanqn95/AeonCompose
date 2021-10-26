package com.example.aeoncompose.di.usecase

import com.example.aeoncompose.api.RequestService
import com.example.aeoncompose.api.RequestState
import com.example.aeoncompose.api.UiState
import com.example.aeoncompose.api.process_api.Repo
import com.example.aeoncompose.data.SyncResponse
import com.example.aeoncompose.utils.JSON
import kotlinx.coroutines.flow.flow

class PreloadUseCase(val preload: GetPreload,
                      val resource: GetResource)

class GetPreload(private val request: RequestService) {
    operator fun invoke(repo: Repo) = flow<UiState<SyncResponse>> {
        request.work(
            onSuccess = {
                emit(UiState(RequestState.SUCCESS, JSON.decode(it.value.data(), SyncResponse::class.java)))
            },
            onError = {
                emit(UiState(RequestState.FAIL, message = it.message))
            }
        ).get(repo)
    }
}

class GetResource(private val request: RequestService) {
    operator fun invoke(repo: Repo) = flow<UiState<SyncResponse>> {
        request.work(
            onSuccess = {
                emit(UiState(RequestState.SUCCESS, JSON.decode(it.value.data(), SyncResponse::class.java)))
            },
            onError = {
                emit(UiState(RequestState.FAIL, message = it.message))
            }
        ).get(repo)
    }
}