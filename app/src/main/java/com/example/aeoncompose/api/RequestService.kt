package com.example.aeoncompose.api

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.aeoncompose.base.BaseResponse
import com.example.aeoncompose.utils.LogCat
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.utils.*
import io.ktor.http.*
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class RequestService @Inject constructor(private val client: HttpClient, val context: Context) {

    var work: Work? = null

    suspend fun request(repo: Repo) {
        check(isOnline()) {
            EventBus.getDefault().postSticky(TypeError.NO_INTERNET)
        }
        try {
            when (repo.typeRepo) {
                TypeRepo.GET -> get(repo)
                TypeRepo.POST -> post(repo)
                TypeRepo.PUT -> put(repo)
                TypeRepo.DELETE -> delete(repo)
            }
        } catch (e: RedirectResponseException) {
            // 3xx - responses
            LogCat.e(e.message)
            work?.onError(ResultWrapper.Error(error = e.response.status.value.toString(), e.response.status.description))
            EventBus.getDefault().postSticky(TypeError.REDIRECT_RESPONSE_ERROR)
        } catch (e: ClientRequestException) {
            // 4xx - responses
            LogCat.e(e.message)
            work?.onError(ResultWrapper.Error(error = e.response.status.value.toString(), e.response.status.description))
            EventBus.getDefault().postSticky(TypeError.CLIENT_REQUEST_ERROR)
        } catch (e: ServerResponseException) {
            // 5xx - responses
            LogCat.e(e.message)
            work?.onError(ResultWrapper.Error(error = e.response.status.value.toString(), e.response.status.description))
            EventBus.getDefault().postSticky(TypeError.SERVER_RESPONSE_ERROR)
        } catch (e: Exception) {
            LogCat.e(e.message)
            work?.onError(ResultWrapper.Error(error = e.message.toString()))
            EventBus.getDefault().postSticky(TypeError.NO_INTERNET)
        }
    }

    private suspend fun get(repo: Repo) {
        val response: HttpResponse = client.get {
            url(path = repo.url)
            headers {
                repo.headers.forEach { (k, v) ->
                    append(k, v)
                }
            }
        }
        if (response.status.isSuccess()) work?.onSuccess(ResultWrapper.Success(BaseResponse(data = response.receive())))
    }

    private suspend fun post(repo: Repo) {
        val response: HttpResponse = client.post {
            url(path = repo.url)
            headers {
                repo.headers.forEach { (k, v) ->
                    append(k, v)
                }
            }
            contentType(ContentType.Application.Json)
            body = repo.message ?: EmptyContent
        }
        if (response.status.isSuccess()) work?.onSuccess(ResultWrapper.Success(BaseResponse(data = response.receive())))
    }

    private suspend fun put(repo: Repo) {
        val response: HttpResponse = client.put {
            headers {
                repo.headers.forEach { (k, v) ->
                    append(k, v)
                }
            }
        }
        if (response.status.isSuccess()) work?.onSuccess(ResultWrapper.Success(BaseResponse(data = response.receive())))
    }

    private suspend fun delete(repo: Repo) {
        val response: HttpResponse = client.delete {
            headers {
                repo.headers.forEach { (k, v) ->
                    append(k, v)
                }
            }
        }
        if (response.status.isSuccess()) work?.onSuccess(ResultWrapper.Success(BaseResponse(data = response.receive())))
    }

    private fun isOnline(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        val result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
        return result
    }

    fun work(work: Work) = apply {
        this.work = work
    }

    inline fun work(
        crossinline onSuccess: suspend (success: ResultWrapper.Success<BaseResponse>) -> Unit = {},
        crossinline onError: suspend (error: ResultWrapper.Error) -> Unit = {}
    ) = work(object : Work {
        override suspend fun onSuccess(result: ResultWrapper.Success<BaseResponse>): ResultWrapper.Success<BaseResponse> {
            onSuccess.invoke(result)
            return result
        }

        override suspend fun onError(error: ResultWrapper.Error): ResultWrapper.Error {
            onError.invoke(error)
            return error
        }
    })

    interface Work {
        suspend fun onSuccess(result: ResultWrapper.Success<BaseResponse>): ResultWrapper.Success<BaseResponse>

        suspend fun onError(error: ResultWrapper.Error): ResultWrapper.Error
    }
}