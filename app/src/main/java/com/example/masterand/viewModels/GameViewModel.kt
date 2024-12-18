package com.example.masterand.viewModels

import androidx.compose.runtime.mutableLongStateOf
import androidx.lifecycle.ViewModel
import com.example.masterand.entities.Score
import com.example.masterand.repositories.PlayersRepository
import com.example.masterand.repositories.ScoresRepository

//@HiltViewModel
class GameViewModel /*@Inject*/ constructor(
    playersRepository: PlayersRepository,
    private val scoresRepository: ScoresRepository
) : ViewModel() {
    var playerId = playersRepository.getCurrentPlayerId()
    var score = mutableLongStateOf(0L)

    suspend fun savePlayerScore() {
        val playerIdValue = playerId.value ?: throw IllegalStateException("PlayerId is null")
        val score = Score(playerId = playerIdValue, score = score.longValue)
        scoresRepository.insert(score)
    }
}