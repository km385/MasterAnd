package com.example.masterand.viewModels

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.masterand.entities.Player
import com.example.masterand.repositories.PlayerScoresRepository
import com.example.masterand.repositories.PlayersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val playersRepository: PlayersRepository,
    private val playersScoresRepository: PlayerScoresRepository
) : ViewModel()
{
    var playerId = mutableStateOf(0L)
    val name = mutableStateOf("")
    val email = mutableStateOf("")
    val imageBitmap = mutableStateOf<Bitmap?>(null)
    val imageUri = mutableStateOf<String?>(null)

    suspend fun delete() {
        val currentPlayer = playersRepository.getCurrentPlayerId()
        currentPlayer.value?.let {
            playersScoresRepository.deletePlayersScores(it)
        }
    }

    suspend fun loadPlayer() {
        val currentPlayer = playersRepository.getCurrentPlayerId()
        currentPlayer.value?.let {
            var player = playersRepository.getPlayerStream(it).first()
            if (player != null) {
                name.value = player.name
                email.value = player.email
                playerId.value = player.playerId
                imageBitmap.value = player.imageData?.let { byteArray ->
                    byteArrayToBitmap(byteArray)
                }
            }

        }
    }

    private fun byteArrayToBitmap(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    suspend fun savePlayer(context: Context) {
        val players = playersRepository.getPlayersByEmail(email.value)
        var player: Player

        // return null if no uri passed
        val imageByteArray = imageUri.value?.let { uri ->
            try {
                uriToByteArray(context, Uri.parse(uri))
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        if (players.isEmpty()) {
            player = Player(
                name = name.value,
                email = email.value,
                imageData = imageByteArray
            )
            val playerId = playersRepository.insert(player)
            player = playersRepository.getPlayerStream(playerId).first()!!
        } else {
            player = players.first()
            player.name = name.value
            // preserve data if image exist but no uri passed
            player.imageData = imageByteArray ?: player.imageData
            playersRepository.update(player)
        }
        playersRepository.setCurrentPlayerId(player.playerId)
    }

    private fun uriToByteArray(context: Context, uri: Uri): ByteArray {
        val inputStream = context.contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        inputStream?.close()

        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }
}