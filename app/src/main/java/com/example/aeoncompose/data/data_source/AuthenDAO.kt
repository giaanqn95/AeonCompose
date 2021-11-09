package com.example.aeoncompose.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.aeoncompose.data.room_data.Authentic

@Dao
interface AuthenDAO {

    @Query("SELECT * FROM authentic")
    suspend fun getAuthentic(): Authentic?

    @Query("SELECT * FROM authentic WHERE id = :id")
    suspend fun getAuthenticById(id: Int): Authentic

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuthentic(authen: Authentic)

    @Query("DELETE FROM authentic")
    suspend fun delete()
}