package com.example.masterand.viewModels

import androidx.lifecycle.ViewModel
import com.example.masterand.entities.PlayerWithScore
import com.example.masterand.repositories.PlayerScoresRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

//@HiltViewModel
class ResultsViewModel /*@Inject*/ constructor(
    private val playerScoresRepository: PlayerScoresRepository
) : ViewModel() {

    fun loadPlayerScores(): Flow<List<PlayerWithScore>>{
        return playerScoresRepository.loadPlayersWithScores()
            .map { players ->
                players.sortedBy { it.score }
            }
    }
}