package com.example.masterand.viewModels

import androidx.compose.runtime.mutableLongStateOf
import androidx.lifecycle.ViewModel
import com.example.masterand.entities.Score
import com.example.masterand.repositories.PlayersRepository
import com.example.masterand.repositories.ScoresRepository
import kotlinx.coroutines.flow.first

//@HiltViewModel
class GameViewModel /*@Inject*/ constructor(
    playersRepository: PlayersRepository,
    private val scoresRepository: ScoresRepository
) : ViewModel() {
//    TODO("dodac get current players id")
//    var playerId = playersRepository.getCurrentPlayerId()
    var playerId = 2L
    var score = mutableLongStateOf(0L)

    suspend fun savePlayerScore() {
//        val playerIdValue = playerId.value ?: throw IllegalStateException("PlayerId is null")
        val playerIdValue = 2L
        val score = Score(playerId = playerIdValue, score = score.longValue)
        scoresRepository.insert(score)
    }
}