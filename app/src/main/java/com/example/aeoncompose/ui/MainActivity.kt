package com.example.aeoncompose.ui

import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.aeoncompose.api.RequestState
import com.example.aeoncompose.api.TypeError
import com.example.aeoncompose.api.UiState
import com.example.aeoncompose.base.BaseActivity
import com.example.aeoncompose.ui.base_view.DialogSingle
import com.example.aeoncompose.ui.navigation.*
import com.example.aeoncompose.ui.theme.AeonComposeTheme
import com.example.aeoncompose.utils.LogCat
import com.example.aeoncompose.utils.ScreenUtils
import com.example.aeoncompose.utils.ScreenUtils.screenHeight
import com.example.aeoncompose.utils.ScreenUtils.screenWidth
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerEventBus()
        setContent {
            screenWidth = LocalContext.current.resources.displayMetrics.widthPixels / LocalDensity.current.density
            screenHeight = LocalContext.current.resources.displayMetrics.heightPixels / LocalDensity.current.density
            ScreenUtils.calculatePercentScreen(LocalContext.current.resources.displayMetrics)
            MainView(mainViewModel)
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        hideSystemUI()
    }

    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        val decorView: View = window.decorView
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

    // Shows the system bars by removing all the flags
    // except for the ones that make the content appear under the system bars.
    private fun showSystemUI() {
        val decorView: View = window.decorView
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

    override fun onStop() {
        super.onStop()
        unregisterEventBus()
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onEvent(event: Any?) {
        if (event == null) return
        LogCat.d("Receive event $event")
        when (event) {
            TypeError.NO_INTERNET -> errorNoInternet(event as TypeError)
            TypeError.REDIRECT_RESPONSE_ERROR -> redirectError(event as TypeError)
            TypeError.CLIENT_REQUEST_ERROR -> clientRequestError(event as TypeError)
            TypeError.SERVER_RESPONSE_ERROR -> serverResponseError(event as TypeError)
        }
        EventBus.getDefault().removeStickyEvent(event)
    }

    fun errorNoInternet(typeTypeError: TypeError) {
        mainViewModel.handleTypeError(typeTypeError)
    }

    fun redirectError(typeTypeError: TypeError) {
        mainViewModel.handleTypeError(typeTypeError)
    }

    fun clientRequestError(typeTypeError: TypeError) {
        mainViewModel.handleExpiredToken()
    }

    fun serverResponseError(typeTypeError: TypeError) {
        mainViewModel.handleTypeError(typeTypeError)
    }

    fun anotherError(typeTypeError: TypeError) {
        mainViewModel.handleTypeError(typeTypeError)
    }
}

@Composable
fun MainView(mainViewModel: MainViewModel) {
    AeonComposeTheme {
        val navController = rememberNavController()
        Surface(color = MaterialTheme.colors.background) {
            NavHost(
                navController = navController,
                startDestination = EnumKYCScreen.getName(),
            ) {
                navigationKYC(navController)
                navigationMain(navController)
                navigationProfile(navController)
                dialog(route = AeonDialog.DialogSingle.name + "/{message}", arguments = listOf(navArgument("message") {
                    type = NavType.StringType
                })) {
                    DialogSingle(message = it.arguments?.getString("message") ?: "") {
                        navController.popBackStack()
                    }
                }
            }
        }

        HandleErrorState(mainViewModel = mainViewModel, navController = navController)
    }
}

@Composable
fun HandleErrorState(mainViewModel: MainViewModel, navController: NavController) {
    val errorState = mainViewModel.uiStateError.value
    val errorExpiredToken = mainViewModel.uiStateExpiredToken.value
    if (errorState.state == RequestState.ERROR) {
        LaunchedEffect(key1 = errorState.state, block = {
            navController.navigate(AeonDialog.DialogSingle.name + "/${errorState.message}")
            mainViewModel._uiStateError.value = UiState(RequestState.NON)
        })
    }
    if (errorExpiredToken.state == RequestState.ERROR) {
        DialogSingle("Token hết hạn") {
            navController.navigate(EnumKYCScreen.StartKYC.name) {
                navController.currentDestination?.id?.let {
                    popUpTo(it) {
                        inclusive = true
                    }
                }
            }
            mainViewModel._uiStateExpiredToken.value = UiState(RequestState.NON)
        }
    }
}
