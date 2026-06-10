package com.example.animetracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.animetracker.viewmodel.FavoritesViewModel

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel =
        viewModel(factory = FavoritesViewModel.Factory)
) {

    val favorites by
    viewModel.favorites.collectAsState(
        initial = emptyList()
    )

    if (favorites.isEmpty()) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),

            contentAlignment = Alignment.Center
        ) {

            Column(
                horizontalAlignment =
                    Alignment.CenterHorizontally
            ) {

                Text(
                    text = "❤️ No Favorites Yet",
                    style =
                        MaterialTheme.typography.headlineSmall
                )

                Spacer(
                    modifier =
                        Modifier.height(8.dp)
                )

                Text(
                    text = "Start adding anime to your favorites",
                    style =
                        MaterialTheme.typography.bodyMedium
                )
            }
        }

    } else {

        LazyColumn(

            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),

            contentPadding =
                PaddingValues(16.dp),

            verticalArrangement =
                Arrangement.spacedBy(12.dp)

        ) {

            items(favorites) { anime ->

                Card(

                    modifier =
                        Modifier.fillMaxWidth(),

                    elevation =
                        CardDefaults.cardElevation(6.dp)

                ) {

                    Row(

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),

                        horizontalArrangement =
                            Arrangement.SpaceBetween

                    ) {

                        Column(
                            modifier =
                                Modifier.weight(1f)
                        ) {

                            Text(
                                text = anime.title,
                                style =
                                    MaterialTheme.typography.titleMedium
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(4.dp)
                            )

                            Text(
                                text = "⭐ ${anime.score ?: "N/A"}",
                                style =
                                    MaterialTheme.typography.bodyMedium
                            )
                        }

                        Text(
                            text = "❤️",
                            style =
                                MaterialTheme.typography.headlineSmall
                        )
                    }
                }
            }
        }
    }
}