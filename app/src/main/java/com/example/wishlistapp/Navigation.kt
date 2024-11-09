package com.example.wishlistapp

import androidx.navigation.*
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

//"NavHostController" - is a contrller that manages navigation (to manage navigation bw diff dest as defined in nav graph) within "NavHost"
//NavHost - container for destinations(screen) in navigation component
//rememberNavController() - NavHostController instance and it remembers its recompositions
@Composable
fun Navigation(viewModel: WishViewModel = viewModel(),
               navController : NavHostController = rememberNavController()){
    NavHost(
       navController = navController,
        startDestination = Screen.UserScreen.route
    ){
        composable(Screen.UserScreen.route){
            UserView(viewModel = viewModel,navController = navController)
        }
        composable(Screen.SignupScreen.route ){

            val authViewModel : AuthViewModel = viewModel()
            SignupView(viewModel = authViewModel, navController = navController)
        }
        composable(Screen.SigninScreen.route ){
            val loginAuthViewModel : LoginAuthViewModel = viewModel()
            SigninView(viewModel = loginAuthViewModel, navController = navController)
        }

        composable(Screen.ProfileScreen.route){
            ProfileView(viewModel = viewModel,navController = navController, onDismiss = {})
        }

        //composable(Screen.SettingsScreen.route + "/{emailState}"){backStackEntry ->
        composable(Screen.SettingsScreen.route
//                + "/{userNameState}" ,
//            arguments = listOf(
//                navArgument("userNameState"
//                ){
//                    type = NavType.StringType
//                }
//            )
        ){
//            backStackEntry ->
            //val imageUri = backStackEntry.arguments?.getString("imageUri")
            //val email = backStackEntry.arguments?.getString("emailState")
//            val username = backStackEntry.arguments?.getString("userNameState") ?: ""
//            //val decodedEmail = URLDecoder.decode(email, "UTF-8")
            //SettingsView(viewModel = viewModel,navController = navController, emailState = decodedEmail ?: "")
            val wishViewModel: WishViewModel = viewModel() // Obtain WishViewModel
            val authViewModel: AuthViewModel = viewModel()
            SettingsView( wishViewModel = wishViewModel,navController = navController)
//                username = username,
                //authViewModel = authViewModel,

            //SettingsView( wishViewModel = wishViewModel,authViewModel = authViewModel, navController = navController, username = username ?: "Default")
        }
        // initialiation in composable fnn(used in NavHost to define individual screen) - it takes a route and a lambda fnn
        composable(Screen.HomeScreen.route){
            HomeView(viewModel = viewModel,navController = navController)
        }

        composable(Screen.AddScreen.route + "/{id}",
            //arguements that composable expects
            arguments = listOf(
                navArgument("id"){
                    type = NavType.LongType
                    defaultValue = 0L
                    nullable = false
                }
            )
        ){
            //entry - current instance of NavBackStackEntry(an entry in the nav back stack managed by navigation)
            entry->
            //entry.args -> args associated with current naviagtion dest
            // retrives the long value associated with key id if entry.arg is not null
            //if entry.args is null or getlong returns null -> default its id to 0L
            val id = if(entry.arguments != null) entry.arguments!!.getLong("id") else 0L
            //passing that id to addeditdeatilView
            AddEditDetailView(id = id, viewModel = viewModel, navController = navController)
        }
        composable(Screen.InfoPrevScreen.route){
            AppInfoPrev(viewModel = viewModel,navController = navController)
        }
        composable(Screen.InfoScreen.route){
            AppInfo(viewModel = viewModel,navController = navController)
        }


    }

}
