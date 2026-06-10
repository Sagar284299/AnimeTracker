package com.example.animetracker.ui.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.animetracker.data.model.Anime
import com.example.animetracker.viewmodel.FavoritesViewModel

@Composable
fun AnimeCard(

    anime: Anime,

    onClick: () -> Unit,

    favoritesViewModel: FavoritesViewModel =
        viewModel(factory = FavoritesViewModel.Factory)

) {

    var isFavorite by remember {
        mutableStateOf(false)
    }

    Card(

        modifier =
            Modifier
                .fillMaxWidth()
                .clickable {
                    onClick()
                }

    ) {

        Row(

            modifier =
                Modifier.padding(12.dp)

        ) {

            AsyncImage(

                model =
                    anime.images.jpg.image_url,

                contentDescription =
                    anime.title,

                modifier =
                    Modifier.size(120.dp)
            )

            Spacer(
                modifier =
                    Modifier.width(12.dp)
            )

            Column(

                modifier =
                    Modifier.weight(1f)

            ) {

                Row(

                    modifier =
                        Modifier.fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.SpaceBetween,

                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Text(

                        text =
                            anime.title,

                        style =
                            MaterialTheme
                                .typography
                                .titleMedium,

                        modifier =
                            Modifier.weight(1f)
                    )

                    IconButton(

                        onClick = {

                            isFavorite =
                                !isFavorite

                            if (isFavorite) {

                                favoritesViewModel
                                    .addFavorite(anime)

                            } else {

                                favoritesViewModel
                                    .removeFavorite(anime)
                            }
                        }
                    ) {

                        Icon(

                            imageVector =

                                if (isFavorite)
                                    Icons.Filled.Favorite
                                else
                                    Icons.Outlined.FavoriteBorder,

                            contentDescription = null,

                            tint =

                                if (isFavorite)
                                    Color.Red
                                else
                                    Color.Gray
                        )
                    }
                }

                Spacer(
                    modifier =
                        Modifier.height(8.dp)
                )

                Text(
                    text =
                        "⭐ ${anime.score}"
                )

                Spacer(
                    modifier =
                        Modifier.height(4.dp)
                )

                Text(
                    text =
                        "Episodes: ${anime.episodes}"
                )
            }
        }
    }
}