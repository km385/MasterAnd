package com.example.masterand

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun HighScores(navController: NavController) {
    Column() {

        Text("highscores lol", Modifier.padding(50.dp))

        Button(onClick = {
            navController.popBackStack()
        }) {
            Text("restart game")
        }

        Button(onClick = {
            navController.navigate(route = Screen.Login.route)
        }) {
            Text("log out")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun HighScoresPreview() {
    HighScores(navController = rememberNavController())
}