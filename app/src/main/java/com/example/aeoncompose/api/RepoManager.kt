package com.example.aeoncompose.api

import com.example.aeoncompose.data.ProfileService
import com.example.aeoncompose.di.module.ApiClientModule.AEON_API_KEY
import com.example.aeoncompose.di.module.ApiClientModule.HEADER_API_KEY
import com.example.aeoncompose.di.module.ApiClientModule.HEADER_AUTHORIZATION
import com.example.aeoncompose.di.module.ApiClientModule.HEADER_LANG
import com.example.aeoncompose.di.module.ApiClientModule.HEADER_UUID
import com.example.aeoncompose.ui.view.home.HomeRepo
import com.example.aeoncompose.ui.view.login.LoginRepo
import com.example.aeoncompose.ui.view.preload.PreloadRepo
import java.util.*
import kotlin.collections.HashMap

class RepoManager :
    PreloadRepo,
    LoginRepo,
    HomeRepo

interface BaseRepo {
    fun getHeaders(): HashMap<String, String> {
        val headers = HashMap<String, String>()
        if (ProfileService.authen.access_token.isNotEmpty()) headers[HEADER_AUTHORIZATION] = ProfileService.authen.access_token
        headers[HEADER_LANG] = "vi"
        headers[HEADER_API_KEY] = AEON_API_KEY
        headers[HEADER_UUID] = UUID.randomUUID().toString()
        return headers
    }
}