package com.example.aeoncompose.ui

import androidx.compose.runtime.Composable

enum class KYCScreen(val body: @Composable ((String) -> Unit) -> Unit) {

    Preload(body = { }),
    StartKYC(body = { }),
    Login(body = { });

    @Composable
    fun content(onScreenChange: (String) -> Unit) {
        body(onScreenChange)
    }

    companion object {
        fun getName() = "KYCScreens"
    }
}

enum class HomeScreen(val body: @Composable ((String) -> Unit) -> Unit) {

    Home(body = {});

    companion object {
        fun getName() = "HomeScreens"
    }
}