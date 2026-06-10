package com.example.animetracker.repository

import com.example.animetracker.data.local.AnimeDao
import com.example.animetracker.data.local.AnimeEntity
import com.example.animetracker.data.model.AnimeDetailResponse
import com.example.animetracker.data.model.AnimeFilter
import com.example.animetracker.data.remote.RetrofitInstance


class AnimeRepository(

    private val dao: AnimeDao

) {
//    suspend fun getTopAnime() =
//        RetrofitInstance.api.getTopAnime()

    suspend fun getTopAnime(page: Int) =
        RetrofitInstance.api.getTopAnime(page)

        //  suspend fun getAllAnime(page: Int) =
        // RetrofitInstance.api.getAllAnime(page)

    suspend fun getAnimeDetails(
        animeId: Int
    ): AnimeDetailResponse? {

        return try {

            RetrofitInstance.api.getAnimeDetails(animeId)

        } catch (e: Exception) {

            null
        }
    }

suspend fun getTrendingAnime() =
    RetrofitInstance.api.getTrendingAnime()

suspend fun getSeasonalAnime() =
    RetrofitInstance.api.getSeasonalAnime()

    suspend fun searchAnime(query: String) =
        RetrofitInstance.api.searchAnime(
            query
        )

        suspend fun getRecommendations() =
    RetrofitInstance.api.getRecommendations()

    suspend fun getAllAnime(page: Int,filter: AnimeFilter) =
    RetrofitInstance.api.getAllAnime(

        page = page,

        query = filter.query,

        status = filter.status,

        orderBy = filter.orderBy,

        sort = filter.sort
    )

    suspend fun addFavorite(
        anime: AnimeEntity
    ) {
        dao.insert(anime)
    }

    suspend fun removeFavorite(
        anime: AnimeEntity
    ) {
        dao.delete(anime)
    }

    fun getFavorites() =
        dao.getAllFavorites()
}
