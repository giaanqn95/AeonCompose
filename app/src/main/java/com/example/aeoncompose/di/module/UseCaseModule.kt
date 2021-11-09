package com.example.aeoncompose.di.module

import com.example.aeoncompose.api.RequestService
import com.example.aeoncompose.api.repository.AuthenRepository
import com.example.aeoncompose.di.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun providePreloadUseCase(requestService: RequestService, authenRepository: AuthenRepository): PreloadUseCase {
        return PreloadUseCase(
            sync = GetSync(requestService),
            resource = GetResource(requestService),
            province = GetProvince(requestService),
            getAuthentic = GetAuthentic(authenRepository)
        )
    }

    @Provides
    fun provideLoginUseCase(requestService: RequestService, authenRepository: AuthenRepository): LoginUseCase {
        return LoginUseCase(request = requestService, authenRepository)
    }

    @Provides
    fun provideHomeUseCase(requestService: RequestService): HomeUseCase {
        return HomeUseCase(getBanner = GetBanner(requestService))
    }
}