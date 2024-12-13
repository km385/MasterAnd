package com.example.masterand.containers

import com.example.masterand.repositories.PlayersRepository


interface AppContainer {
    val playersRepository: PlayersRepository
//    val scoresRepository: ScoresRepository
//    val playerScoresRepository: PlayerScoreRepository
}