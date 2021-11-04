package com.example.aeoncompose.ui.view.preload

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.example.aeoncompose.api.PreloadRepo
import com.example.aeoncompose.api.RequestState
import com.example.aeoncompose.api.UiState
import com.example.aeoncompose.base.BaseViewModel
import com.example.aeoncompose.data.response.SyncResponse
import com.example.aeoncompose.di.usecase.PreloadUseCase
import com.example.aeoncompose.utils.LogCat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class PreloadViewModel @Inject constructor(
    private val request: PreloadUseCase,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<PreloadRepo>() {

    private val _uiStateSync = mutableStateOf(UiState<SyncResponse>())
    val uiStateSync: State<UiState<SyncResponse>> = _uiStateSync

    init {
        getSyncData()
        savedStateHandle.get<UiState<SyncResponse>>("sync")?.let {
            _uiStateSync.value = it
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getSyncData() = viewModelScope {
        request.sync(repo.repoGetSync()).onStart {
            LogCat.d("getSyncData onStart")
        }.onCompletion {
            LogCat.d("getSyncData onCompletion")
        }.flatMapConcat {
            LogCat.d("getSyncData flatMapConcat")
            getResource(it).flatMapConcat { getProvince(it) }
        }.onEach {
            LogCat.d("getSyncData onEach")
        }.collect {
            LogCat.d("getSyncData collect")
            _uiStateSync.value = it
        }
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
}