package com.example.aeoncompose.ui.view.main_home.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.aeoncompose.api.RequestState
import com.example.aeoncompose.api.UiState
import com.example.aeoncompose.base.BaseViewModel
import com.example.aeoncompose.data.response.PromotionResponse
import com.example.aeoncompose.di.usecase.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val request: HomeUseCase,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<HomeRepo>() {

    private val _uiStateHomeBanner = mutableStateOf(UiState<PromotionResponse>())
    val uiStateHomeBanner: State<UiState<PromotionResponse>> = _uiStateHomeBanner

    init {
        getHomeBanner()
    }

    private fun getHomeBanner() {
        request.getBanner(repo.repoGetBanner())
            .onEach {
                _uiStateHomeBanner.value = it
            }.catch { e ->
                _uiStateHomeBanner.value = UiState(RequestState.ERROR, message = e.message)
            }.launchIn(viewModelScope)
    }
}