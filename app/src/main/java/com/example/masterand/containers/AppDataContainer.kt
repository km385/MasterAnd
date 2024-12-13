package com.example.masterand.containers

import android.content.Context
import com.example.masterand.database.HighScoreDatabase
import com.example.masterand.repositories.PlayersRepository
import com.example.masterand.repositories.PlayersRepositoryImpl


class AppDataContainer(private val context: Context) : AppContainer {
    override val playersRepository: PlayersRepository by lazy {
        PlayersRepositoryImpl(HighScoreDatabase.getDatabase(context).playerDao())
    }
    //tutaj dodać implementację wszystkich repozytoriów...
}