package com.majasociet.nafusitemobileapp.features.auth.ui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

class WelcomeRegistrationScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun welcomeScreen_displaysWelcomeTextAndButtons() {
        composeTestRule.setContent {
            WelcomeRegistrationScreen(
                navigateRegistrationOnboarding = {},
                navigateLogin = {}
            )
        }

        composeTestRule.onNodeWithText("Welcome to Nafusite").assertExists()
        composeTestRule.onNodeWithText("Get Started").assertExists()
        composeTestRule.onNodeWithText("Login").assertExists()
    }

    @Test
    fun welcomeScreen_getStartedClick_triggersNavigation() {
        val onGetStartedClick = mockk<() -> Unit>(relaxed = true)
        
        composeTestRule.setContent {
            WelcomeRegistrationScreen(
                navigateRegistrationOnboarding = onGetStartedClick,
                navigateLogin = {}
            )
        }

        composeTestRule.onNodeWithText("Get Started").performClick()
        
        verify { onGetStartedClick() }
        confirmVerified(onGetStartedClick)
    }

    @Test
    fun welcomeScreen_loginClick_triggersNavigation() {
        val onLoginClick = mockk<() -> Unit>(relaxed = true)

        composeTestRule.setContent {
            WelcomeRegistrationScreen(
                navigateRegistrationOnboarding = {},
                navigateLogin = onLoginClick
            )
        }

        composeTestRule.onNodeWithText("Login").performClick()

        verify { onLoginClick() }
        confirmVerified(onLoginClick)
    }
}
