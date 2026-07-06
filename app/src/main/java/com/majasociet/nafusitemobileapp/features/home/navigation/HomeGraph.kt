// File path: com/majasociet/nafusitemobileapp/features/home/navigation/HomeGraph.kt
package com.majasociet.nafusitemobileapp.features.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.majasociet.nafusitemobileapp.features.auth.ui.viewmodel.AuthViewModel
import com.majasociet.nafusitemobileapp.features.home.ui.HomeScreen
import com.majasociet.nafusitemobileapp.navigation.HomeGraph
import com.majasociet.nafusitemobileapp.navigation.Screen

fun NavGraphBuilder.homeGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    navigation<HomeGraph>(
        startDestination = Screen.HomeScreen
    ) {
        composable<Screen.HomeScreen> {
            HomeScreen(
                navigateToProfile = {
                    navController.navigate(
                        Screen.ProfileMainScreen
                    )
                },
                authViewModel = authViewModel
            )
        }
    }
}