package com.example.masterand.repositories

import com.example.masterand.daos.ScoreDao
import com.example.masterand.entities.Score
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScoresRepositoryImpl @Inject constructor(
    private val scoreDao: ScoreDao
): ScoresRepository {
    override suspend fun insert(score: Score): Long {
        return scoreDao.insert(score)
    }
}