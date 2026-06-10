package com.example.animetracker.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.animetracker.data.model.Anime
import com.example.animetracker.data.model.AnimeFilter
import com.example.animetracker.repository.AnimeRepository

class AllAnimePagingSource(
    private val repository: AnimeRepository,
    private val filter: AnimeFilter

) : PagingSource<Int, Anime>() {

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, Anime> {

        return try {

            val page = params.key ?: 1
            Log.d("PAGING", "Loading Page = $page")
            val response =
                // repository.getAllAnime(page)
                   repository.getAllAnime(page = page,filter = filter)

            LoadResult.Page(
                data = response.data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = page + 1
            )

        } catch (e: Exception) {

            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(
        state: PagingState<Int, Anime>
    ): Int? = null
}