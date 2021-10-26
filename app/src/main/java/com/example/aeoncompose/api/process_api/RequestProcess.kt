package com.example.aeoncompose.api.process_api

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.aeoncompose.api.ResultWrapper
import com.example.aeoncompose.base.BaseResponse
import com.example.aeoncompose.api.ApiInterface
import okhttp3.MultipartBody
import retrofit2.HttpException
import com.example.aeoncompose.extensions.timeOutSuspend
import com.example.aeoncompose.utils.JSON

/**
 * Created by giaan on 12/16/20.
 * Company: Intelin
 * Email: antranit95@gmail.com
 */
class RequestProcess(
    private val repo: Repo,
    val context: Context,
    private val apiInterface: ApiInterface
) {

    private lateinit var apiCall: (suspend () -> BaseResponse)
    private var codeRequired: Any? = repo.codeRequired
    var loading: (isLoading: Boolean) -> Unit = {}
    var work: RetrofitService.Work? = null
    var error: (error: ResultWrapper.ErrorThrowable) -> Unit = {}

    fun loading(loading: (isLoading: Boolean) -> Unit) = apply {
        this.loading = loading
    }

    fun work(work: RetrofitService.Work) = apply {
        this.work = work
    }

    inline fun work(
        crossinline onSuccess: (success: ResultWrapper.Success<BaseResponse>) -> Unit = {},
        crossinline onError: (error: ResultWrapper.Error) -> Unit = {}
    ) = work(object : RetrofitService.Work {
        override fun onSuccess(result: ResultWrapper.Success<BaseResponse>): ResultWrapper.Success<BaseResponse> {
            onSuccess.invoke(result)
            return result
        }

        override fun onError(error: ResultWrapper.Error): ResultWrapper.Error {
            onError.invoke(error)
            return error
        }
    })

    fun registerListenerError(onError: (error: ResultWrapper.ErrorThrowable) -> Unit) {
        error = onError
    }

    private suspend fun getMethod(
        headers: Map<String, String>,
        url: String,
        message: Any? = null
    ) = BaseResponse(data = apiInterface.get(headers, url + message))


    private suspend fun postMethod(
        headers: Map<String, String>,
        url: String,
        message: Any? = null
    ) = BaseResponse(data = apiInterface.post(headers, url, message))


    private suspend fun putMethod(
        headers: Map<String, String>,
        url: String,
        message: Any? = null
    ) = BaseResponse(data = apiInterface.put(headers, url, message))


    private suspend fun deleteMethod(
        headers: Map<String, String>,
        url: String,
        message: Any?
    ) = BaseResponse(data = apiInterface.delete(headers, url, message))


//    private suspend fun uploadFile(
//        headers: Map<String, String>,
//        url: String,
//        message: MultipartBody.Part?
//    ) {
//        this.apiCall = {
//            JSON.decode(apiInterface.uploadFile(headers, url, message), BaseResponse::class.java)
//                ?: BaseResponse()
//        }
//    }

    private suspend fun getRequest() = when (repo.typeRepo) {
        TypeRepo.GET -> {
            getMethod(repo.headers, repo.url, repo.message)
        }
        TypeRepo.POST -> {
            postMethod(repo.headers, repo.url, repo.message)
        }
        TypeRepo.PUT -> {
            putMethod(repo.headers, repo.url, repo.message)
        }
        TypeRepo.DELETE -> {
            deleteMethod(repo.headers, repo.url, repo.message)
        }
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

    suspend fun build(): ResultWrapper<BaseResponse> {
        loading.invoke(true)
        val result = timeOutSuspend {
            if (!isOnline())
                return@timeOutSuspend ResultWrapper.Error("NoInternet")
            try {
                return@timeOutSuspend handleResponseApi(getRequest())
            } catch (throwable: Throwable) {
                return@timeOutSuspend handleThrowable(throwable)
            }
        }
        loading.invoke(false)
        if (result is ResultWrapper.ErrorThrowable)
            error.invoke(result)
        return result

    }

    private fun handleResponseApi(response: BaseResponse?): ResultWrapper<BaseResponse> {
        if (response == null) {
            work?.onError(ResultWrapper.Error("Error due to model mismatch or response BE is empty"))
            return ResultWrapper.Error("Error due to model mismatch or response BE is empty")
        }
        work?.onSuccess(ResultWrapper.Success(response))
        return ResultWrapper.Success(response)
    }

    private fun handleThrowable(throwable: Throwable): ResultWrapper<BaseResponse> {
        when (throwable) {
            is HttpException -> {
                val code = throwable.code()
                if (code in 500..599) {
                    work?.onError(ResultWrapper.Error("$code", throwable.message))
                    return ResultWrapper.ErrorThrowable("$code", throwable.message)
                } else if (code in 400..499) {
                    work?.onError(ResultWrapper.Error("$code", throwable.message))
                    return ResultWrapper.ErrorThrowable("$code", throwable.message)
                }
                work?.onError(ResultWrapper.Error("$code", throwable.message))
                return ResultWrapper.ErrorThrowable("$code", throwable.message)
            }
            else -> {
                work?.onError(ResultWrapper.Error("", throwable.message))
                return ResultWrapper.ErrorThrowable("", throwable.message)
            }
        }
    }

    interface Work {
        fun onSuccess(result: ResultWrapper.Success<BaseResponse>): ResultWrapper.Success<BaseResponse>

        fun onError(error: ResultWrapper.Error): ResultWrapper.Error
    }
}
