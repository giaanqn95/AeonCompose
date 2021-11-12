package com.example.aeoncompose.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.aeoncompose.api.TypeError
import com.example.aeoncompose.ui.MainActivity

class ErrorReceiver constructor(var activity: MainActivity) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        LogCat.d("onReceive get action " + intent.action)
        when (intent.action) {
            TypeError.NO_INTERNET.name -> activity.errorNoInternet(TypeError.NO_INTERNET)
            TypeError.REDIRECT_RESPONSE_ERROR.name -> activity.redirectError(TypeError.REDIRECT_RESPONSE_ERROR)
            TypeError.CLIENT_REQUEST_ERROR.name -> activity.clientRequestError(TypeError.CLIENT_REQUEST_ERROR)
            TypeError.SERVER_RESPONSE_ERROR.name -> activity.serverResponseError(TypeError.SERVER_RESPONSE_ERROR)
            else -> activity.anotherError(TypeError.SERVER_RESPONSE_ERROR)
        }
    }
}