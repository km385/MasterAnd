package com.example.masterand.repositories

import com.example.masterand.daos.PlayerDao
import com.example.masterand.entities.Player
import kotlinx.coroutines.flow.Flow


class PlayersRepositoryImpl(private val playerDao: PlayerDao) : PlayersRepository {
    override fun getAllPlayersStream(): Flow<List<Player>> {
        TODO("Not yet implemented")
    }

    override fun getPlayerStream(playerId: Int): Flow<Player?> =
        playerDao.getPlayerStream(playerId)
    override suspend fun getPlayersByEmail(email: String): List<Player> =
        playerDao.getPlayersByEmail(email)
    override suspend fun insertPlayer(player: Player): Long = playerDao.insert(player)
}