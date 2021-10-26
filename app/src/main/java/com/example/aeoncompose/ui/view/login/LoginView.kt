package com.example.aeoncompose.ui.view.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.aeoncompose.ui.HomeScreen
import com.example.aeoncompose.ui.KYCScreen
import com.example.aeoncompose.utils.LogCat

@Composable
fun LoginView(navHostController: NavHostController) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier
        .background(color = Color.LightGray)
        .size(100.dp)
        .clickable {
            LogCat.d("LoginView")
            navHostController.navigate(HomeScreen.Home.name) {
                launchSingleTop = true
                popUpTo(KYCScreen.Preload.name) {
                    inclusive = true
                }
            }
        }) {
        Text(text = "LoginView")
    }
}