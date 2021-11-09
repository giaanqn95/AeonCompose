package com.example.aeoncompose.base

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aeoncompose.extensions.launchWhenCreated
import com.example.aeoncompose.utils.LogCat

abstract class BaseFragment<VM : BaseViewModel<*>> : Fragment() {
    private val DELAYED_TIME_FOR_INIT_SCREEN = 160L

    val viewModel: ViewModel by lazy { ViewModelProvider(this).get(getViewModelClass()) }
    private val handler = Handler(Looper.getMainLooper())
    private val runnable = { flowOnce() }

    open var useSharedViewModel: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogCat.d("Lifecycle in Fragment onCreate " + this.javaClass.name)
        init()
        launchWhenCreated { initViewModel() }
        handleError()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        LogCat.d("Lifecycle in Fragment onCreateView " + this.javaClass.name)
        return ComposeView(requireContext()).apply {
            setContent {
                GetViewBinding().apply {
                    try {
                        setOnTouchListener { _, _ ->
                            hideKeyBoard()
                            return@setOnTouchListener false
                        }
                    } catch (e: Exception) {
                        onShowError(e.message.toString())
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        LogCat.d("Lifecycle in Fragment onViewCreated " + this.javaClass.name)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun init() {
    }

    private fun handleError() {
        launchWhenCreated {
        }
    }

    @Composable
    protected abstract fun GetViewBinding()
    protected abstract fun getViewModelClass(): Class<VM>

    /*Handle viewModel*/
    abstract suspend fun initViewModel()
    abstract fun flowOnce()
    abstract fun onComeBack()

    fun progressBar(boolean: Boolean) {
        LogCat.i("progressBar $boolean")
        if (boolean) showProgressBar()
        else hideProgressBar()
    }

    fun showProgressBar() {
        (requireActivity() as BaseActivity).progressBarView()
    }

    fun hideProgressBar() {
        (requireActivity() as BaseActivity).dismissProgressBar()
    }

    fun hideKeyBoard() {
        (requireActivity() as BaseActivity).hideKeyBoard()
    }

    fun onShowError(mess: String) {

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        LogCat.d("Lifecycle in Fragment onAttach " + this.javaClass.name)
    }

    override fun onPause() {
        super.onPause()
        LogCat.d("Lifecycle in Fragment onPause " + this.javaClass.name)
    }

    override fun onStop() {
        super.onStop()
        LogCat.d("Lifecycle in Fragment onStop " + this.javaClass.name)
    }

    override fun onDestroy() {
        super.onDestroy()
        LogCat.d("Lifecycle in Fragment onDestroy " + this.javaClass.name)
    }

    override fun onStart() {
        super.onStart()
        LogCat.d("Lifecycle in Fragment onStart " + this.javaClass.name)
    }

    override fun onResume() {
        super.onResume()
        LogCat.d("Lifecycle in Fragment onResume " + this.javaClass.name)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks { runnable }
        LogCat.d("Lifecycle in Fragment onDestroyView " + this.javaClass.name)
    }

    override fun onDetach() {
        super.onDetach()
        LogCat.d("Lifecycle in Fragment onDetach " + this.javaClass.name)
    }
}