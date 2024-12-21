package com.example.masterand.repositories

import com.example.masterand.daos.PlayerScoreDao
import com.example.masterand.entities.PlayerWithScore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerScoresRepositoryImpl @Inject constructor(
    private val playerScoreDao: PlayerScoreDao
): PlayerScoresRepository {
    override fun loadPlayersWithScores(): Flow<List<PlayerWithScore>> =  playerScoreDao.loadPlayersWithScores();
    override suspend fun deletePlayersScores(playerId: Long) {
        playerScoreDao.deleteScoresByPlayerId(playerId)
    }

}