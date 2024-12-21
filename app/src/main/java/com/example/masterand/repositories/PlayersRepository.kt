package com.example.masterand.repositories

import androidx.lifecycle.LiveData
import com.example.masterand.entities.Player
import kotlinx.coroutines.flow.Flow

interface PlayersRepository {
    fun getPlayerStream(id: Long): Flow<Player?>
    fun setCurrentPlayerId(id: Long)
    fun getCurrentPlayerId() : LiveData<Long>
    suspend fun getPlayersByEmail(email: String): List<Player>
    suspend fun insert(player: Player): Long
    suspend fun update(player: Player): Int
}