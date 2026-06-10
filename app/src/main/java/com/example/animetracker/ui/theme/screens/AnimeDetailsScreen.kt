package com.example.animetracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.animetracker.viewmodel.AnimeDetailsViewModel

@Composable
fun AnimeDetailsScreen(

    animeId: Int,

    viewModel:
    AnimeDetailsViewModel =
        viewModel(factory = AnimeDetailsViewModel.Factory)

) {

    LaunchedEffect(
        animeId
    ) {

        viewModel
            .loadAnimeDetails(
                animeId
            )
    }

    val anime =
        viewModel.anime

    if (anime == null) {

        Box(

            modifier =
                Modifier.fillMaxSize()

        ) {

            CircularProgressIndicator()
        }

        return
    }

    Column(

        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp)

    ) {

        AsyncImage(

            model =anime.images.jpg.image_url,

            contentDescription =anime.title,

            modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
        )

        Spacer(
            modifier =
                Modifier.height(16.dp)
        )

        Text(

            text =
                anime.title,

            style =
                MaterialTheme
                    .typography
                    .headlineSmall
        )

        Spacer(
            modifier =
                Modifier.height(8.dp)
        )

        Text(
            text =
                "⭐ ${anime.score}"
        )

        Text(
            text =
                "Episodes: ${anime.episodes}"
        )

        Spacer(
            modifier =
                Modifier.height(16.dp)
        )

        Text(

            text =
                anime.synopsis
                    ?: "No Synopsis"
        )
    }
}

