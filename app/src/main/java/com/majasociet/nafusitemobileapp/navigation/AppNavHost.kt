package com.majasociet.nafusitemobileapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.majasociet.nafusitemobileapp.features.auth.navigation.registrationNavGraph
import com.majasociet.nafusitemobileapp.features.home.navigation.homeGraph

/**
 * This is the main navigation host for the app.
 * @param navController The navigation controller to use passed from the main activity.
 */
@Composable
fun AppNavHost(
navController: NavHostController
){
    NavHost(
        navController = navController,
        startDestination = Screen.RegistrationGraph.route
    ){

        //Registration navigation graph
       registrationNavGraph(navController)
        // Home
        homeGraph(navController)
        //Other navigation graphs

    }
}
