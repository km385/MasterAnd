package com.example.masterand.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.masterand.entities.PlayerWithScore
import com.example.masterand.repositories.PlayerScoresRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

//@HiltViewModel
class ResultsViewModel /*@Inject*/ constructor(private val playerScoresRepository: PlayerScoresRepository) :
    ViewModel() {
    // TODO(te do usuniecia, to jest tylko placeholder)
    private val _playerScores = mutableStateListOf<Pair<String, Int>>()
    val playerScores: List<Pair<String, Int>> = _playerScores

    var recentScore by mutableStateOf(0)
        private set

    fun loadPlayerScores()/*: Flow<List<PlayerWithScore>> */{
//        return playerScoresRepository.loadPlayersWithScores()
        viewModelScope.launch {
            // Example of loading scores; replace this with real data
            _playerScores.clear()
            _playerScores.addAll(
                listOf(
                    "Name1" to 2,
                    "Name1" to 2,
                    "Name2" to 5,
                    "Name2" to 6
                )
            )
            recentScore = 4 // Example recent score
        }
    }
}