package com.example.aeoncompose.data.response

class LoginResponse(val login_user: ProfileResponse = ProfileResponse(), val access_token: String = "")

class ProfileResponse(
    val full_name: String = "",
    val gender: Int = -1,
    val avatar: String = "",
    val phone_number: String = "",
    val id: String = "",
    val point: Int = -1
) {

}
