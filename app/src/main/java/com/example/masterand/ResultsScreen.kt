package com.example.masterand

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.masterand.providers.AppViewModelProvider
import com.example.masterand.viewModels.ResultsViewModel


// Composable Screen
@Composable
fun ResultsScreen(
    viewModel: ResultsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    // Load player scores when the screen is loaded
    LaunchedEffect(Unit) {
        viewModel.loadPlayerScores()
    }

    // UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Results Title
        Text(
            text = "Results",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Recent Score
        Text(
            text = "Recent score: ${viewModel.recentScore}",
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Score Table
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Gray)
        ) {
            // Iterate through dynamic scores
            viewModel.playerScores.forEach { (name, score) ->
                ScoreRow(name, score.toString())
                HorizontalDivider()
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Restart Button
        Button(
            onClick = { /* Restart game action */ },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D75A3))
        ) {
            Text(text = "Restart game", color = Color.White)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Logout Button
        Button(
            onClick = { /* Logout action */ },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D75A3))
        ) {
            Text(text = "Logout", color = Color.White)
        }
    }
}

// Row for displaying player name and score
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
fun lolPreview() {
    ResultsScreen()
}