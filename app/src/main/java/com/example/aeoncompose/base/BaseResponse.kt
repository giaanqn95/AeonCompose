package com.example.aeoncompose.base

import com.google.gson.JsonElement
import java.util.*

/**
 * Copyright by Intelin.
 * Creator: Tran Do Gia An
 * Date: 22/03/2019
 * Time: 12:34 PM
 */
class BaseResponse(
    val data: JsonElement? = null,
    val message: String = "",
    val code: String = "",
    val status: Int = 0,
){
    fun data(): String {
        return Objects.toString(data)
    }
}