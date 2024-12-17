package com.example.masterand.repositories

import com.example.masterand.daos.PlayerScoreDao
import com.example.masterand.entities.PlayerWithScore
import kotlinx.coroutines.flow.Flow

class PlayerScoresRepositoryImpl(private val playerScoreDao: PlayerScoreDao): PlayerScoresRepository {
    override fun loadPlayersWithScores(): Flow<List<PlayerWithScore>> =  playerScoreDao.loadPlayersWithScores();

}