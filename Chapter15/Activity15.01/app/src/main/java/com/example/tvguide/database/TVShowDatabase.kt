package com.example.tvguide.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tvguide.model.TVShow

@Database(entities = [TVShow::class], version = 1)
abstract class TVShowDatabase : RoomDatabase() {

    abstract fun tvShowDao(): TVShowDao

    companion object {
        @Volatile
        private var instance: TVShowDatabase? = null
        fun getInstance(context: Context): TVShowDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): TVShowDatabase {
            return Room.databaseBuilder(context, TVShowDatabase::class.java, "tvshows-db")
                .build()
        }
    }
}