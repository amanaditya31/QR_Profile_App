package com.example.zarvis_dashboard.navigation

enum class AppScreens {
    HomeScreen,
    LoginScreen,
    ProfileScreen,
    CreateAccountScreen;

    companion object{
        fun fromRoute(route: String?): AppScreens
        = when(route?.substringBefore("/")){
            HomeScreen.name -> HomeScreen
            LoginScreen.name -> LoginScreen
            ProfileScreen.name-> ProfileScreen
            CreateAccountScreen.name -> CreateAccountScreen

            null-> HomeScreen
            else-> throw IllegalArgumentException("Route $route is not recognized")
        }
    }

}