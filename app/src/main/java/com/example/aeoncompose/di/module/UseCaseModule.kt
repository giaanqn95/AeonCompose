package com.example.aeoncompose.di.module

import com.example.aeoncompose.api.RequestService
import com.example.aeoncompose.di.usecase.*
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

    @Provides
    fun provideLoginUseCase(requestService: RequestService): LoginUseCase {
        return LoginUseCase(request = requestService)
    }
}