package com.example.animetracker.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="favourite_Anime")
data class AnimeEntity(

    @PrimaryKey
    val ani_id  : Int,

    val title : String,

    val imageUrl: String,

    val score : Double?

)
