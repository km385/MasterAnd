package com.example.masterand

//import com.example.masterand.providers.AppViewModelProvider
//import androidx.lifecycle.viewmodel.compose.viewModel
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.masterand.viewModels.ProfileViewModel
import kotlinx.coroutines.launch

private const val TAG = "Profile"

@Composable
fun ProfileCard(
    navController: NavController,
//    viewModel: ProfileViewModel = viewModel(factory = AppViewModelProvider.Factory),
    viewModel: ProfileViewModel = hiltViewModel<ProfileViewModel>(),
    colorCount: Int
) {
    LaunchedEffect(Unit) {
        viewModel.loadPlayer()
    }

    val coroutineScope = rememberCoroutineScope()
    val name by viewModel.name
    val email by viewModel.email
    val imageUri by viewModel.imageUri

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .size(100.dp)
                        .clip(CircleShape)
                ) {

                    if (imageUri != "null") {
                        AsyncImage(
                            model = imageUri,
                            contentDescription = "Profile Image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            onError = { error ->
                                Log.i(TAG, "ProfileCard: ${error.result.throwable}")
                            }
                        )
                    } else {
                        Icon(
                            painter = painterResource(R.drawable.baseline_question_mark_24),
                            contentDescription = "Select profile photo",
                            modifier = Modifier.fillMaxSize(),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(start = 16.dp)
                ) {
                    Text(name)
                    Spacer(
                        modifier = Modifier.size(8.dp)
                    )
                    Text(email)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                FilledButton(onClick = {
                    navController.navigate(route = Screen.Login.route)
                }, text = "Log out")

                FilledButton(onClick = {
                    navController.navigate(Screen.Game
                        .passArguments(colorCount))
                }, text = "Play")

                FilledButton(onClick = {
                    navController.navigate(Screen.HighScores
                        .passArguments(recentScore = null, colorCount = colorCount))
                }, text = "High Scores")
            }

        }



        FloatingButton(onClick = {
            coroutineScope.launch {
                viewModel.delete()
            }
        })
    }
}

@Composable
fun FloatingButton(onClick: () -> Unit) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        FloatingActionButton(
            onClick = {
                onClick()
                Toast.makeText(context, "Scores deleted!", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
        ) {
            Text(
                "Delete scores",
                modifier = Modifier
                    .padding(16.dp)
            )
        }
    }
}


@Composable
fun FilledButton(onClick: () -> Unit, text:String) {
    Button(
        onClick = { onClick() },
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D75A3))
    ) {
        Text(text)
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileCardPreview() {
    ProfileCard(
        navController = rememberNavController(), colorCount = 2
    )
}
