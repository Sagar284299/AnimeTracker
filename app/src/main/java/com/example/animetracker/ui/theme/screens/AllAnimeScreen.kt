package com.example.animetracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.animetracker.data.model.AnimeFilter
import com.example.animetracker.navigation.Routes
import com.example.animetracker.ui.screens.components.AnimeCard
import com.example.animetracker.viewmodel.AnimeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllAnimeScreen(

    navController: NavController,

    viewModel: AnimeViewModel =
        viewModel(factory = AnimeViewModel.Factory)

) {

    val animeItems =
        viewModel
            .allAnimePagingFlow
            .collectAsLazyPagingItems()

    var query by remember {
        mutableStateOf("")
    }

    var selectedStatus by remember {
        mutableStateOf("All")
    }

    var selectedSort by remember {
        mutableStateOf("Popularity")
    }

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
            Arrangement.spacedBy(16.dp)

    ) {

        item {

            Column(

                verticalArrangement =
                    Arrangement.spacedBy(16.dp)

            ) {

                OutlinedTextField(

                    value = query,

                    onValueChange = {

                        query = it

                        viewModel.updateFilter(

                            AnimeFilter(

                                query = query,

                                status =
                                    if (selectedStatus == "All")
                                        null
                                    else
                                        selectedStatus.lowercase(),

                                orderBy =
                                    when (selectedSort) {

                                        "Popularity" ->
                                            "popularity"

                                        "Score" ->
                                            "score"

                                        "Favorites" ->
                                            "favorites"

                                        else ->
                                            "popularity"
                                    },

                                sort = "desc"
                            )
                        )
                    },

                    modifier =
                        Modifier.fillMaxWidth(),

                    label = {
                        Text("Search Anime")
                    },

                    singleLine = true
                )
                Column {

                    Text(
                        text = "Status",
                        style =
                            MaterialTheme.typography.titleMedium
                    )

                    Spacer(
                        modifier =
                            Modifier.height(10.dp)
                    )

                    LazyRow(

                        horizontalArrangement =
                            Arrangement.spacedBy(8.dp)

                    ) {

                        items(

                            listOf(
                                "All",
                                "Airing",
                                "Complete",
                                "Upcoming"
                            )

                        ) { status ->

                            FilterChip(

                                selected =
                                    selectedStatus == status,

                                onClick = {

                                    selectedStatus = status

                                    viewModel.updateFilter(

                                        AnimeFilter(

                                            query = query,

                                            status =
                                                if (status == "All")
                                                    null
                                                else
                                                    status.lowercase(),

                                            orderBy =
                                                when (selectedSort) {

                                                    "Popularity" ->
                                                        "popularity"

                                                    "Score" ->
                                                        "score"

                                                    "Favorites" ->
                                                        "favorites"

                                                    else ->
                                                        "popularity"
                                                },

                                            sort = "desc"
                                        )
                                    )
                                },

                                label = {
                                    Text(status)
                                }
                            )
                        }
                    }
                }
                Column {

                    Text(
                        text = "Sort By",
                        style =
                            MaterialTheme.typography.titleMedium
                    )

                    Spacer(
                        modifier =
                            Modifier.height(10.dp)
                    )

                    LazyRow(

                        horizontalArrangement =
                            Arrangement.spacedBy(8.dp)

                    ) {

                        items(

                            listOf(
                                "Popularity",
                                "Score",
                                "Favorites"
                            )

                        ) { sort ->

                            FilterChip(

                                selected =
                                    selectedSort == sort,

                                onClick = {

                                    selectedSort = sort

                                    viewModel.updateFilter(

                                        AnimeFilter(

                                            query = query,

                                            status =
                                                if (selectedStatus == "All")
                                                    null
                                                else
                                                    selectedStatus.lowercase(),

                                            orderBy =
                                                when (sort) {

                                                    "Popularity" ->
                                                        "popularity"

                                                    "Score" ->
                                                        "score"

                                                    "Favorites" ->
                                                        "favorites"

                                                    else ->
                                                        "popularity"
                                                },

                                            sort = "desc"
                                        )
                                    )
                                },

                                label = {
                                    Text(sort)
                                }
                            )
                        }
                    }
                }
            }
        }

        if (
            animeItems.loadState.refresh
                    is LoadState.Loading
        ) {

            item {

                Box(

                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 40.dp),

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

                    text =
                        "Failed To Load Anime",

                    color =
                        MaterialTheme.colorScheme.error
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

                    modifier =
                        Modifier
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