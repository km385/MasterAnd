package com.example.masterand

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.masterand.ui.theme.MasterAndTheme

private const val TAG = "MainActivity"
class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MasterAndTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    ProfileScreen()
//                }
                navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }

        }

    }
}

//@Composable
//fun FirstScreenInitialll(
//    navController: NavController
//) {
//    Column(
////        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Button(
//            onClick = { navController.navigate(route = Screen.Game.route)
//            }
//        ) {
//            Text(text = "Kliknij mnie i zobacz co się stanie!")
//        }
//    }
//}

@Composable
fun ProfileScreen(
    navController: NavController
) {

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var name by rememberSaveable { mutableStateOf("imie") }
    var email by rememberSaveable { mutableStateOf("test@test.com") }
    var colors by rememberSaveable { mutableStateOf("5") }

    val isNameValid = name.isNotBlank()
    val isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val isColorsValid = colors.toIntOrNull()?.let { it in 5..10 } ?: false

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "MasterAnd",
            style = MaterialTheme.typography.displayLarge
        )

        ProfileImageWithPicker(
            imageUri = imageUri,
            onImagePicked = { imageUri = it }
        )

        OutlinedTextFieldWithError(
            value = name,
            onValueChange = { name = it },
            label = "Enter name",
            isError = !isNameValid,
            errorMessage = "Name cannot be empty",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        OutlinedTextFieldWithError(
            value = email,
            onValueChange = { email = it },
            label = "Enter email",
            isError = email.isNotBlank() && !isEmailValid,
            errorMessage = "Enter valid email address",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        OutlinedTextFieldWithError(
            value = colors,
            onValueChange = { colors = it },
            label = "Enter number of colors",
            isError = colors.isNotBlank() && !isColorsValid,
            errorMessage = "Number must be between 5 and 10",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Button(
            onClick = { navController.navigate(
//                route = Screen.Second.passArguments(
//                    login = name,
//                    description = "puste",
//                    profileUri = imageUri.toString()
//                )
                route = Screen.Profile.route
            )
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isNameValid && isEmailValid && isColorsValid
        ) {
            Text("Next")
        }
    }
}

@Composable
fun OutlinedTextFieldWithError(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean,
    errorMessage: String,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            isError = isError,
            keyboardOptions = keyboardOptions,
            singleLine = true,
            trailingIcon = {
                if (isError) {
                    Icon(Icons.Default.Warning, "error", tint = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        if (isError) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}

@Composable
fun ProfileImageWithPicker(
    imageUri: Uri?,
    onImagePicked: (Uri) -> Unit,
    modifier: Modifier = Modifier
) {
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { onImagePicked(it) }
    }

    Box(
        modifier = modifier
            .size(100.dp)
            .clickable { imagePickerLauncher.launch("image/*") },
        contentAlignment = Alignment.Center
    ) {
        if (imageUri != null) {
            AsyncImage(
                model = imageUri,
                contentDescription = "Profile photo",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            Icon(
                painter = painterResource(R.drawable.baseline_question_mark_24),
                contentDescription = "Select profile photo",
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Icon(
                painter = painterResource(R.drawable.baseline_image_search_24),
                contentDescription = "Select profile photo",
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.TopEnd),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

//@Preview
//@Composable
//fun ProfileScreenPreview() {
//    MasterAndTheme {
//        ProfileScreen()
//    }
//}