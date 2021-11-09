package com.example.aeoncompose.ui.view.preload

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.example.aeoncompose.api.RequestState
import com.example.aeoncompose.api.UiState
import com.example.aeoncompose.base.BaseViewModel
import com.example.aeoncompose.data.response.SyncResponse
import com.example.aeoncompose.data.room_data.Authentic
import com.example.aeoncompose.di.usecase.PreloadUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class PreloadViewModel @Inject constructor(
    private val request: PreloadUseCase,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<PreloadRepo>() {

    private val _uiStateSync = mutableStateOf(UiState<Authentic>())
    val uiStateSync: State<UiState<Authentic>> = _uiStateSync

    init {
        getSyncData()
        savedStateHandle.get<UiState<Authentic>>("sync")?.let {
            _uiStateSync.value = it
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getSyncData() = viewModelScope {
//        request.sync(repo.repoGetSync()).onStart {
//        }.onCompletion {
//        }.flatMapConcat {
//            getResource(it).flatMapConcat { getProvince(it) }
//        }.onEach {
//        }.collect {
//            _uiStateSync.value = it
//        }
        request.sync(repo.repoGetSync()).flatMapConcat {
            getResource(it).flatMapConcat { getProvince(it) }
        }.collect { checkStateSyncData(it) }
    }

    private fun getResource(uiState: UiState<SyncResponse>) = flow<UiState<SyncResponse>> {
        request.resource(repo.repoGetResource()).collect {
            if (it.state == RequestState.SUCCESS)
                emit(uiState)
            else
                emit(UiState(RequestState.FAIL))
        }
    }

    private fun getProvince(uiState: UiState<SyncResponse>) = flow<UiState<SyncResponse>> {
        request.province(repo.repoGetProvince()).collect {
            if (it.state == RequestState.SUCCESS)
                emit(uiState)
            else
                emit(UiState(RequestState.FAIL))
        }
    }

    private suspend fun checkStateSyncData(uiState: UiState<*>) {
        when (uiState.state) {
            RequestState.SUCCESS -> {
                request.getAuthentic().collect {
                    if (it.state == RequestState.SUCCESS && !it.result?.token.isNullOrEmpty())
                        _uiStateSync.value = it
                    else _uiStateSync.value = UiState(RequestState.FAIL)
                }
            }
            RequestState.FAIL -> {
                _uiStateSync.value = UiState(RequestState.FAIL)
            }
        }
    }
}