package com.example.aeoncompose.ui.view.preload

import com.example.aeoncompose.api.BaseRepo
import com.example.aeoncompose.api.Repo
import com.example.aeoncompose.api.TypeRepo

interface PreloadRepo : BaseRepo {

    fun repoGetSync(): Repo {
        return Repo(
            getHeaders(),
            "sync",
            "",
            "",
            TypeRepo.GET
        )
    }

    fun repoGetResource(): Repo {
        return Repo(
            getHeaders(),
            "resources",
            "",
            "",
            TypeRepo.GET
        )
    }

    fun repoGetProvince(): Repo {
        return Repo(
            getHeaders(),
            "get_provices",
            "",
            "",
            TypeRepo.GET
        )
    }
}