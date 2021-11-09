package com.example.aeoncompose

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.aeoncompose.ui.AeonDialog
import com.example.aeoncompose.ui.HomeScreen
import com.example.aeoncompose.ui.KYCScreen
import com.example.aeoncompose.ui.base_view.DialogSingle
import com.example.aeoncompose.ui.theme.AeonComposeTheme
import com.example.aeoncompose.ui.view.home.HomeScreen
import com.example.aeoncompose.ui.view.login.LoginView
import com.example.aeoncompose.ui.view.preload.PreloadView
import com.example.aeoncompose.ui.view.start_kyc.StartKYC
import com.example.aeoncompose.utils.ScreenUtils
import com.example.aeoncompose.utils.ScreenUtils.screenHeight
import com.example.aeoncompose.utils.ScreenUtils.screenWidth
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            screenWidth = LocalContext.current.resources.displayMetrics.widthPixels / LocalDensity.current.density
            screenHeight = LocalContext.current.resources.displayMetrics.heightPixels / LocalDensity.current.density
            ScreenUtils.calculatePercentScreen(LocalContext.current.resources.displayMetrics)
            MainView()
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
}

@Composable
fun MainView() {
    AeonComposeTheme {
        val navController = rememberNavController()
        Surface(color = MaterialTheme.colors.background) {
            NavHost(
                navController = navController,
                startDestination = KYCScreen.getName(),
            ) {
                navigation(startDestination = KYCScreen.Preload.name, route = KYCScreen.getName()) {
                    composable(KYCScreen.Preload.name) { PreloadView(navHostController = navController) }
                    composable(KYCScreen.StartKYC.name) { StartKYC(navHostController = navController) }
                    composable(KYCScreen.Login.name) { LoginView(navHostController = navController) }
                }
                navigation(startDestination = HomeScreen.Home.name, route = HomeScreen.getName()) {
                    composable(HomeScreen.Home.name) {
                        HomeScreen(navHostController = navController)
                    }
                }

                dialog(route = AeonDialog.DialogSingle.name + "/{message}", arguments = listOf(navArgument("message") {
                    type = NavType.StringType
                })) {
                    DialogSingle(message = it.arguments?.getString("message") ?: "") {
                        navController.popBackStack()
                    }
                }
            }
        }
    }
}