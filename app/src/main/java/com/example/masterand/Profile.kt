package com.example.firstapp

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.masterand.Screen

private const val TAG = "Profile"

//@Composable
//fun SecondScreenInitial(navController: NavController) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center) {
////        GifExample()
//        Spacer(modifier = Modifier.width(100.dp))
//        Button(onClick = { navController.navigate(route =
//        Screen.First.route) }) {
//            Text(text = "Cha! Cha-cha! Cha-cha-cha!")
//        }
//    }
//}


@Composable
fun ProfileCard(
    navController: NavController,
    login: String,
    description: String,
    profileUri: Uri,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
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
                ) {
                    AsyncImage(
                        model = profileUri,
                        contentDescription = "Profile photo",
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
//                    Image(
//                        painter = painterResource(R.drawable.baseline_question_mark_24),
//                        contentDescription = "",
//                        modifier = Modifier
//                            .size(64.dp)
//                            .clip(CircleShape)
//                    )
                }
                Column(
                    modifier = Modifier
                        .padding(start = 16.dp)
                ) {
                    Text(login)
                    Spacer(
                        modifier = Modifier.size(8.dp)
                    )
                    Text(description)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                FilledButtonExample(onClick = {
                    navController.navigate(route = Screen.First.route)
                }, text = "back")

                FilledButtonExample(onClick = {

                }, text = "play")

                FilledButtonExample(onClick = {

                }, text = "results")
            }

        }



        Example(onClick = {
            navController.navigate(route = Screen.First.route)
        })
    }
}

@Composable
fun Example(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        FloatingActionButton(
            onClick = { onClick() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
        ) {
            Icon(Icons.Filled.PlayArrow, contentDescription = "Floating action button")
        }
    }
}


@Composable
fun FilledButtonExample(onClick: () -> Unit, text:String) {
    Button(onClick = { onClick() }) {
        Text(text)
    }
}

//@Preview(showBackground = true)
//@Composable
//fun ProfileCardPreview() {
//    FirstAppTheme {
//        ProfileCard(Profile("Calvin", "description of the profile " +
//                "that is empty " +
//                "and it is a privew"))
//    }
//}
