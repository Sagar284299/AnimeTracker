package com.example.animetracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.animetracker.navigation.Routes
import com.example.animetracker.ui.screens.components.AnimeCard
import com.example.animetracker.viewmodel.AnimeViewModel

@Composable
fun TopAnimeScreen(

    navController: NavController,

    viewModel: AnimeViewModel =
        viewModel(factory = AnimeViewModel.Factory)

) {

    val animeItems =
        viewModel
            .topAnimePagingFlow
            .collectAsLazyPagingItems()

    LazyColumn(

        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),

        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 20.dp,
            bottom = 16.dp
        ),

        verticalArrangement =
            Arrangement.spacedBy(12.dp)

    ) {

        item {

            Text(

                text = "Top Rated Anime",

                style =
                    MaterialTheme
                        .typography
                        .headlineMedium
            )
        }

        if (
            animeItems.loadState.refresh
                    is LoadState.Loading
        ) {

            item {

                Box(

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),

                    contentAlignment =
                        Alignment.Center

                ) {

                    CircularProgressIndicator()
                }
            }
        }

        if (
            animeItems.loadState.refresh
                    is LoadState.Error
        ) {

            item {

                Text(

                    text = "Failed To Load Anime 😢",

                    color =
                        MaterialTheme
                            .colorScheme
                            .error
                )
            }
        }

        itemsIndexed(

            items =
                List(animeItems.itemCount) { it }

        ) { index, _ ->

            val anime =
                animeItems[index]

            anime?.let {

                AnimeCard(

                    anime = it,

                    onClick = {

                        navController.navigate(

                            "${Routes.DETAILS}/${it.mal_id}"
                        )
                    }
                )
            }
        }

        if (
            animeItems.loadState.append
                    is LoadState.Loading
        ) {

            item {

                Box(

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),

                    contentAlignment =
                        Alignment.Center

                ) {

                    CircularProgressIndicator()
                }
            }
        }
    }
}