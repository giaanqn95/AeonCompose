package com.example.aeoncompose.api

import com.example.aeoncompose.api.process_api.Repo
import com.example.aeoncompose.base.BaseResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.JsonElement
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

    suspend fun post(repo: Repo): JsonElement? {
        val response: HttpResponse = client.post {
            url(path = repo.url)
            headers {
                repo.headers.forEach { (k, v) ->
                    append(k, v)
                }
            }
        }
        return response.receive()
    }

    suspend fun put(repo: Repo): JsonElement? {
        val response: HttpResponse = client.put {
            headers {
                repo.headers.forEach { (k, v) ->
                    append(k, v)
                }
            }
        }
        return response.receive()
    }

    suspend fun delete(repo: Repo): JsonElement? {
        val response: HttpResponse = client.delete {
            headers {
                repo.headers.forEach { (k, v) ->
                    append(k, v)
                }
            }
        }
        return response.receive()
    }

    private suspend fun handleResponse(response: HttpResponse) {
        try {
            if (response.status.value in 200..299) work?.onSuccess(ResultWrapper.Success(BaseResponse(data = response.receive())))
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

//    suspend fun getPosts(): List<PostResponse> {
//        return try {
//            client.get { url(HttpRoutes.POSTS) }
//        } catch(e: RedirectResponseException) {
//            // 3xx - responses
//            println("Error: ${e.response.status.description}")
//            emptyList()
//        } catch(e: ClientRequestException) {
//            // 4xx - responses
//            println("Error: ${e.response.status.description}")
//            emptyList()
//        } catch(e: ServerResponseException) {
//            // 5xx - responses
//            println("Error: ${e.response.status.description}")
//            emptyList()
//        } catch(e: Exception) {
//            println("Error: ${e.message}")
//            emptyList()
//        }
//    }

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