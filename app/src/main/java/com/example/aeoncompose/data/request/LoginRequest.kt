package com.example.aeoncompose.data.request

import android.os.Build
import kotlinx.serialization.Serializable

data class LoginRequest(
    val phone_number: String,
    val password: String,
    val device_name: String = Build.BRAND + " " + Build.MODEL.replace(Build.BRAND.toRegex(), ""),
    val os: String = "Android " + Build.VERSION.RELEASE
)