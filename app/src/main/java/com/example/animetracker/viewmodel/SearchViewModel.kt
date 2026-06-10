package com.example.animetracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.animetracker.data.local.DatabaseInstance
import com.example.animetracker.data.model.Anime
import com.example.animetracker.repository.AnimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: AnimeRepository
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val searchQuery = _query.asStateFlow()

    sealed class UiState {
        object Idle : UiState()
        object Loading : UiState()
        data class Success(val data: List<Anime>) : UiState()
        data class Error(val message: String) : UiState()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {

            _query
                .debounce(500)
                .distinctUntilChanged()
                .collectLatest { query ->

                    if (query.isBlank()) {
                        _uiState.value = UiState.Idle
                        return@collectLatest
                    }

                    searchAnime(query)
                }
        }
    }

    fun onQueryChange(query: String) {
        _query.value = query
    }

    private suspend fun searchAnime(query: String) {
        try {
            _uiState.value = UiState.Loading

            val response = repository.searchAnime(query)

            _uiState.value = UiState.Success(response.data)

        } catch (e: Exception) {
            _uiState.value = UiState.Error(
                e.message ?: "Something went wrong"
            )
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    checkNotNull(
                        this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                    )

                val database = DatabaseInstance.getDatabase(application)

                val repository = AnimeRepository(database.animeDao())

                SearchViewModel(repository)
            }
        }
    }
}