package com.example.animetracker.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(anime: AnimeEntity)

    @Delete
    suspend fun delete(anime: AnimeEntity)

    @Query("SELECT * FROM favourite_Anime")
    fun getAllFavorites(): Flow<List<AnimeEntity>>

    @Query("SELECT * FROM favourite_Anime WHERE ani_id = :id")
    suspend fun isFavorite(id: Int): AnimeEntity?

}