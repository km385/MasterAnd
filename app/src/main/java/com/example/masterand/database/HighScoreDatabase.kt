package com.example.masterand.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.masterand.daos.PlayerDao
import com.example.masterand.daos.PlayerScoreDao
import com.example.masterand.daos.ScoreDao
import com.example.masterand.entities.Player
import com.example.masterand.entities.Score

@Database(
    entities = [Score::class, Player::class],
    version = 1,
    exportSchema = false
)
abstract class HighScoreDatabase: RoomDatabase() {
    abstract fun playerDao(): PlayerDao
    abstract fun playerScoreDao(): PlayerScoreDao
    abstract fun scoreDao(): ScoreDao


    companion object {
        @Volatile
        private var Instance: HighScoreDatabase? = null
        fun getDatabase(context: Context): HighScoreDatabase {
            return Room.databaseBuilder(
                context,
                HighScoreDatabase::class.java,
                "highscore_database"
            )
                .build().also { Instance = it }
        }
    }
}