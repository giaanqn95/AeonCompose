package com.example.aeoncompose.api.repository

import com.example.aeoncompose.data.data_source.AuthenDAO
import com.example.aeoncompose.data.room_data.Authentic

class AuthenRepository(private val dao: AuthenDAO) {

    suspend fun getAuthen(): Authentic? {
        return dao.getAuthentic()
    }

    suspend fun insertAuthen(authentic: Authentic) {
        dao.insertAuthentic(authentic)
    }

    suspend fun deleteAuthen() {
        dao.delete()
    }
}