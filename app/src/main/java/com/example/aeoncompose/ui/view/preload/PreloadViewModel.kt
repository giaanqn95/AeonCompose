package com.example.aeoncompose.ui.view.preload

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.aeoncompose.api.PreloadRepo
import com.example.aeoncompose.api.UiState
import com.example.aeoncompose.base.BaseViewModel
import com.example.aeoncompose.data.SyncResponse
import com.example.aeoncompose.di.usecase.PreloadUseCase
import com.example.aeoncompose.extensions.onExpiredToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
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

    private fun getSyncData() {
        request.preload(repo.repoGetResource()).onStart {
            _isLoading.value = true
        }.onCompletion {
            _isLoading.value = false
            savedStateHandle.get<UiState<SyncResponse>>("sync")
        }.onEach {
            _uiStateSync.value = it
        }.onExpiredToken().launchIn(viewModelScope)
    }
}