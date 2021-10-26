package com.example.aeoncompose.api

import com.example.aeoncompose.api.process_api.Repo
import com.example.aeoncompose.api.process_api.TypeRepo

class RepoManager : LoginRepoUseCase,
    RegisterRepoUseCase,
    PreloadRepo

interface LoginRepoUseCase {

    fun repoLoginUsername(phoneNumber: String): Repo = Repo(
        HashMap(),
        "user/register/",
        if (phoneNumber.startsWith("0")) phoneNumber else "0$phoneNumber",
        "USERNAME_2000",
        TypeRepo.GET
    )

    fun repoSync(): Repo {
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
}

interface PreloadRepo {

    fun repoGetResource(): Repo {
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