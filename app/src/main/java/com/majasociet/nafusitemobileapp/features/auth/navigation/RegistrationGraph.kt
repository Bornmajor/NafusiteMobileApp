// File path: com/majasociet/nafusitemobileapp/features/auth/navigation/RegistrationNavGraph.kt
package com.majasociet.nafusitemobileapp.features.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.majasociet.nafusitemobileapp.features.auth.data.models.BasicRegistrationInfo
import com.majasociet.nafusitemobileapp.features.auth.ui.AppSplashScreen
import com.majasociet.nafusitemobileapp.features.auth.ui.BasicInfoRegistrationScreen
import com.majasociet.nafusitemobileapp.features.auth.ui.PasswordSetupScreen
import com.majasociet.nafusitemobileapp.features.auth.ui.UserPreferenceSelectionScreen
import com.majasociet.nafusitemobileapp.features.auth.ui.WelcomeRegistrationScreen
import com.majasociet.nafusitemobileapp.features.auth.ui.login.LoginScreen
import com.majasociet.nafusitemobileapp.features.auth.ui.viewmodel.AuthViewModel
import com.majasociet.nafusitemobileapp.features.profile.ui.viewmodel.ProfileViewModel
import com.majasociet.nafusitemobileapp.navigation.RegistrationGraph
import com.majasociet.nafusitemobileapp.navigation.Screen
import com.majasociet.nafusitemobileapp.navigation.CustomNavTypes
import kotlin.reflect.typeOf

fun NavGraphBuilder.registrationNavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    profileViewModel: ProfileViewModel
) {
    navigation<RegistrationGraph>(
        startDestination = Screen.WelcomeRegistrationScreen
    ) {
        composable<Screen.AppSplashScreen> {
            AppSplashScreen()
        }

        composable<Screen.LoginScreen> {
            LoginScreen(
                authViewModel = authViewModel,
                profileViewModel = profileViewModel,
                navigateRegistrationScreen = {
                    navController.navigate(Screen.WelcomeRegistrationScreen)
                }
            )
        }

        composable<Screen.WelcomeRegistrationScreen> {
            WelcomeRegistrationScreen(
                navigateRegistrationOnboarding = {
                    navController.navigate(Screen.BasicInfoRegistrationScreen)
                },
                navigateLogin = {
                    navController.navigate(Screen.LoginScreen)
                }
            )
        }

        composable<Screen.BasicInfoRegistrationScreen> {
            BasicInfoRegistrationScreen(
                //Pass BasicInfo details to UserPreferenceSelectionScreen
                onNextClick = { basicInfo ->
                        navController.navigate(Screen.UserPreferenceSelectionScreen(
                            registrationInfo = basicInfo
                        ))

                }
            )
        }

        composable<Screen.UserPreferenceSelectionScreen>(
            typeMap = mapOf(typeOf<BasicRegistrationInfo>() to CustomNavTypes.BasicRegistrationInfoType)
        ) { backStackEntry ->
            //Extract incoming data class args using toRoute()
            val args = backStackEntry.toRoute<Screen.UserPreferenceSelectionScreen>()
            val basicInfo = args.registrationInfo

            UserPreferenceSelectionScreen(
               onPreferenceConfirm = { preferences ->
                   navController.navigate(Screen.PasswordSetupScreen(
                       selectedPreferences = preferences,
                       registrationInfo = basicInfo
                   ))
               }


            )
        }

        composable<Screen.PasswordSetupScreen>(
            typeMap = mapOf(
                typeOf<BasicRegistrationInfo>() to CustomNavTypes.BasicRegistrationInfoType,
                typeOf<List<String>>() to CustomNavTypes.StringListType
            )
        ) { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.PasswordSetupScreen>()
            val basicInfo = args.registrationInfo
            val selectedPreferences = args.selectedPreferences

            PasswordSetupScreen(
                basicInfo = basicInfo,
                selectedPreferences = selectedPreferences,
                navigateLogin = {
                    navController.navigate(Screen.LoginScreen)
                },
                authViewModel = authViewModel
            )
        }
    }
}