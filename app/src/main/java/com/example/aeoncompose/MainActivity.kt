package com.example.aeoncompose

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.aeoncompose.ui.HomeScreen
import com.example.aeoncompose.ui.KYCScreen
import com.example.aeoncompose.ui.theme.AeonComposeTheme
import com.example.aeoncompose.ui.view.home.HomeScreen
import com.example.aeoncompose.ui.view.login.LoginView
import com.example.aeoncompose.ui.view.preload.PreloadView
import com.example.aeoncompose.ui.view.preload.PreloadViewModel
import com.example.aeoncompose.ui.view.start_kyc.StartKYC
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainView()
        }
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
                    composable(
                        KYCScreen.StartKYC.name + "/{name}/{old}",
                        arguments = listOf(
                            navArgument("name") {
                                type = NavType.StringType
                            },
                            navArgument("old") {
                                type = NavType.IntType
                                defaultValue = 0
                            },
                        )
                    ) {
                        StartKYC(navHostController = navController)
                    }
                    composable(KYCScreen.Login.name) { LoginView(navHostController = navController) }
                }
                navigation(startDestination = HomeScreen.Home.name, route = HomeScreen.getName()) {
                    composable(HomeScreen.Home.name) {
                        HomeScreen(navHostController = navController)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AeonComposeTheme {
//        Greeting()
    }
}