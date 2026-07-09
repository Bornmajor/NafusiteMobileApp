package com.majasociet.nafusitemobileapp.shared.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.majasociet.nafusitemobileapp.R
import com.majasociet.nafusitemobileapp.ui.theme.AppTheme

/**
 * Error message composable
 */
@Composable
fun ErrorMessage(
    modifier: Modifier = Modifier,
    message: String
){
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.large)
    ) {
        Icon(
            painter = painterResource(R.drawable.baseline_error_36),
            contentDescription = "Error",
            tint = MaterialTheme.colorScheme.error
        )
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.error
            )
        )
    }

}