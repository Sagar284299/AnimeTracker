package com.example.animetracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.animetracker.data.local.AnimeEntity
import com.example.animetracker.data.local.DatabaseInstance
import com.example.animetracker.data.model.Anime
import com.example.animetracker.repository.AnimeRepository
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val repository: AnimeRepository
) : ViewModel() {

    val favorites = repository.getFavorites()

    fun addFavorite(anime: Anime) {
        viewModelScope.launch {
            repository.addFavorite(
                AnimeEntity(
                    ani_id = anime.mal_id,
                    title = anime.title,
                    imageUrl = anime.images.jpg.image_url,
                    score = anime.score
                )
            )
        }
    }

    fun removeFavorite(anime: Anime) {
        viewModelScope.launch {
            repository.removeFavorite(
                AnimeEntity(
                    ani_id = anime.mal_id,
                    title = anime.title,
                    imageUrl = anime.images.jpg.image_url,
                    score = anime.score
                )
            )
        }
    }

    companion object {

        val Factory: ViewModelProvider.Factory =
            viewModelFactory {

                initializer {

                    val application =
                        checkNotNull(this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])

                    val database =
                        DatabaseInstance.getDatabase(application)

                    val repository =
                        AnimeRepository(database.animeDao())

                    FavoritesViewModel(repository)
                }
            }
    }
}