package com.example.masterand.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.masterand.entities.Player
import com.example.masterand.repositories.PlayerScoresRepository
import com.example.masterand.repositories.PlayersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
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
                imageUri.value = player.profileImageUri
            }

        }
    }

    suspend fun savePlayer() {
        val players = playersRepository.getPlayersByEmail(email.value)
        var player: Player

        if (players.isEmpty()) {
            player = Player(
                name = name.value,
                email = email.value,
                profileImageUri = imageUri.value
            )
            val playerId = playersRepository.insert(player)
            player = playersRepository.getPlayerStream(playerId).first()!!
        } else {
            player = players.first()
            player.name = name.value
            if(imageUri.value != "null") {
                player.profileImageUri = imageUri.value
            } else {
                imageUri.value = player.profileImageUri
            }
            playersRepository.update(player)
        }
        playersRepository.setCurrentPlayerId(player.playerId)
    }
}