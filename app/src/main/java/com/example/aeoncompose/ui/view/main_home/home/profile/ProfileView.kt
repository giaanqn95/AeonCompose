package com.example.aeoncompose.ui.view.main_home.home.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.aeoncompose.extensions.onClick
import com.example.aeoncompose.ui.navigation.EnumProfileScreen

@Composable
fun ProfileScreen(navHostController: NavHostController) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
        .onClick {
            navHostController.navigate(EnumProfileScreen.EditProfile.name)
//            DialogSingle(message = "Abc")
        })
}