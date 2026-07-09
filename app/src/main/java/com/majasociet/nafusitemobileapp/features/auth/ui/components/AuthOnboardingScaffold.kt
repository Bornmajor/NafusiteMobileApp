package com.majasociet.nafusitemobileapp.features.auth.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.majasociet.nafusitemobileapp.R
import com.majasociet.nafusitemobileapp.shared.components.AppButton
import com.majasociet.nafusitemobileapp.shared.components.SegmentedStepper
import com.majasociet.nafusitemobileapp.ui.theme.AppTheme

/**
 * Auth onboarding scaffold
 * @param bottomAction - bottom action button
 * @param content - content of the scaffold
 * @param canProceed - whether the bottom action button should be enabled
 */
@Composable
fun AuthOnboardingScaffold(
    modifier: Modifier = Modifier,
    bottomAction: () -> Unit,
    content: @Composable () -> Unit,
    canProceed: Boolean = false,
    isButtonLoading: Boolean = false,
    currentStep: Int,

) {
    Scaffold(
        modifier = modifier.fillMaxSize(),

        bottomBar = {
            Box(
                modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars).padding(AppTheme.spacing.medium)
            ){
                AppButton(
                    isLoading = isButtonLoading,
                    modifier = Modifier.fillMaxWidth(),
                    text = "Next",
                    onClick = bottomAction,
                    disabled = !canProceed
                )
            }

        }
    ){ innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding)
                .padding(AppTheme.spacing.medium)
        ) {
            SegmentedStepper(
                totalSteps = 3,
                currentStep = currentStep
            )
            content()

        }
    }
}