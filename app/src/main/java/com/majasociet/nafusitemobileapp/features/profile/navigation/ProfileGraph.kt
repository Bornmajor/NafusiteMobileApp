package com.majasociet.nafusitemobileapp.features.profile.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.majasociet.nafusitemobileapp.features.profile.ui.ProfileMainScreen
import com.majasociet.nafusitemobileapp.navigation.ProfileGraph
import com.majasociet.nafusitemobileapp.navigation.Screen
import androidx.navigation.compose.composable
import com.majasociet.nafusitemobileapp.features.auth.ui.viewmodel.AuthViewModel
import com.majasociet.nafusitemobileapp.features.profile.ui.UpdateProfileScreen
import com.majasociet.nafusitemobileapp.features.profile.ui.viewmodel.ProfileViewModel


fun NavGraphBuilder.profileGraph(
    navController: NavHostController,
    profileViewModel: ProfileViewModel,
    authViewModel: AuthViewModel
){

    navigation<ProfileGraph>(
        startDestination = Screen.ProfileMainScreen
    ) {

        composable<Screen.ProfileMainScreen> {
            ProfileMainScreen(
                navigateEditProfile = {
                    navController.navigate(Screen.UpdateProfileScreen)
                },
                logout = {
                    authViewModel.logoutUser()
                },
                profileViewModel = profileViewModel
            )
        }
        composable<Screen.UpdateProfileScreen> {
            UpdateProfileScreen(
                profileViewModel = profileViewModel,
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
