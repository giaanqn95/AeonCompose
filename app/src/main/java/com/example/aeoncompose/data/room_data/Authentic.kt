package com.example.aeoncompose.data.room_data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.aeoncompose.data.response.ProfileResponse

@Entity
class Authentic(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val profileResponse: ProfileResponse,
    val token: String
)