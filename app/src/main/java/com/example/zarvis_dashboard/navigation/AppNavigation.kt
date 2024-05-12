package com.example.zarvis_dashboard.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.zarvis_dashboard.screens.HomeScreen.HomeScreen
import com.example.zarvis_dashboard.screens.login.LoginScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.protobuf.ExperimentalApi

@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalApi
@Composable
fun AppNavigation(){
    val navController= rememberNavController()

    NavHost(navController =navController , startDestination=if(FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()) {
        AppScreens.LoginScreen.name
    }else{
        AppScreens.HomeScreen.name
    }) {
        composable(AppScreens.HomeScreen.name) {
            HomeScreen(navController = navController)
        }
        composable(AppScreens.LoginScreen.name) {
            LoginScreen(navController = navController)
        }
    }
}