package com.example.masterand

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private const val TAG = "Game"
@Composable
fun Game(

) {
    GameScreen()

}

@Composable
fun GameScreen(

) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Your score"
        )
        val colors = listOf(Color.Red, Color.Green, Color.Blue, Color.Gray)
        GameRow(
            colors = colors,
            clickable = true,
            feedbackColors = colors,
            onSelectColorClick = {

            }
        ) {

        }

        Button(onClick = {

        }) {
            Text("replay")
        }
    }
}

@Composable
fun CircularButton(onClick: () -> Unit, color: Color) {
    Button(
        onClick = { onClick() },
        modifier = Modifier
            .size(50.dp)
            .background(color = MaterialTheme.colorScheme.background),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.outline),
        colors = ButtonDefaults
            .buttonColors(
                containerColor = color,
                contentColor = MaterialTheme.colorScheme.onBackground
            )

    ) { }
}

@Composable
fun SelectableColorsRow(colors: List<Color>, onClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        colors.forEach { color ->
            CircularButton(
                onClick = { onClick() },
                color = color
            )
        }
    }
}

@Composable
fun SmallCircle(color: Color) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(color)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = CircleShape
            )
    )
}

@Composable
fun FeedbackCircles(colors: List<Color>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.padding(bottom = 5.dp)
        ) {
            if (colors.isNotEmpty()) {
                SmallCircle(color = colors[0])
            }
            if (colors.size > 1) {
                SmallCircle(color = colors[1])
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            if (colors.size > 2) {
                SmallCircle(color = colors[2])
            }
            if (colors.size > 3) {
                SmallCircle(color = colors[3])
            }
        }
    }
}

@Composable
fun GameRow(
    colors: List<Color>,
    feedbackColors: List<Color>,
    clickable: Boolean,
    onSelectColorClick: (index: Int) -> Unit,
    onCheckClick: () -> Unit
    ) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        SelectableColorsRow(colors) {
            Log.d(TAG, "on colors row")

            // onclick
        }
        var clicked = clickable
        IconButton(
            onClick = {
                Log.d(TAG, "on icon button clicked $clicked" )
                clicked = !clicked
            },
            modifier = Modifier
                .clip(CircleShape)
                .size(50.dp)
                .background(color = MaterialTheme.colorScheme.background),
            colors = IconButtonDefaults.filledIconButtonColors(),
            enabled = clicked
        ) {
            Icon(Icons.Filled.Check, contentDescription = "")
        }
        FeedbackCircles(feedbackColors)

    }
}



@Preview(showBackground = true)
@Composable
fun GamePreview() {
    Game()
}