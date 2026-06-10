package com.example.animetracker.data.model

data class Anime(

    val mal_id: Int,

    val title: String,

    val score: Double?,

    val episodes: Int?,

    val synopsis: String?,

    val images: AnimeImage
)
