package com.example.masterand

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.firstapp.ProfileCard

@Composable
fun SetupNavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
                startDestination = Screen.First.route ){
        composable(route = Screen.First.route, arguments = listOf(navArgument("login") {
            type = NavType.StringType
        })) {
            ProfileScreen(navController = navController, )
        }
        composable(route = Screen.Second.route){
            ProfileCard(navController = navController, login = "jakub", description = "opis")
        }
    }
}