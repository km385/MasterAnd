package com.example.masterand

import android.net.Uri

sealed class Screen(val route: String) {

    object First: Screen(route = "first_screen")
    object Second: Screen(route = "second_screen/{login}/{description}/{profileUri}") {
        fun passArguments(login: String, description: String, profileUri: String): String {
            return "second_screen/$login/$description/${Uri.encode(profileUri)}"
        }
    }
    object Third: Screen(route = "game_screen")

}
