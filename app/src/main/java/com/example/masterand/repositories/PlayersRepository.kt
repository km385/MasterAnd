package com.example.masterand.repositories

import com.example.masterand.entities.Player
import kotlinx.coroutines.flow.Flow

interface PlayersRepository {
    fun getAllPlayersStream(): Flow<List<Player>>
    fun getPlayerStream(id: Int): Flow<Player?>
    suspend fun getPlayersByEmail(email: String): List<Player>
    suspend fun insertPlayer(player: Player) : Long
}