package com.majasociet.nafusitemobileapp.shared.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    disabled: Boolean = false,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    isLoading: Boolean = false
){
    Button(
        onClick = onClick,
        enabled = !disabled && !isLoading,
        modifier = modifier,
        colors = colors
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = Color.Black,
                strokeWidth = 2.dp
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text)
    }

}