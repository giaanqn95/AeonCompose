package com.example.aeoncompose.data.response

class LoginResponse(val login_user: ProfileResponse, val access_token: String)

class ProfileResponse(val full_name: String, val gender: Int, val avatar: String, val phone_number: String, val id: String) {

}
