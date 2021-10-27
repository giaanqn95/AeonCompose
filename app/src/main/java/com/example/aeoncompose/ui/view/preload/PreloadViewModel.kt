package com.example.aeoncompose.ui.view.preload

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.example.aeoncompose.api.PreloadRepo
import com.example.aeoncompose.api.UiState
import com.example.aeoncompose.base.BaseViewModel
import com.example.aeoncompose.data.SyncResponse
import com.example.aeoncompose.di.usecase.PreloadUseCase
import com.example.aeoncompose.extensions.checkStates
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
            _isLoading.value = true
        }.onCompletion {
            _isLoading.value = false
        }.onEach {
            checkStates(it, success = {
                merge(request.resource(repo.repoGetResource()).onEach { resource ->
                    LogCat.d("BBBBB ${resource.state.name}")
                }, request.province(repo.repoGetProvince()).onEach { province ->
                    LogCat.d("BBBBB ${province.state.name}")
                }).collect { collect->
                    LogCat.d("BBBBB ${collect.state.name}")
                }
            })
        }.collect {
            _uiStateSync.value = it
        }
    }
}