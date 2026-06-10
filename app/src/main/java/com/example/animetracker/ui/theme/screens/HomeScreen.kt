package com.example.animetracker.ui.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.animetracker.navigation.Routes
import com.example.animetracker.viewmodel.AnimeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: AnimeViewModel = viewModel(factory = AnimeViewModel.Factory)
) {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var visible by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        visible = true
    }

    ModalNavigationDrawer(

        drawerState = drawerState,

        drawerContent = {

            ModalDrawerSheet {

                Text(
                    text = "Anime Tracker",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleLarge
                )

                NavigationDrawerItem(
                    label = { Text("Home") },
                    selected = true,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                        }
                    }
                )

                NavigationDrawerItem(
                    label = { Text("All Anime") },
                    selected = false,
                    onClick = {
                        navController.navigate(Routes.ALL_ANIME)
                    }
                )

                NavigationDrawerItem(
                    label = { Text("Top Anime") },
                    selected = false,
                    onClick = {
                        navController.navigate(Routes.TOP_ANIME)
                    }
                )

                NavigationDrawerItem(
                    label = { Text("Favorites") },
                    selected = false,
                    onClick = {
                        navController.navigate(Routes.FAVORITES)
                    }
                )
            }
        }
    ) {

        Scaffold(

            topBar = {

                TopAppBar(

                    title = {
                        Text("Anime Tracker")
                    },

                    navigationIcon = {

                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {

                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = null
                            )
                        }
                    }
                )
            }

        ) { innerPadding ->

            LazyColumn(

                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),

                contentPadding = PaddingValues(16.dp),

                verticalArrangement = Arrangement.spacedBy(20.dp)

            ) {



                item {

                    AnimatedVisibility(
                        visible = visible,
                        enter = fadeIn(tween(500)) +
                                slideInVertically { 80 }
                    ) {

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate(Routes.SEARCH)
                                }
                        ) {

                            Row(
                                modifier = Modifier.padding(18.dp)
                            ) {

                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = null
                                )

                                Spacer(
                                    modifier = Modifier.width(12.dp)
                                )

                                Text("Search Anime...")
                            }
                        }
                    }
                }

                item {

                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor =
                                MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            Text(
                                text = "Welcome Back Anime Fan 🎌",
                                style = MaterialTheme.typography.headlineSmall
                            )

                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )

                            Text(
                                text = "Discover trending, seasonal and top rated anime."
                            )
                        }
                    }
                }
                item {

                    Text(
                        text = "💡 Recommended Anime",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }

                item {

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        items(viewModel.recommendedAnime.take(10)) { anime ->

                            ElevatedCard(

                                modifier = Modifier.width(220.dp),

                                onClick = {

                                    Log.d(
                                        "RECOMMEND_CLICK",
                                        "Anime Id = ${anime.mal_id}"
                                    )

                                    if (anime.mal_id > 0) {

                                        navController.navigate(
                                            "${Routes.DETAILS}/${anime.mal_id}"
                                        )
                                    }
                                }
                            ) {

                                Column(
                                    modifier = Modifier.padding(12.dp)
                                ) {

                                    Text(anime.title)

                                    Spacer(
                                        modifier = Modifier.height(6.dp)
                                    )

                                    Text("Recommended For You ⭐")
                                }
                            }
                        }
                    }
                }

                item {

                    Text(
                        text = "🔥 Trending Anime",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }

                item {

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        items(viewModel.trendingAnime) { anime ->

                            ElevatedCard(
                                modifier = Modifier.width(220.dp),

                                onClick = {
                                    navController.navigate(
                                        "${Routes.DETAILS}/${anime.mal_id}"
                                    )
                                }
                            ) {

                                Column(
                                    modifier = Modifier.padding(12.dp)
                                ) {

                                    Text(anime.title)

                                    Spacer(
                                        modifier = Modifier.height(6.dp)
                                    )

                                    Text("⭐ ${anime.score}")
                                }
                            }
                        }
                    }
                }

                item {

                    Text(
                        text = "🌸 Seasonal Anime",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }

                item {

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        items(viewModel.seasonalAnime) { anime ->

                            ElevatedCard(
                                modifier = Modifier.width(220.dp),

                                onClick = {
                                    navController.navigate(
                                        "${Routes.DETAILS}/${anime.mal_id}"
                                    )
                                }
                            ) {

                                Column(
                                    modifier = Modifier.padding(12.dp)
                                ) {

                                    Text(anime.title)

                                    Spacer(
                                        modifier = Modifier.height(6.dp)
                                    )

                                    Text("⭐ ${anime.score}")
                                }
                            }
                        }
                    }
                }

                item {

                    Text(
                        text = "🏆 Top Anime",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }

                item {

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        items(viewModel.topAnime) { anime ->

                            ElevatedCard(
                                modifier = Modifier.width(220.dp),

                                onClick = {
                                    navController.navigate(
                                        "${Routes.DETAILS}/${anime.mal_id}"
                                    )
                                }
                            ) {

                                Column(
                                    modifier = Modifier.padding(12.dp)
                                ) {

                                    Text(anime.title)

                                    Spacer(
                                        modifier = Modifier.height(6.dp)
                                    )

                                    Text("⭐ ${anime.score}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}