package com.example.aeoncompose.api

import com.example.aeoncompose.base.BaseResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.utils.*
import io.ktor.http.*
import javax.inject.Inject

class RequestService @Inject constructor(private val client: HttpClient) {

    var work: Work? = null

    suspend fun get(repo: Repo) {
        val response: HttpResponse = client.get {
            url(path = repo.url)
            headers {
                repo.headers.forEach { (k, v) ->
                    append(k, v)
                }
            }
        }
        handleResponse(response.receive())
    }

    private suspend fun handleResponse(response: HttpResponse) {
        try {
            if (response.status.isSuccess()) work?.onSuccess(ResultWrapper.Success(BaseResponse(data = response.receive())))
            else println("Error: ${response.status.description}")
        } catch (e: RedirectResponseException) {
            // 3xx - responses
            println("Error: ${e.response.status.description}")
        } catch (e: ClientRequestException) {
            // 4xx - responses
            println("Error: ${e.response.status.description}")
        } catch (e: ServerResponseException) {
            // 5xx - responses
            println("Error: ${e.response.status.description}")
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }

    suspend fun post(repo: Repo) {
        try {
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
            if (response.status.isSuccess()) {
                work?.onSuccess(ResultWrapper.Success(BaseResponse(data = response.receive())))
            }
        } catch (e: RedirectResponseException) {
            // 3xx - responses
            work?.onError(ResultWrapper.Error(error = e.response.status.value.toString(), e.response.status.description))
        } catch (e: ClientRequestException) {
            // 4xx - responses
            work?.onError(ResultWrapper.Error(error = e.response.status.value.toString(), e.response.status.description))
        } catch (e: ServerResponseException) {
            // 5xx - responses
            work?.onError(ResultWrapper.Error(error = e.response.status.value.toString(), e.response.status.description))
        } catch (e: Exception) {
            work?.onError(ResultWrapper.Error(error = e.message.toString()))
        }
    }

    suspend fun put(repo: Repo) {
        try {
            val response: HttpResponse = client.put {
                headers {
                    repo.headers.forEach { (k, v) ->
                        append(k, v)
                    }
                }
            }
            if (response.status.isSuccess()) {
                work?.onSuccess(ResultWrapper.Success(BaseResponse(data = response.receive())))
            }
        } catch (e: RedirectResponseException) {
            // 3xx - responses
            work?.onError(ResultWrapper.Error(error = e.response.status.value.toString(), e.response.status.description))
        } catch (e: ClientRequestException) {
            // 4xx - responses
            work?.onError(ResultWrapper.Error(error = e.response.status.value.toString(), e.response.status.description))
        } catch (e: ServerResponseException) {
            // 5xx - responses
            work?.onError(ResultWrapper.Error(error = e.response.status.value.toString(), e.response.status.description))
        } catch (e: Exception) {
            work?.onError(ResultWrapper.Error(error = e.message.toString()))
        }
    }

    suspend fun delete(repo: Repo) {
        try {
            val response: HttpResponse = client.delete {
                headers {
                    repo.headers.forEach { (k, v) ->
                        append(k, v)
                    }
                }
            }
            if (response.status.isSuccess()) {
                work?.onSuccess(ResultWrapper.Success(BaseResponse(data = response.receive())))
            }
        } catch (e: RedirectResponseException) {
            // 3xx - responses
            work?.onError(ResultWrapper.Error(error = e.response.status.value.toString(), e.response.status.description))
        } catch (e: ClientRequestException) {
            // 4xx - responses
            work?.onError(ResultWrapper.Error(error = e.response.status.value.toString(), e.response.status.description))
        } catch (e: ServerResponseException) {
            // 5xx - responses
            work?.onError(ResultWrapper.Error(error = e.response.status.value.toString(), e.response.status.description))
        } catch (e: Exception) {
            work?.onError(ResultWrapper.Error(error = e.message.toString()))
        }
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