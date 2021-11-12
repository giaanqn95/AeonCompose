package com.example.aeoncompose.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.aeoncompose.R
import com.example.aeoncompose.ui.view.main_home.MainScreen

enum class EnumMainScreen {
    Main;

    companion object {
        fun getName() = "MainScreen"
    }
}

fun NavGraphBuilder.navigationMain(navController: NavHostController) {
    navigation(startDestination = EnumMainScreen.Main.name, route = EnumMainScreen.getName()) {
        composable(EnumMainScreen.Main.name) {
            MainScreen(navHostController = navController)
        }
    }
}

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object Home : NavigationItem(EnumNavigationScreen.Home.name, R.drawable.ic_home, "Home")
    object Shopping : NavigationItem(EnumNavigationScreen.Shopping.name, R.drawable.ic_shopping, "Shopping")
    object Other : NavigationItem(EnumNavigationScreen.Other.name, R.drawable.ic_other, "Other")
}

enum class EnumNavigationScreen {
    Home,
    Shopping,
    Other;
}