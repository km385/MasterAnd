package com.example.masterand.navigation

sealed class Screen(val route: String) {

    object Login: Screen(route = "first_screen")
    object Profile: Screen(route = "second_screen/{color_count}") {
        fun passArguments(colorCount: Int): String {
            return "second_screen/$colorCount"
        }
    }
    object Game: Screen(route = "game_screen/{color_count}") {
        fun passArguments(colorCount: Int): String {
            return "game_screen/$colorCount"
        }
    }

    object HighScores: Screen(route = "high_scores/{recent_score}/{color_count}") {
        fun passArguments(recentScore: Long?, colorCount: Int?): String {
            return "high_scores/$recentScore/$colorCount"
        }
    }

}
