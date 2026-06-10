package com.example.animetracker.data.model

data class AnimeFilter(

    val query: String = "",

    val status: String? = null,

    val orderBy: String? = null,

    val sort: String? = null
)