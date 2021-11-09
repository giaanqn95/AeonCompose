package com.example.aeoncompose.ui.view.home

import androidx.lifecycle.SavedStateHandle
import com.example.aeoncompose.base.BaseViewModel
import com.example.aeoncompose.di.usecase.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val request: HomeUseCase,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<HomeRepo>() {
}