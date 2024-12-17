package com.example.masterand.repositories

import com.example.masterand.entities.Score

interface ScoresRepository {
    suspend fun insert(score: Score): Long
}