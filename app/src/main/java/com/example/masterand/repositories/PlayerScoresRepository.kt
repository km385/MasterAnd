package com.example.masterand.repositories

import com.example.masterand.entities.PlayerWithScore
import kotlinx.coroutines.flow.Flow

interface PlayerScoresRepository {
    fun loadPlayersWithScores(): Flow<List<PlayerWithScore>>
}