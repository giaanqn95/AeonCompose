package com.example.aeoncompose.data.response

import java.io.Serializable

class PromotionResponse : ArrayList<NewPromotion>()
data class NewPromotion(
    val applyend: String?,
    val applystart: String?,
    val contenttype: Int /*1 =link, 0= html*/,
    val description: String,
    val displayend: String,
    val displaystart: String,
    val displaytype: String,
    val id: Int,
    val name: String,
    val order_no: Int,
    val programtype: String,
    val store_code: String,
    val url_image: String,
    val uuid: String,
    val date_format: String?
): Serializable