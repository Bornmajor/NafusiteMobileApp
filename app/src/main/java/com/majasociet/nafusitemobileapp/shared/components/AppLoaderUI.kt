package com.majasociet.nafusitemobileapp.shared.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AppLoaderUI(
    modifier: Modifier = Modifier,
    message: String = "Loading content",
){
    Column(
        modifier = modifier
    ) {
        CircularProgressIndicator(
         color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium
        )
    }


}