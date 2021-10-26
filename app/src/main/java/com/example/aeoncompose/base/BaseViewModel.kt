package com.example.aeoncompose.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aeoncompose.R
import com.example.aeoncompose.api.RepoManager
import com.example.aeoncompose.api.ResultWrapper
import com.example.aeoncompose.api.process_api.RetrofitService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

open class BaseViewModel<R>() : ViewModel(), LifecycleObserver, CoroutineScope {

    var _isLoading = MutableStateFlow(false)
    var isLoading: StateFlow<Boolean> = _isLoading

    var _error = MutableStateFlow(ResultWrapper.ErrorThrowable("-1"))
    var error: StateFlow<ResultWrapper.ErrorThrowable> = _error
    var baseError: StateFlow<ResultWrapper.ErrorThrowable> = _error

    var netWorkError = MutableLiveData<Boolean>()
    val viewModelJob = SupervisorJob()
    val repo = RepoManager() as R
    val coroutineExceptionHandler = CoroutineExceptionHandler { context, exception ->
        _error.value =
            ResultWrapper.ErrorThrowable("Error by ${exception.message.toString()} on ${Thread.currentThread().name}")
    }

    fun CoroutineScope.viewModelScope(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(
            coroutineContext + coroutineExceptionHandler + CoroutineName(
                getMethodName()
            )
        ) { block(this) }
    }

    fun viewModelScope(
        coroutineContext: CoroutineContext,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return CoroutineScope(
            coroutineContext + coroutineExceptionHandler + viewModelJob + CoroutineName(
                getMethodName()
            )
        )
            .launch { block(this) }
    }

    fun getMethodName() = Thread.currentThread().stackTrace[5].methodName

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + viewModelJob

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
