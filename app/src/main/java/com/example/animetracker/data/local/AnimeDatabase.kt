
package com.example.animetracker.data.local
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [AnimeEntity::class],
    version = 4
)
abstract class AnimeDatabase : RoomDatabase() {

    abstract fun animeDao(): AnimeDao
}