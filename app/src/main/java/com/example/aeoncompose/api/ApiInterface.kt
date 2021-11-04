package com.example.aeoncompose.api

import com.google.gson.JsonElement
import okhttp3.MultipartBody
import retrofit2.http.*


/**
 * Copyright by Intelin.
 * Creator: Tran Do Gia An
 * Date: 22/03/2019
 * Time: 10:33 AM
 */
interface ApiInterface {
    @GET
    suspend fun get(@HeaderMap headers: Map<String, String>, @Url url: String): JsonElement

    @POST
    suspend fun post(
        @HeaderMap headers: Map<String, String>,
        @Url url: String,
        @Body o: Any?
    ): JsonElement

    @PUT
    suspend fun put(
        @HeaderMap headers: Map<String, String>,
        @Url url: String,
        @Body o: Any?
    ): JsonElement

    @HTTP(method = "DELETE", hasBody = true)
    suspend fun delete(
        @HeaderMap headers: Map<String, String>,
        @Url url: String,
        @Body o: Any?
    ): JsonElement

    @Multipart
    @POST
    suspend fun uploadFile(
        @HeaderMap headers: Map<String, String>,
        @Url url: String,
        @Part file: MultipartBody.Part?
    ): JsonElement
}