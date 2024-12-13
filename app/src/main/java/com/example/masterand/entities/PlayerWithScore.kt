package com.example.masterand.entities

data class PlayerWithScore(
    val scoreId: Long,
    val playerId: Long,

    val playerName: String,
    val score: Long
)

