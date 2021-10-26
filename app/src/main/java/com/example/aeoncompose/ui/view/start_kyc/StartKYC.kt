package com.example.aeoncompose.ui.view.start_kyc

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
import com.example.aeoncompose.ui.KYCScreen
import com.example.aeoncompose.utils.LogCat


@Composable
fun StartKYC(navHostController: NavHostController) {
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(color = Color.Yellow)
            .size(100.dp)
            .clickable {
                LogCat.d(
                    "StartKYC ${navHostController.currentBackStackEntry?.arguments?.getString("name")} " +
                            "- ${navHostController.currentBackStackEntry?.arguments?.getInt("old")}"
                )
                navHostController.navigate(KYCScreen.Login.name) {
//                    launchSingleTop = true
//                    popUpTo(KYCScreen.Preload.name) {
//                        inclusive = true
//                    }
                }
            }) {
        Text(text = "StartKYC", color = Color.Black)
    }
}