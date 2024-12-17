package com.example.masterand.daos

import androidx.room.Dao
import androidx.room.Query
import com.example.masterand.entities.PlayerWithScore
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerScoreDao {
    @Query(
        "SELECT players.playerId AS playerId, " +
                "players.name AS playerName," +
                "scores.scoreId AS scoreId, " +
                "scores.score AS score " +
                "FROM players, scores WHERE players.playerId = scores.playerId"
    )
    fun loadPlayersWithScores(): Flow<List<PlayerWithScore>>
}
