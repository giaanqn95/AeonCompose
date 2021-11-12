package com.example.aeoncompose.ui.view.main_home.home

import com.example.aeoncompose.api.BaseRepo
import com.example.aeoncompose.api.Repo


interface HomeRepo : BaseRepo {
    fun repoGetBanner(): Repo {
        return Repo(
            getHeaders(false),
            "promotionevent_highlight",
            message = ""
        )
    }
}