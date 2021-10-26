package com.example.aeoncompose.api.process_api

import android.app.Application
import android.content.Context
import com.example.aeoncompose.api.ResultWrapper
import com.example.aeoncompose.base.BaseResponse
import com.example.aeoncompose.api.ApiInterface
import com.example.aeoncompose.utils.LogCat
import kotlinx.coroutines.*
import java.lang.ref.WeakReference


/**
 * Copyright by Intelin.
 * Creator: Tran Do Gia An
 * Date: 22/03/2019
 * Time: 10:36 AM
 */

class RetrofitService(val context: Context, val apiInterface: ApiInterface) {

    var end: () -> Unit = {}
    var loading: (isLoading: Boolean) -> Unit = {}
    var work = object : Work {
        override fun onSuccess(result: ResultWrapper.Success<BaseResponse>): ResultWrapper.Success<BaseResponse> =
            result

        override fun onError(error: ResultWrapper.Error): ResultWrapper.Error = error
    }

    var errorRegister: (error: ResultWrapper.ErrorThrowable) -> Unit = {}

    inline fun work(
        crossinline onSuccess: (success: ResultWrapper.Success<BaseResponse>) -> Unit = {},
        crossinline onError: (error: ResultWrapper.Error) -> Unit = {}
    ) = work(object : Work {
        override fun onSuccess(result: ResultWrapper.Success<BaseResponse>): ResultWrapper.Success<BaseResponse> {
            onSuccess.invoke(result)
            return result
        }

        override fun onError(error: ResultWrapper.Error): ResultWrapper.Error {
            onError.invoke(error)
            return error
        }
    })

    fun work(work: Work) = apply {
        this.work = work
    }

    fun end(end: () -> Unit) = apply {
        this.end = end
    }

    fun loading(loading: (isLoading: Boolean) -> Unit) = apply {
        this.loading = loading
    }

    fun registerError(onError: (error: ResultWrapper.ErrorThrowable) -> Unit) = apply {
        errorRegister = onError
    }

    fun setup(repo: Repo) = RequestProcess(repo, context, apiInterface).apply {
        registerListenerError(errorRegister)
    }

    fun successTogether(result: List<Any>) = apply {
        loading.invoke(true)
        result.forEach {
            if (it is ResultWrapper.Error) {
                work.onError(ResultWrapper.Error("Failed"))
                return@apply
            }
        }
        LogCat.d("List job done ${System.currentTimeMillis()} - ${Thread.currentThread().name}")
        work.onSuccess(ResultWrapper.Success(BaseResponse()))
        loading.invoke(false)
    }

    fun merge(vararg jobs: Job) {
        jobs.forEach {
            it.start()
        }
        LogCat.d("List job done${System.currentTimeMillis()} - ${Thread.currentThread().name}")
    }

    interface Work {
        fun onSuccess(result: ResultWrapper.Success<BaseResponse>): ResultWrapper.Success<BaseResponse>

        fun onError(error: ResultWrapper.Error): ResultWrapper.Error
    }
}
