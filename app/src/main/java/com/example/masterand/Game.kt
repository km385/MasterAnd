package com.example.masterand

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private const val TAG = "Game"

data class RowState(
    val selectedColors: List<Color>,
    val feedbackColors: List<Color>,
    val isClickable: Boolean
)

fun selectNextAvailableColor(availableColors: List<Color>,
                             selectedColors: List<Color>,
                             buttonIndex: Int): Color {
    val currentColor = selectedColors[buttonIndex]
    val usedColors = selectedColors.filterIndexed { index, _ -> index != buttonIndex }
    val unusedColors = availableColors.filter { it !in usedColors }
    if (unusedColors.isEmpty()) return availableColors.first()

    val currentIndex = unusedColors.indexOf(currentColor)

    return if (currentIndex == -1 || currentIndex == unusedColors.size - 1) {
        unusedColors.first()
    } else {
        unusedColors[currentIndex + 1]
    }
}

fun selectRandomColors(availableColors: List<Color>): List<Color> {
    return availableColors.shuffled().take(4)
}

fun checkColors(
    selectedColors: List<Color>,
    correctColors: List<Color>,
    notFoundColor: Color
): List<Color> {
    val feedbackColors = MutableList(4) { notFoundColor }

    for (i in selectedColors.indices) {
        if (selectedColors[i] == correctColors[i]) {
            feedbackColors[i] = Color.Red
        }
    }

    val remainingSelected = selectedColors.filterIndexed { i, color -> feedbackColors[i] != Color.Red }
    val remainingCorrect = correctColors.filterIndexed { i, color -> feedbackColors[i] != Color.Red }

    for (color in remainingSelected) {
        if (color in remainingCorrect) {
            feedbackColors[selectedColors.indexOf(color)] = Color.Yellow
        }
    }

    return feedbackColors
}

@Composable
fun GameScreen(

) {
    val availableColors = listOf(Color.Red, Color(0xFFFFA500), Color.Blue, Color.Yellow, Color.Black, Color(0xFF00FFFF))
    val score = remember { mutableIntStateOf(0) }
    var rows = remember {
        mutableStateListOf(
            RowState(
                selectedColors = List(4) { Color.White },
                feedbackColors = List(4) { Color.White },
                isClickable = true
            )
        )
    }
    val correctColors = remember { selectRandomColors(availableColors) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Your score: ${score.intValue}",
            fontSize = 30.sp
        )
        LazyColumn {
            items(rows.size) { rowIndex ->
                val rowState = rows[rowIndex]
                Spacer(modifier = Modifier.size(25.dp))
                GameRow(
                    selectedColors = rowState.selectedColors,
                    clickable = rowState.isClickable,
                    feedbackColors = rowState.feedbackColors,
                    onSelectColorClick = { index ->
                        if (!rowState.isClickable) {
                            return@GameRow
                        }
                        val newSelectedColors = rowState.selectedColors.toMutableList().apply {
                            this[index] = selectNextAvailableColor(
                                availableColors = availableColors,
                                selectedColors = this,
                                buttonIndex = index
                            )
                        }
                        rows[rowIndex] = rowState.copy(
                            selectedColors = newSelectedColors
                        )
                    },
                    onCheckClick = {
                        if (!rowState.isClickable || rowState.selectedColors.contains(Color.White)) {
                            return@GameRow
                        }

                        val feedback = checkColors(rowState.selectedColors, correctColors, Color.White)
                        rows[rowIndex] = rowState.copy(
                            feedbackColors = feedback,
                            isClickable = false
                        )

                        if (!feedback.all { it == Color.Red }) {
                            rows.add(RowState(
                                selectedColors = List(4) { Color.White },
                                feedbackColors = List(4) { Color.White },
                                isClickable = true
                            ))
                        }
                        score.value += 1
                    }
                )
            }

        }

        Button(onClick = {
            rows.clear()
            rows.add(
                RowState(
                    selectedColors = List(4) { Color.White },
                    feedbackColors = List(4) { Color.White },
                    isClickable = true
                )
            )
        }) {
            Text("replay")
        }
    }
}

@Composable
fun GameRow(
    selectedColors: List<Color>,
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
        SelectableColorsRow(selectedColors) { index -> onSelectColorClick(index) }
        IconButton(
            onClick = { onCheckClick() },
            modifier = Modifier
                .clip(CircleShape)
                .size(50.dp)
                .background(color = MaterialTheme.colorScheme.background),
            colors = IconButtonDefaults.filledIconButtonColors(),
            enabled = clickable
        ) {
            Icon(Icons.Filled.Check, contentDescription = "")
        }
        FeedbackCircles(feedbackColors)

    }
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
fun SelectableColorsRow(colors: List<Color>, onClick: (Int) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        colors.forEachIndexed() { index, color ->
            CircularButton(
                onClick = { onClick(index) },
                color = color
            )
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

@Preview(showBackground = true)
@Composable
fun GamePreview() {
    GameScreen()
}