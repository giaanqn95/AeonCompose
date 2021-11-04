package com.example.aeoncompose.data.response

class SyncResponse(
    val ANDROID_APP_VERSION: Int,
    val SERVER_VERSION: Int,
    val ANDROID_FORCE_UPDATE: String?
) {
    fun needUpdate() = ANDROID_FORCE_UPDATE != null && ANDROID_FORCE_UPDATE != "0"
}