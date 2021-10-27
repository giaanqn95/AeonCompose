package com.example.aeoncompose.ui.view.preload

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.aeoncompose.R
import com.example.aeoncompose.api.RequestState
import com.example.aeoncompose.api.UiState
import com.example.aeoncompose.ui.KYCScreen
import com.example.aeoncompose.utils.LogCat

@Composable
fun PreloadView(navHostController: NavHostController, preloadViewModel: PreloadViewModel = hiltViewModel()) {
    val syncState = preloadViewModel.uiStateSync.value
    LogCat.d("AAAAA ${syncState.state.name}")
    PreloadContent(navHostController = navHostController, syncState)
}

@Composable
fun PreloadContent(navHostController: NavHostController, uiState: UiState<*>) {
    when (uiState.state) {
        RequestState.SUCCESS -> {
            LaunchedEffect(key1 = Unit, block = {
                navHostController.navigate(KYCScreen.StartKYC.name + "/An/26") {
                    popUpTo(KYCScreen.Preload.name) { inclusive = true }
                }
            })
        }
        RequestState.FAIL -> {
            Box(contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(color = Color.White)
                    .size(100.dp)
                    .clickable {
                        LogCat.d("PreloadView")
                        navHostController.navigate(KYCScreen.StartKYC.name + "/An/26") {
                        }
                    }) {
                Text(text = "PreloadView", color = Color.Black)
            }
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