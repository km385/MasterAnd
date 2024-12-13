package com.example.masterand.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.masterand.entities.Player
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(player: Player) : Long

    @Query("SELECT * from players WHERE playerId = :playerId")
    fun getPlayerStream(playerId: Int): Flow<Player>

    @Query("SELECT * from players WHERE email = :email")
    suspend fun getPlayersByEmail(email: String): List<Player>

    // dodane
    @Update
    suspend fun update(player: Player): Int
}