package com.example.masterand.containers

import android.content.Context
import com.example.masterand.database.HighScoreDatabase
import com.example.masterand.repositories.PlayerScoresRepository
import com.example.masterand.repositories.PlayerScoresRepositoryImpl
import com.example.masterand.repositories.PlayersRepository
import com.example.masterand.repositories.PlayersRepositoryImpl
import com.example.masterand.repositories.ScoresRepository
import com.example.masterand.repositories.ScoresRepositoryImpl


class AppDataContainer(private val context: Context) : AppContainer {
    override val playersRepository: PlayersRepository by lazy {
        PlayersRepositoryImpl(HighScoreDatabase.getDatabase(context).playerDao())
    }
    override val scoresRepository: ScoresRepository by lazy {
        ScoresRepositoryImpl(HighScoreDatabase.getDatabase(context).scoreDao())
    }
    override val playerScoresRepository: PlayerScoresRepository by lazy {
        PlayerScoresRepositoryImpl(HighScoreDatabase.getDatabase(context).playerScoreDao())
    }
}