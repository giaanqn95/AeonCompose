package com.example.aeoncompose.di.module

import android.app.Application
import com.example.aeoncompose.BuildConfig
import com.example.aeoncompose.api.ApiInterface
import com.example.aeoncompose.api.RequestService
import com.example.aeoncompose.api.process_api.RetrofitService
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiClientModule {

    @Provides
    @Singleton
    fun okHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()
        logging.setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
        builder.protocols(Collections.singletonList(Protocol.HTTP_1_1))
        builder.addInterceptor(logging)
            .callTimeout(15000, TimeUnit.MILLISECONDS)
            .readTimeout(15000, TimeUnit.MILLISECONDS)
            .writeTimeout(15000, TimeUnit.MILLISECONDS)
            .connectTimeout(15000, TimeUnit.MILLISECONDS)
        return builder.build()
    }

    @Provides
    @Singleton
    fun client(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://113.161.84.22:8063/api/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun providePostApi(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun getRetroService(application: Application, apiInterface: ApiInterface): RetrofitService {
        return RetrofitService(application, apiInterface)
    }

    @Provides
    @Singleton
    fun provideHttpKtorClient(): HttpClient = HttpClient(Android) {
        install(Logging) {
            level = if (BuildConfig.DEBUG) LogLevel.ALL else LogLevel.NONE
        }
        install(JsonFeature) {
            serializer = KotlinxSerializer()
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