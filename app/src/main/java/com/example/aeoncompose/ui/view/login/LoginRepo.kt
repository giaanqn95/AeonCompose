package com.example.aeoncompose.ui.view.login

import com.example.aeoncompose.api.BaseRepo
import com.example.aeoncompose.api.Repo
import com.example.aeoncompose.data.request.LoginRequest


interface LoginRepo : BaseRepo {
    fun repoLogin(phoneNumber: String, password: String): Repo {
        return Repo(
            getHeaders(),
            "auth",
            message = LoginRequest(phoneNumber, password)
        )
    }
}