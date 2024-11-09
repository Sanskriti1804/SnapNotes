package com.example.wishlistapp

//restricted class heirarchy - defining subclasses
//Screen(route) - primary constructor of sealed class screen
// - route represents unique identifier or route for each screen
sealed class Screen(val route : String){
    object UserScreen : Screen("user_screen")
    object SignupScreen : Screen("signup_screen")
    object SigninScreen : Screen("signin_screen")
    object HomeScreen : Screen("home_screen")
    object AddScreen : Screen("add_screen")
    object ProfileScreen : Screen("profile_screen")
    object SettingsScreen : Screen("settings_screen")
    object InfoPrevScreen : Screen("aboutinfo_screen")
    object InfoScreen : Screen("info_screen")
//    object ScreenFav : Screen("favorite_screen")
}