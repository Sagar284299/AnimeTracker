package com.example.animetracker.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.animetracker.data.local.DatabaseInstance
import com.example.animetracker.data.model.Anime
import com.example.animetracker.data.model.AnimeFilter
import com.example.animetracker.data.model.RecommendationEntry
import com.example.animetracker.data.paging.AllAnimePagingSource
import com.example.animetracker.data.paging.TopAnimePagingSource
import com.example.animetracker.repository.AnimeRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class AnimeViewModel(
    private val repository: AnimeRepository
) : ViewModel() {

    var trendingAnime =
        mutableStateListOf<Anime>()
        private set

    var seasonalAnime =
        mutableStateListOf<Anime>()
        private set

    var topAnime =
        mutableStateListOf<Anime>()
        private set

    var recommendedAnime =
        mutableStateListOf<RecommendationEntry>()
        private set

    init {

        viewModelScope.launch {

            loadTrendingAnime()

            delay(1200)

            loadSeasonalAnime()

            delay(1200)

            loadTopAnime()

            delay(1200)

            loadRecommendedAnime()
        }
    }

    private suspend fun loadTrendingAnime() {

        try {

            val response =
                repository.getTrendingAnime()

            trendingAnime.clear()

            trendingAnime.addAll(
                response.data.take(10)
            )

        } catch (e: Exception) {

            e.printStackTrace()
        }
    }

    private suspend fun loadSeasonalAnime() {

        try {

            val response =
                repository.getSeasonalAnime()

            seasonalAnime.clear()

            seasonalAnime.addAll(
                response.data.take(10)
            )

        } catch (e: Exception) {

            e.printStackTrace()
        }
    }

    private suspend fun loadTopAnime() {

        try {

            val response =
                repository.getTopAnime(1)

            topAnime.clear()

            topAnime.addAll(
                response.data.take(10)
            )

        } catch (e: Exception) {

            e.printStackTrace()
        }
    }

    private suspend fun loadRecommendedAnime() {

        try {

            val response =
                repository.getRecommendations()

            recommendedAnime.clear()

            response.data.forEach { recommendation ->

                recommendedAnime.addAll(
                    recommendation.entry
                )
            }

        } catch (e: Exception) {

            e.printStackTrace()
        }
    }

    private val filterState =
        MutableStateFlow(
            AnimeFilter()
        )

    fun updateFilter(
        filter: AnimeFilter
    ) {

        filterState.value =
            filter
    }

    val topAnimePagingFlow =

        Pager(

            config = PagingConfig(

                pageSize = 25,

                enablePlaceholders = false
            ),

            pagingSourceFactory = {

                TopAnimePagingSource(
                    repository
                )
            }

        ).flow.cachedIn(viewModelScope)

    val allAnimePagingFlow =

        filterState.flatMapLatest { filter ->

            Pager(

                config = PagingConfig(

                    pageSize = 25,

                    enablePlaceholders = false
                ),

                pagingSourceFactory = {

                    AllAnimePagingSource(
                        repository,
                        filter
                    )
                }

            ).flow

        }.cachedIn(viewModelScope)

    companion object {

        val Factory: ViewModelProvider.Factory =

            viewModelFactory {

                initializer {

                    val application =
                        checkNotNull(
                            this[
                                ViewModelProvider
                                    .AndroidViewModelFactory
                                    .APPLICATION_KEY
                            ]
                        )

                    val database =
                        DatabaseInstance
                            .getDatabase(application)

                    val repository =
                        AnimeRepository(
                            database.animeDao()
                        )

                    AnimeViewModel(repository)
                }
            }
    }
}