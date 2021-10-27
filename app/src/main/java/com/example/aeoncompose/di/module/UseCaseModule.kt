package com.example.aeoncompose.di.module

import com.example.aeoncompose.api.RequestService
import com.example.aeoncompose.di.usecase.GetProvince
import com.example.aeoncompose.di.usecase.GetResource
import com.example.aeoncompose.di.usecase.GetSync
import com.example.aeoncompose.di.usecase.PreloadUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun providePreloadUseCase(requestService: RequestService): PreloadUseCase {
        return PreloadUseCase(
            sync = GetSync(requestService),
            resource = GetResource(requestService),
            province = GetProvince(requestService)
        )
    }
}