package com.example.aeoncompose.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.aeoncompose.ui.view.main_home.home.edit_profile.EditProfileScreen
import com.example.aeoncompose.ui.view.main_home.home.profile.ProfileScreen

enum class EnumProfileScreen {

    Profile,
    EditProfile;

    companion object {
        fun getName() = "ProfileScreen"
    }
}

fun NavGraphBuilder.navigationProfile(navController: NavHostController) {
    navigation(startDestination = EnumProfileScreen.Profile.name, route = EnumProfileScreen.getName()) {
        composable(EnumProfileScreen.Profile.name) {
            ProfileScreen(navHostController = navController)
        }
        composable(EnumProfileScreen.EditProfile.name) {
            EditProfileScreen(navHostController = navController)
        }
    }
}
