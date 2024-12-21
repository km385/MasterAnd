package com.example.masterand.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.example.masterand.providers.AppViewModelProvider
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.masterand.R
import com.example.masterand.navigation.Screen
import com.example.masterand.viewModels.ProfileViewModel
import kotlinx.coroutines.launch


@Composable
fun Login(
    navController: NavController,
//    viewModel: ProfileViewModel = viewModel(factory = AppViewModelProvider.Factory)
    viewModel: ProfileViewModel = hiltViewModel<ProfileViewModel>()
) {


    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var name by rememberSaveable { mutableStateOf("imie") }
    var email by rememberSaveable { mutableStateOf("test@test.com") }
    var colors by rememberSaveable { mutableStateOf("5") }

    val isNameValid = name.isNotBlank()
    val isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val isColorsValid = colors.toIntOrNull()?.let { it in 5..10 } ?: false

    val coroutineScope = rememberCoroutineScope()

    val infiniteScaleAnimation = rememberInfiniteTransition(label = "infiniteScaleTitle")
    val titleScale by infiniteScaleAnimation.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "titleScale"
    )

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
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier
                .graphicsLayer(
                    scaleX = titleScale,
                    scaleY = titleScale,
                    transformOrigin = TransformOrigin.Center
                )
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
        val context = LocalContext.current
        Button(
            onClick = {
                viewModel.email.value = email
                viewModel.name.value = name
                viewModel.imageUri.value = imageUri.toString()


                coroutineScope.launch {
                    viewModel.savePlayer(context)
                    navController.navigate(
                        route = Screen.Profile.passArguments(colors.toInt())
                    )
                }

            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D75A3)),
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