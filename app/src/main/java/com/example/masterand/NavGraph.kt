package com.example.masterand

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument


@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(
            route = Screen.Login.route
        ) {
            ProfileScreen(navController = navController)
        }

        composable(
            route = Screen.Profile.route,
            arguments = listOf(
                navArgument("color_count") { type = NavType.IntType }
            )
        ) {backStackEntry ->
            val colorCount = backStackEntry.arguments?.getInt("color_count")
            ProfileCard(navController = navController, colorCount = colorCount!!)
        }

        composable(
            route = Screen.Game.route,
            arguments = listOf(
                navArgument("color_count") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val colorCount = backStackEntry.arguments?.getInt("color_count")
            // TODO(change to default value rather then crash)
            GameScreen(
                navController = navController,
                colorCount = colorCount ?: throw IllegalStateException("no color count passed")
            )
        }

        composable(route = Screen.HighScores.route,
            arguments = listOf(
                navArgument("recent_score") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                },
                navArgument("color_count") {
                    type = NavType.StringType
                    defaultValue = "5"
                }
            )
        ) { backStackEntry ->
            val recentScore = backStackEntry.arguments?.getString("recent_score")?.toLongOrNull()
            val colorCount = backStackEntry.arguments?.getString("color_count")?.toIntOrNull()
            ResultsScreen(navController = navController, recentScore = recentScore, colorCount = colorCount)
        }
    }
}