package com.example.aeoncompose.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aeoncompose.api.RequestState
import com.example.aeoncompose.api.TypeError
import com.example.aeoncompose.api.UiState
import com.example.aeoncompose.api.repository.AuthenRepository
import com.example.aeoncompose.data.ProfileService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val authenRepository: AuthenRepository) : ViewModel() {

    val _uiStateError = mutableStateOf(UiState<Any>())
    val uiStateError: State<UiState<*>> = _uiStateError
    val _uiStateExpiredToken = mutableStateOf(UiState<Any>())
    val uiStateExpiredToken: State<UiState<*>> = _uiStateExpiredToken

    fun handleTypeError(typeTypeError: TypeError) {
        when (typeTypeError) {
            TypeError.NO_INTERNET -> _uiStateError.value = UiState(RequestState.ERROR, message = "Không có internet")
            TypeError.REDIRECT_RESPONSE_ERROR -> _uiStateError.value = UiState(RequestState.ERROR, message = "Lỗi 3xx")
            TypeError.SERVER_RESPONSE_ERROR -> _uiStateError.value = UiState(RequestState.ERROR, message = "Lỗi 5xx")
        }
    }

    fun handleExpiredToken() = viewModelScope.launch {
        ProfileService.authen = null
        authenRepository.deleteAuthen()
        _uiStateExpiredToken.value = UiState(RequestState.ERROR)
    }
}