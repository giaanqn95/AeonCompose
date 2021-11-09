package com.example.aeoncompose.ui.view.home

import com.example.aeoncompose.api.BaseRepo
import com.example.aeoncompose.api.Repo


interface HomeRepo : BaseRepo {
    fun repoGetBanner(): Repo {
        return Repo(
            getHeaders(),
            "auth",
            message = ""
        )
    }
}