package com.majasociet.nafusitemobileapp.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Segmented stepper for onboarding screens
 */
@Composable
fun SegmentedStepper(
    totalSteps: Int,
    currentStep: Int,
    modifier: Modifier = Modifier,
    barHeight: Dp = 6.dp,
    spaceBetween: Dp = 8.dp,
    activeColor: Color = MaterialTheme.colorScheme.primary,
    inactiveColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f)
) {
    // Edge-case safeguard: Ensure we have at least 1 step to avoid division or layout errors
    val stepsCount = totalSteps.coerceAtLeast(1)
    // Coerce currentStep so it never exceeds the total steps bounds
    val completedSteps = currentStep.coerceIn(0, stepsCount)

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(spaceBetween)
    ) {
        for (i in 0 until stepsCount) {
            // Determine if this specific segment index should look completed/active
            val isStepCompleted = i < completedSteps

            Box(
                modifier = Modifier
                    .weight(1f) // Gives every block an identical equal width segment ratio
                    .height(barHeight)
                    .clip(CircleShape) // Smooth rounded pill shape edges for each segment block
                    .background(if (isStepCompleted) activeColor else inactiveColor)
            )
        }
    }
}