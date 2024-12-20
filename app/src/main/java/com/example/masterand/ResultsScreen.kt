package com.example.masterand

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.masterand.entities.PlayerWithScore
//import com.example.masterand.providers.AppViewModelProvider
import com.example.masterand.viewModels.ResultsViewModel


@Composable
fun ResultsScreen(
    navController: NavController,
//    viewModel: ResultsViewModel = viewModel(factory = AppViewModelProvider.Factory),
    viewModel: ResultsViewModel = hiltViewModel<ResultsViewModel>(),
    recentScore: Long?,
    colorCount: Int?
) {
    val playersFlow = viewModel.loadPlayerScores()
    var playersScore by remember { mutableStateOf(emptyList<PlayerWithScore>()) }
    LaunchedEffect(playersFlow) {
        playersFlow.collect { newPlayer ->
            playersScore = newPlayer
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Results",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            if( recentScore != null) {
                Text(
                    text = "Recent score: $recentScore",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }


            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .border(1.dp, Color.Gray)
            ) {
                items(playersScore) { playerScore ->
                    ScoreRow(playerScore.playerName, playerScore.score.toString())
                    HorizontalDivider()
                }
            }



            Spacer(modifier = Modifier.height(24.dp))
            Row {
                Button(
                    onClick = {
                        navController.navigate(route = Screen.Game.passArguments(colorCount = colorCount ?: 5))
                    },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D75A3))
                ) {
                    Text(text = "New Game", color = Color.White)
                }


                Button(
                    onClick = {
                        navController.navigate(route = Screen.Login.route)
                    },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D75A3))
                ) {
                    Text(text = "Logout", color = Color.White)
                }

                Button(
                    onClick = {
                        navController.navigate(route = Screen.Profile.passArguments(colorCount = colorCount ?: 5))
                    },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D75A3))
                ) {
                    Text(text = "Profile", color = Color.White)
                }
            }

        }

    }
}

@Composable
fun ScoreRow(name: String, score: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = name, fontSize = 18.sp, fontWeight = FontWeight.Normal)
        Text(text = score, fontSize = 18.sp, fontWeight = FontWeight.Normal)
    }
}

@Composable
@Preview(showBackground = true)
fun ResultsScreenPreview() {
    ResultsScreen(
        navController = rememberNavController(),
        recentScore = 2L,
        colorCount = 5
    )
}