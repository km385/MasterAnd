package com.example.masterand.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.masterand.entities.Player
import com.example.masterand.repositories.PlayersRepository

class ProfileViewModel(private val playersRepository: PlayersRepository) : ViewModel()
{
    var playerId = mutableStateOf(0L)
    val name = mutableStateOf("")
    val email = mutableStateOf("")
    suspend fun savePlayer() {
        //zapisać nowego gracza lub zaktualizować istniejącego
        //wykorzystując metody z repozytorium...

        val players = playersRepository.getPlayersByEmail(email.value)
        var player: Player

//        if (players.isEmpty()) {
//            player = Player(
//                name = name.value,
//                email = email.value
//            )
//            val playerId = playersRepository.insert(player)
//            player = playersRepository.getPlayersById(playerId).first()
//        } else {
//            player = players.first()
//            player.name = name.value
//            playersRepository.update(player)
//        }
//        playersRepository.setCurrentPlayerId(player.id)


    }
}