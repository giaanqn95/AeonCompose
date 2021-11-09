package com.example.aeoncompose.di.module

import com.example.aeoncompose.BuildConfig
import com.example.aeoncompose.api.RequestService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiClientModule {

    val HEADER_AUTHORIZATION = "Authorization"
    val HEADER_LANG = "Accept-Language"
    val HEADER_API_KEY = "x-api-key"
    val HEADER_UUID = "x-request-id"
    val HEADER_HEAD_TOKEN = "BEARER "
    val HEADER_CONTENT_TYPE = "Content-Type"
    val HEADER_CONTENT_TYPE_VALUE_JSON = "application/json"
    val AEON_API_KEY = "3EB76D87D97C427943957C555AB0B60847582D38CB1688ED86C59251206305E3"

    @Provides
    @Singleton
    fun provideHttpKtorClient(): HttpClient = HttpClient(Android) {
        install(Logging) {
            level = if (BuildConfig.DEBUG) LogLevel.BODY else LogLevel.NONE
        }
        install(JsonFeature) {
            serializer = GsonSerializer {
                setPrettyPrinting()
                disableHtmlEscaping()
            }
        }
        defaultRequest {
            host = "113.161.84.22:8063/api"
            url { protocol = URLProtocol.HTTP }
        }
        engine {
            connectTimeout = 30_000
            threadsCount = 4
            pipelining = true
        }
    }

    @Provides
    @Singleton
    fun provideRequestService(client: HttpClient): RequestService {
        return RequestService(client)
    }
}