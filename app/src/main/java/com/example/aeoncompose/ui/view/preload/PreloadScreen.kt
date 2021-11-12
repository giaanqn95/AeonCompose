package com.example.aeoncompose.ui.view.preload

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.aeoncompose.R
import com.example.aeoncompose.api.RequestState
import com.example.aeoncompose.api.UiState
import com.example.aeoncompose.data.ProfileService
import com.example.aeoncompose.data.response.LoginResponse
import com.example.aeoncompose.data.response.ProfileResponse
import com.example.aeoncompose.data.room_data.Authentic
import com.example.aeoncompose.ui.navigation.EnumKYCScreen
import com.example.aeoncompose.ui.navigation.EnumMainScreen

@Composable
fun PreloadView(navHostController: NavHostController, preloadViewModel: PreloadViewModel = hiltViewModel()) {
    val syncState = preloadViewModel.uiStateSync.value
    PreloadContent(navHostController = navHostController, syncState)
}

@Composable
fun PreloadContent(navHostController: NavHostController, uiState: UiState<Authentic>) {
    when (uiState.state) {
        RequestState.SUCCESS -> {
            LaunchedEffect(key1 = Unit, block = {
                ProfileService.authen = LoginResponse(uiState.result?.profileResponse ?: ProfileResponse(), uiState.result?.token ?: "")
                navHostController.navigate(EnumMainScreen.getName()) {
                    popUpTo(EnumKYCScreen.getName()) { inclusive = true }
                    launchSingleTop = true
                }
            })
        }
        RequestState.ERROR -> {
            LaunchedEffect(key1 = Unit, block = {
                navHostController.navigate(EnumKYCScreen.StartKYC.name) {
                    popUpTo(EnumKYCScreen.Preload.name) { inclusive = true }
                }
            })
        }
        RequestState.NON -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFAA1F87))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bg_splash_screen),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    alignment = Alignment.BottomCenter
                )
                CircularProgressIndicator()
            }
        }
    }
}