package com.majasociet.nafusitemobileapp.navigation

sealed class Screen(val route: String) {

    //Registration screens
    object RegistrationGraph : Screen("registration_graph")
    object AppSplashScreen : Screen("app_splash_screen")
    object WelcomeRegistrationScreen : Screen("welcome_registration_screen")
    object BasicInfoRegistrationScreen : Screen("basic_info_registration_screen")
    object UserPreferenceSelectionScreen : Screen("user_preference_selection_screen")
    object PasswordSetupScreen : Screen("password_setup_screen")

    // Home
    object HomeGraph : Screen("home_graph")
    object HomeScreen: Screen("home_screen")

}