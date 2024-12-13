package com.example.masterand

import android.net.Uri

sealed class Screen(val route: String) {

    object Login: Screen(route = "first_screen")
    object Profile: Screen(route = "second_screen/{login}/{description}/{profileUri}") {
        fun passArguments(login: String, description: String, profileUri: String): String {
            return "second_screen/$login/$description/${Uri.encode(profileUri)}"
        }
    }
    object Game: Screen(route = "game_screen")

    object HighScores: Screen(route = "high_scores")

}
