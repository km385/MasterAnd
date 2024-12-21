package com.example.masterand

//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.example.masterand.providers.AppViewModelProvider
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.masterand.navigation.Screen
import com.example.masterand.navigation.SetupNavGraph
import com.example.masterand.ui.theme.MasterAndTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MasterAndTheme {
                navController = rememberNavController()
                BaseScreen(navController = navController)
            }

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScreen(navController: NavHostController) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination

    val screenTitles = mapOf(
        Screen.Login.route to "Login",
        Screen.Profile.route to "Profile",
        Screen.Game.route to "Game Screen",
        Screen.HighScores.route to "High Scores"
    )

    val title = screenTitles[currentDestination?.route] ?: "MasterAnd"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0D75A3),
                    titleContentColor = Color.White
                ),
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            SetupNavGraph(navController = navController)
        }
    }
}

