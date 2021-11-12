package com.example.aeoncompose.base

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.aeoncompose.utils.LogCat
import org.greenrobot.eventbus.EventBus

abstract class BaseActivity : AppCompatActivity() {

//    private val dialogLoading by lazy {
//        DialogLoading(this)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onUserInteraction() {
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun progressBarView() {
//        if (dialogLoading.isShowing) return
//        dialogLoading.showDialog()
    }

    fun dismissProgressBar() {
//        if (dialogLoading.isShowing) dialogLoading.closeDialog()
    }

    fun hideKeyBoard() {
        val view = this.currentFocus
        if (view is EditText) {
            view.clearFocus()
            val imm =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
        }
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        LogCat.d("onTrimMemory:$level")
        when (level) {
            TRIM_MEMORY_COMPLETE -> {
                LogCat.d("TRIM_MEMORY_COMPLETE=$level")
            }
            TRIM_MEMORY_UI_HIDDEN -> {
                LogCat.d("TRIM_MEMORY_UI_HIDDEN=$level")
            }
            TRIM_MEMORY_BACKGROUND -> {
                LogCat.d("TRIM_MEMORY_BACKGROUND=$level")
            }
            TRIM_MEMORY_MODERATE -> {
                LogCat.d("TRIM_MEMORY_MODERATE=$level")
            }
            TRIM_MEMORY_RUNNING_CRITICAL -> {
                LogCat.d("TRIM_MEMORY_RUNNING_CRITICAL=$level")
            }
            TRIM_MEMORY_RUNNING_LOW -> {
                LogCat.d("TRIM_MEMORY_RUNNING_LOW=$level")
            }
            TRIM_MEMORY_RUNNING_MODERATE -> {
                LogCat.d("TRIM_MEMORY_RUNNING_MODERATE=$level")
            }
            else -> {
                LogCat.d("TRIM_MEMORY_UNKNOWN=$level")
            }
        }
    }

    protected open fun registerEventBus() {
        EventBus.getDefault().register(this)
    }

    protected open fun unregisterEventBus() {
        EventBus.getDefault().unregister(this)
    }
}