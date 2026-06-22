package com.majasociet.nafusitemobileapp.features.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.majasociet.nafusitemobileapp.features.auth.ui.AppSplashScreen
import com.majasociet.nafusitemobileapp.features.auth.ui.BasicInfoRegistrationScreen
import com.majasociet.nafusitemobileapp.features.auth.ui.PasswordSetupScreen
import com.majasociet.nafusitemobileapp.features.auth.ui.UserPreferenceSelectionScreen
import com.majasociet.nafusitemobileapp.features.auth.ui.WelcomeRegistrationScreen
import com.majasociet.nafusitemobileapp.navigation.Screen

/**
 * Registration navigation graph
 * @param navController
 */
fun NavGraphBuilder.registrationNavGraph(
    navController: NavHostController
){
    navigation(
        route = Screen.RegistrationGraph.route,
        startDestination = Screen.WelcomeRegistrationScreen.route
    ){
        composable(
            route = Screen.AppSplashScreen.route
        ){
            AppSplashScreen()
        }

        composable(
            route = Screen.WelcomeRegistrationScreen.route
        ){
            WelcomeRegistrationScreen(
                navigateRegistrationOnboarding = {
                    navController.navigate(
                        Screen.BasicInfoRegistrationScreen.route
                    )
                }
            )
        }
        composable(
            route = Screen.BasicInfoRegistrationScreen.route
        ){
            BasicInfoRegistrationScreen(
                navigateUserPreferenceSelection = {
                    navController.navigate(
                        Screen.UserPreferenceSelectionScreen.route
                    )
                }
            )
        }

        composable(
            route = Screen.UserPreferenceSelectionScreen.route
        ) {
            UserPreferenceSelectionScreen(
                navigatePasswordSetup = {
                    navController.navigate(
                        Screen.PasswordSetupScreen.route
                    )
                }
            )
        }

        composable(
            route = Screen.PasswordSetupScreen.route
        ) {
            PasswordSetupScreen(
                navigateHome = {
                    navController.navigate(
                        Screen.HomeGraph.route
                    )
                }
            )
        }
    }
}