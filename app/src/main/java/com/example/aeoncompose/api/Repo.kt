package com.example.aeoncompose.api

/**
 * Created by giaan on 11/18/20.
 * Company: Intelin
 * Email: antranit95@gmail.com
 */
class Repo(
    val headers: Map<String, String>,
    val url: String,
    val message: Any? = null,
    val codeRequired: Any? = null,
    val typeRepo: TypeRepo = TypeRepo.GET,
)

enum class TypeRepo {
    GET, POST, PUT, DELETE
}