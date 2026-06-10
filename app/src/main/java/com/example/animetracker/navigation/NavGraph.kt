package com.example.animetracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.animetracker.ui.screens.AllAnimeScreen
import com.example.animetracker.ui.screens.AnimeDetailsScreen
import com.example.animetracker.ui.screens.FavoritesScreen
import com.example.animetracker.ui.screens.HomeScreen
import com.example.animetracker.ui.screens.SearchScreen
import com.example.animetracker.ui.screens.TopAnimeScreen

@Composable
fun NavGraph() {

    val navController =
        rememberNavController()

    NavHost(

        navController =
            navController,

        startDestination =
            Routes.HOME

    ) {
        composable(
            Routes.HOME
        ) {

            HomeScreen(
                navController
            )
        }

        composable(
            Routes.SEARCH
        ) {

            SearchScreen(
                navController
            )
        }

        composable(
            Routes.ALL_ANIME
        ) {

            AllAnimeScreen(
                navController
            )
        }


        composable(
            Routes.TOP_ANIME
        ) {

            TopAnimeScreen(
                navController
            )
        }


        composable(
            Routes.FAVORITES
        ) {

            FavoritesScreen()
        }

        composable(

            route =
                "${Routes.DETAILS}/{animeId}",

            arguments = listOf(

                navArgument(
                    "animeId"
                ) {

                    type =
                        NavType.IntType
                }
            )

        ) { backStackEntry ->

            val animeId =

                backStackEntry
                    .arguments
                    ?.getInt(
                        "animeId"
                    ) ?: 0

            AnimeDetailsScreen(
                animeId
            )
        }
    }
}