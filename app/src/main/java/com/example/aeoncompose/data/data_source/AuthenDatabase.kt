package com.example.aeoncompose.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.aeoncompose.data.response.ProfileResponse
import com.example.aeoncompose.data.room_data.Authentic
import com.example.aeoncompose.utils.JSON
import com.example.aeoncompose.utils.LogCat

@Database(
    entities = [Authentic::class],
    version = 1
)
@TypeConverters(ProfileConverter::class)
abstract class AuthenDatabase : RoomDatabase() {

    abstract val authenDAO: AuthenDAO

    companion object {
        const val DATABASE_NAME = "authen_db"
    }
}

class ProfileConverter {

    @TypeConverter
    fun convertStringToProfile(value: String): ProfileResponse {
        return try {
            JSON.decode(value, ProfileResponse::class.java) ?: ProfileResponse()
        } catch (e: Exception) {
            LogCat.e(e.message)
            return ProfileResponse()
        }
    }

    @TypeConverter
    fun convertProfileToString(value: ProfileResponse): String {
        return try {
            JSON.encode(value)
        } catch (e: Exception) {
            LogCat.e(e.message)
            return ""
        }
    }
}