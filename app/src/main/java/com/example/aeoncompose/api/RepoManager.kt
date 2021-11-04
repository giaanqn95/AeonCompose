package com.example.aeoncompose.api

import com.example.aeoncompose.api.process_api.Repo
import com.example.aeoncompose.api.process_api.TypeRepo
import com.example.aeoncompose.data.request.LoginRequest

class RepoManager :
    RegisterRepoUseCase,
    PreloadRepo,
    LoginRepo

interface PreloadRepo {

    fun repoGetSync(): Repo {
        val hashMap = HashMap<String, String>()
        hashMap["x-api-key"] = "3EB76D87D97C427943957C555AB0B60847582D38CB1688ED86C59251206305E3"
        return Repo(
            hashMap,
            "sync",
            "",
            "",
            TypeRepo.GET
        )
    }

    fun repoGetResource(): Repo {
        val hashMap = HashMap<String, String>()
        hashMap["x-api-key"] = "3EB76D87D97C427943957C555AB0B60847582D38CB1688ED86C59251206305E3"
        return Repo(
            hashMap,
            "resources",
            "",
            "",
            TypeRepo.GET
        )
    }

    fun repoGetProvince(): Repo {
        val hashMap = HashMap<String, String>()
        hashMap["x-api-key"] = "3EB76D87D97C427943957C555AB0B60847582D38CB1688ED86C59251206305E3"
        return Repo(
            hashMap,
            "get_provices",
            "",
            "",
            TypeRepo.GET
        )
    }
}

interface RegisterRepoUseCase {
    fun repoRegister(phoneNumber: String): Repo = Repo(
        HashMap(),
        "user/register/",
        if (phoneNumber.startsWith("0")) phoneNumber else "0$phoneNumber",
        "USERNAME_2000",
        TypeRepo.GET
    )
}

interface LoginRepo {
    fun repoLogin(phoneNumber: String, password: String): Repo {
        val hashMap = HashMap<String, String>()
        hashMap["x-api-key"] = "3EB76D87D97C427943957C555AB0B60847582D38CB1688ED86C59251206305E3"
        return Repo(
            hashMap,
            "auth",
            message = LoginRequest(phoneNumber, password)
        )
    }
}