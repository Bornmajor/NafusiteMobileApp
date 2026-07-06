package com.majasociet.nafusitemobileapp.shared.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


/**
 * App button
 * @param text - button text
 * @param onClick - button click action
 * @param modifier - button modifier
 * @param disabled - whether the button is disabled
 * @param isOutlined - whether the button is outlined or filled
 * @param isLoading - whether the button is loading
 */
@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    disabled: Boolean = false,
    isOutlined: Boolean = false,
    isLoading: Boolean = false
) {
    // 1. Automatically calculate the right colors based on the flag
    val buttonColors = if (isOutlined) {
        ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.primary,
            containerColor = Color.Transparent
        )
    } else {
        ButtonDefaults.buttonColors() // Standard filled look
    }

    // 2. Automatically calculate if a border stroke is required
    val buttonBorder = if (isOutlined) {
        BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    } else {
        null
    }

    Button(
        onClick = onClick,
        enabled = !disabled && !isLoading,
        modifier = modifier,
        colors = buttonColors,
        border = buttonBorder
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                // Make loader color match the text style cleanly
                color = if (isOutlined) MaterialTheme.colorScheme.primary else Color.White,
                strokeWidth = 2.dp
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
        Text(text = text)
    }
}