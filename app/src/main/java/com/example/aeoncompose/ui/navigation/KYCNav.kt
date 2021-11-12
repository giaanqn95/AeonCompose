package com.example.aeoncompose.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.aeoncompose.ui.view.login.LoginView
import com.example.aeoncompose.ui.view.preload.PreloadView
import com.example.aeoncompose.ui.view.start_kyc.StartKYC

enum class EnumKYCScreen {

    Preload,
    StartKYC,
    Login;

    companion object {
        fun getName() = "KYCScreens"
    }
}

fun NavGraphBuilder.navigationKYC(navController: NavHostController) {
    navigation(startDestination = EnumKYCScreen.Preload.name, route = EnumKYCScreen.getName()) {
        composable(EnumKYCScreen.Preload.name) { PreloadView(navHostController = navController) }
        composable(EnumKYCScreen.StartKYC.name) { StartKYC(navHostController = navController) }
        composable(EnumKYCScreen.Login.name) { LoginView(navHostController = navController) }
    }
}