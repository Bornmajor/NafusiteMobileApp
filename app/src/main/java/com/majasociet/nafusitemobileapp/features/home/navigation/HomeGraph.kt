package com.majasociet.nafusitemobileapp.features.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.majasociet.nafusitemobileapp.features.home.ui.HomeScreen
import com.majasociet.nafusitemobileapp.navigation.Screen

fun NavGraphBuilder.homeGraph(
    navController: NavHostController
) {
    navigation(
        route = Screen.HomeGraph.route,
        startDestination = Screen.HomeScreen.route
    ){
        composable(
            route = Screen.HomeScreen.route
        ){
            HomeScreen()
        }
    }
}