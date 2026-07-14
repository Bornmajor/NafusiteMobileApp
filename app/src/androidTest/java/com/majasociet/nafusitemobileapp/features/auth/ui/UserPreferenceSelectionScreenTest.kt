package com.majasociet.nafusitemobileapp.features.auth.ui

import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

class UserPreferenceSelectionScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun preferenceScreen_displaysOptions() {
        composeTestRule.setContent {
            UserPreferenceSelectionScreen(onPreferenceConfirm = {})
        }

        composeTestRule.onNodeWithText("Necklaces").assertExists()
        composeTestRule.onNodeWithText("Bracelets").assertExists()
        composeTestRule.onNodeWithText("Luxury Watches").assertExists()
    }

    @Test
    fun preferenceScreen_initialState_buttonDisabled() {
        composeTestRule.setContent {
            UserPreferenceSelectionScreen(onPreferenceConfirm = {})
        }

        composeTestRule.onNodeWithText("Next").assertIsNotEnabled()
    }

    @Test
    fun preferenceScreen_selectingItem_triggersConfirm() {
        val onConfirm = mockk<(List<String>) -> Unit>(relaxed = true)
        
        composeTestRule.setContent {
            UserPreferenceSelectionScreen(onPreferenceConfirm = onConfirm)
        }

        composeTestRule.onNodeWithText("Necklaces").performClick()
        composeTestRule.onNodeWithText("Next").performClick()

        verify { onConfirm(listOf("Necklaces")) }
        confirmVerified(onConfirm)
    }
}
