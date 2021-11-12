package com.example.aeoncompose.ui.view.main_home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.aeoncompose.ui.navigation.EnumNavigationScreen
import com.example.aeoncompose.ui.navigation.NavigationItem
import com.example.aeoncompose.ui.view.main_home.home.HomeScreen
import com.example.aeoncompose.ui.view.main_home.other.OtherScreen
import com.example.aeoncompose.ui.view.main_home.shopping.ShoppingScreen

@Composable
fun MainScreen(navHostController: NavHostController) {
    val navControllerMainHome = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navControllerMainHome)
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            MainScreenNavigationConfigurations(navHostController, navControllerMainHome)
        }
    }
}

@Composable
fun BottomNavigationBar(navControllerMainHome: NavHostController) {
    BottomNavigation(Modifier.background(Color.White)) {
        val navBackStackEntry by navControllerMainHome.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        listOf(
            NavigationItem.Home,
            NavigationItem.Shopping,
            NavigationItem.Other
        ).forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = screen.icon), contentDescription = screen.title) },
                label = { Text(text = screen.title) },
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = Color.White.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navControllerMainHome.navigate(screen.route) {
                        popUpTo(navControllerMainHome.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
private fun MainScreenNavigationConfigurations(navController: NavHostController, navControllerMainHome: NavHostController) {
    NavHost(navControllerMainHome, startDestination = EnumNavigationScreen.Home.name) {
        composable(EnumNavigationScreen.Home.name) {
            HomeScreen(navHostController = navController)
        }
        composable(EnumNavigationScreen.Shopping.name) {
            ShoppingScreen(navHostController = navController)
        }
        composable(EnumNavigationScreen.Other.name) {
            OtherScreen(navHostController = navController)
        }
    }
}