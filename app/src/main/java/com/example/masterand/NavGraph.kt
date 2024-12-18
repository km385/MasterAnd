package com.example.masterand

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument


@Composable
fun SetupNavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(
            route = Screen.Login.route/*,
            arguments = listOf(navArgument("login") {
                type = NavType.StringType
            })*/
        ) {
            ProfileScreen(navController = navController)
        }

        composable(
            route = Screen.Profile.route,
            arguments = listOf(
                navArgument("login") {type = NavType.StringType},
                navArgument("description") {type = NavType.StringType},
                navArgument("profileUri") {
                    type = NavType.StringType
                    nullable = true
                }
            )
            ){ backStackEntry ->
            val login = backStackEntry.arguments?.getString("login") ?: ""
            val description = backStackEntry.arguments?.getString("description") ?: ""
            val profileUri = backStackEntry.arguments?.getString("profileUri") ?.let {
                Uri.parse(it)
            }
            ProfileCard(
                navController = navController,
                login = login,
                description = description,
                profileUri = profileUri ?: Uri.EMPTY
            )

        }

        composable(route = Screen.Game.route) {
            GameScreen(navController = navController)
        }

        composable(route = Screen.HighScores.route) {
            ResultsScreen(navController = navController)
        }
    }
}