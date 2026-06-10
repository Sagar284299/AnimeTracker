package com.example.animetracker.data.local

import android.content.Context
import androidx.room.Room

object DatabaseInstance {

    fun getDatabase(context: Context): AnimeDatabase {

        return Room.databaseBuilder(
            context,
            AnimeDatabase::class.java,
            "anime_db"
        ).build()
    }
}