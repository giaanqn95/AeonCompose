package com.example.aeoncompose.ui.view.login

import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.aeoncompose.api.LoginRepo
import com.example.aeoncompose.api.UiState
import com.example.aeoncompose.base.BaseViewModel
import com.example.aeoncompose.data.response.LoginResponse
import com.example.aeoncompose.di.usecase.LoginUseCase
import com.example.aeoncompose.extensions.checkStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val request: LoginUseCase,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<LoginRepo>() {

    val _uiStateLogin = mutableStateOf(UiState<LoginResponse>())
    val uiStateLogin: State<UiState<LoginResponse>> = _uiStateLogin

    var _isLoading = mutableStateOf(false)
    var isLoading: State<Boolean> = _isLoading
    init {
        savedStateHandle.get<UiState<LoginResponse>>("sync")?.let {
            _uiStateLogin.value = it
        }
    }
    @Stable
    @OptIn(ExperimentalCoroutinesApi::class)
    fun postLogin(phoneNumber: String, password: String) = viewModelScope {
        request(repo.repoLogin(phoneNumber, password)).onStart {
            _isLoading.value = true
        }.onCompletion {
            _isLoading.value = false
        }.onEach {
            checkStates(it,
                success = { _uiStateLogin.value = it },
                fail = { _uiStateLogin.value = it })
        }.launchIn(viewModelScope)
    }
}