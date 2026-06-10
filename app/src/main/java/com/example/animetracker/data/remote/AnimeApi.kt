package com.example.animetracker.data.remote

import com.example.animetracker.data.model.AnimeDetailResponse
import com.example.animetracker.data.model.AnimeResponse
import com.example.animetracker.data.model.RecommendationResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimeApi {

//    @GET("top/anime")
//    suspend fun getTopAnime(): AnimeResponse

  @GET("anime")
suspend fun getAllAnime(
    @Query("page") page: Int,

    @Query("q")
    query: String? = null,

    @Query("status")
    status: String? = null,

    @Query("order_by")
    orderBy: String? = null,

    @Query("sort")
    sort: String? = null
): AnimeResponse

@GET("top/anime")
suspend fun getTopAnime(
    @Query("page") page: Int
): AnimeResponse

@GET("top/anime")
suspend fun getTrendingAnime(
    @Query("filter") filter: String = "bypopularity",
    @Query("page") page: Int = 1
): AnimeResponse

@GET("recommendations/anime")
suspend fun getRecommendations(): RecommendationResponse

@GET("seasons/now")
suspend fun getSeasonalAnime(): AnimeResponse

    @GET("anime/{id}")
    suspend fun getAnimeDetails(
        @Path("id") animeId: Int
    ): AnimeDetailResponse

    @GET("anime")

    suspend fun searchAnime(

        @Query("q")
        query: String

    ): AnimeResponse
}