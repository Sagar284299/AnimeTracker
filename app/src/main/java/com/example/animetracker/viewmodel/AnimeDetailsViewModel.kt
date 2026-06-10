package com.example.animetracker.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.animetracker.data.local.AnimeEntity
import com.example.animetracker.data.local.DatabaseInstance
import com.example.animetracker.data.model.Anime
import com.example.animetracker.repository.AnimeRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AnimeDetailsViewModel(

    private val repository: AnimeRepository

) : ViewModel() {

    var anime by mutableStateOf<Anime?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun loadAnimeDetails(
        animeId: Int
    ) {

        viewModelScope.launch {

            try {

                isLoading = true

                errorMessage = null

                delay(500)

                val response =
                    repository.getAnimeDetails(
                        animeId
                    )

                anime = response?.data

            } catch (e: Exception) {

                e.printStackTrace()

                errorMessage =
                    "Failed To Load Anime Details"

            } finally {

                isLoading = false
            }
        }
    }

    fun addToFavorites() {

        anime?.let { currentAnime ->

            viewModelScope.launch {

                try {

                    repository.addFavorite(

                        AnimeEntity(

                            ani_id =
                                currentAnime.mal_id,

                            title =
                                currentAnime.title,

                            imageUrl =
                                currentAnime
                                    .images
                                    .jpg
                                    .image_url,

                            score =
                                currentAnime.score
                        )
                    )

                } catch (e: Exception) {

                    e.printStackTrace()
                }
            }
        }
    }

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

                    AnimeDetailsViewModel(
                        repository
                    )
                }
            }
    }
}