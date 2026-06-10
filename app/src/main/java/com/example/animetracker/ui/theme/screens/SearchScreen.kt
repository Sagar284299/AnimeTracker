
package com.example.animetracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.animetracker.navigation.Routes
import com.example.animetracker.ui.screens.components.AnimeCard
import com.example.animetracker.viewmodel.SearchViewModel

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = viewModel(factory = SearchViewModel.Factory)
) {

    val query by viewModel.searchQuery.collectAsState()
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp)
    ) {

        OutlinedTextField(
            value = query,
            onValueChange = {
                viewModel.onQueryChange(it)
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Search Anime") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        when (state) {

            is SearchViewModel.UiState.Loading -> {
                CircularProgressIndicator()
            }

            is SearchViewModel.UiState.Error -> {
                Text(
                    text = (state as SearchViewModel.UiState.Error).message
                )
            }

            is SearchViewModel.UiState.Success -> {

                val data =
                    (state as SearchViewModel.UiState.Success).data

                LazyColumn {
                    items(data) { anime ->
                        AnimeCard(
                            anime = anime,
                            onClick = {
                                navController.navigate("${Routes.DETAILS}/${anime.mal_id}")
                            }
                        )
                    }
                }
            }

            else -> {
                Text("Search something...")
            }
        }
    }
}

