package com.example.aeoncompose.utils

import android.util.DisplayMetrics
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp

object ScreenUtils {

    var screenWidth = 0F
    var screenHeight = 0F
    var rateDifference = 0F

    fun calculatePercentScreen(displayMetrics: DisplayMetrics) {
        val designWidth = 412F
        val designHeight = 870F
        val designRatio = designWidth / designHeight
        val currentDeviceRatio = displayMetrics.widthPixels.toFloat() / displayMetrics.heightPixels.toFloat()
        rateDifference = currentDeviceRatio / designRatio
    }

    @Stable
    val Float.rdp: Dp
        get() = Dp(value = this * rateDifference)
    @Stable
    val Int.rdp: Dp
        get() = Dp(value = this * rateDifference)
    @Stable
    val Long.rdp: Dp
        get() = Dp(value = this * rateDifference)
}
