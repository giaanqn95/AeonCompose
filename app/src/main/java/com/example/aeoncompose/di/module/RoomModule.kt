package com.example.aeoncompose.di.module

import android.app.Application
import androidx.room.Room
import com.example.aeoncompose.api.repository.AuthenRepository
import com.example.aeoncompose.data.data_source.AuthenDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideRoomAuthenDatabase(app: Application): AuthenDatabase {
        return Room.databaseBuilder(
            app,
            AuthenDatabase::class.java,
            AuthenDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun authenRepository(db: AuthenDatabase): AuthenRepository{
        return AuthenRepository(db.authenDAO)
    }
}