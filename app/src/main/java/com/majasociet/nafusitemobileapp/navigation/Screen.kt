// File path: com/majasociet/nafusitemobileapp/navigation/Screen.kt
package com.majasociet.nafusitemobileapp.navigation

import com.majasociet.nafusitemobileapp.features.auth.data.models.BasicRegistrationInfo
import kotlinx.serialization.Serializable

// Sub-graphs are now represented as simple serializable data structures
@Serializable
object RegistrationGraph
@Serializable
object HomeGraph
@Serializable
object ProfileGraph
sealed class Screen {
    @Serializable
    object AppSplashScreen
    @Serializable
    object WelcomeRegistrationScreen
    @Serializable
    object BasicInfoRegistrationScreen
    @Serializable
    data class UserPreferenceSelectionScreen(
        val registrationInfo: BasicRegistrationInfo
    )

    @Serializable
    data class PasswordSetupScreen(
        val registrationInfo: BasicRegistrationInfo,
        val selectedPreferences: List<String>
    )

    @Serializable
    object LoginScreen
    @Serializable
    object HomeScreen

    //Profile screens
    @Serializable
    object ProfileMainScreen
    @Serializable
    object UpdateProfileScreen
}
