package com.example.masterand.containers

import com.example.masterand.repositories.PlayerScoresRepository
import com.example.masterand.repositories.PlayersRepository
import com.example.masterand.repositories.ScoresRepository


interface AppContainer {
    val playersRepository: PlayersRepository
    val scoresRepository: ScoresRepository
    val playerScoresRepository: PlayerScoresRepository
}