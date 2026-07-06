package com.majasociet.nafusitemobileapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.majasociet.nafusitemobileapp.features.auth.navigation.registrationNavGraph
import com.majasociet.nafusitemobileapp.features.auth.ui.viewmodel.AuthViewModel
import com.majasociet.nafusitemobileapp.features.home.navigation.homeGraph
import com.majasociet.nafusitemobileapp.features.profile.navigation.profileGraph
import com.majasociet.nafusitemobileapp.features.profile.ui.viewmodel.ProfileViewModel


/**
 * This is the main navigation host for the app.
 * @param navController The navigation controller to use passed from the main activity.
 */
@Composable
fun AppNavHost(
    authViewModel: AuthViewModel,
    profileViewModel: ProfileViewModel,
    navController: NavHostController
) {
    val authState = authViewModel.authState.collectAsStateWithLifecycle().value

    NavHost(
        navController = navController,
        startDestination = if(authState.isLogin) HomeGraph else RegistrationGraph
    ) {

        //Registration navigation graph
        registrationNavGraph(
            navController = navController,
            authViewModel = authViewModel,
            profileViewModel = profileViewModel
        )
        // Home
        homeGraph(
            navController =  navController,
            authViewModel = authViewModel
        )
        //Profile
        profileGraph(
            profileViewModel = profileViewModel,
            authViewModel = authViewModel,
            navController = navController,
        )

        //Other navigation graphs

    }
}
