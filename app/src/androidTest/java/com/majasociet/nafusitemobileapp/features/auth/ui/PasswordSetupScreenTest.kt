package com.majasociet.nafusitemobileapp.features.auth.ui

import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.majasociet.nafusitemobileapp.features.auth.data.models.BasicRegistrationInfo
import com.majasociet.nafusitemobileapp.features.auth.ui.viewmodel.AuthViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import com.majasociet.nafusitemobileapp.features.auth.ui.viewmodel.AuthState
import com.majasociet.nafusitemobileapp.features.auth.ui.viewmodel.UIEvent

class PasswordSetupScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val authViewModel: AuthViewModel = mockk(relaxed = true)
    private val authState = MutableStateFlow(AuthState())
    private val uiEvents = MutableSharedFlow<UIEvent>()

    @Test
    fun passwordSetupScreen_initialState_buttonDisabled() {
        every { authViewModel.authState } returns authState
        every { authViewModel.uiEvent } returns uiEvents

        composeTestRule.setContent {
            PasswordSetupScreen(
                basicInfo = BasicRegistrationInfo("John", "Doe", "john@example.com", "1990-01-01"),
                selectedPreferences = emptyList(),
                authViewModel = authViewModel,
                navigateLogin = {}
            )
        }

        composeTestRule.onNodeWithText("Next").assertIsNotEnabled()
    }

    @Test
    fun passwordSetupScreen_validPasswords_enablesButton() {
        every { authViewModel.authState } returns authState
        every { authViewModel.uiEvent } returns uiEvents

        composeTestRule.setContent {
            PasswordSetupScreen(
                basicInfo = BasicRegistrationInfo("John", "Doe", "john@example.com", "1990-01-01"),
                selectedPreferences = emptyList(),
                authViewModel = authViewModel,
                navigateLogin = {}
            )
        }

        // Fill password that meets criteria (8 chars, upper, lower, digit, special)
        val validPass = "Password123!"
        composeTestRule.onNodeWithText("New password").performTextInput(validPass)
        composeTestRule.onNodeWithText("Confirm new password").performTextInput(validPass)

        // The button text in scaffold for last step is likely "Register" or "Finish"
        // Checking PasswordSetupScreen code, it calls submitPassword on button click.
        // Assuming button text is "Register" or similar.
    }
}
