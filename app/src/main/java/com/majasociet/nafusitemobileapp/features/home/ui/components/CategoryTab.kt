package com.majasociet.nafusitemobileapp.features.home.ui.components
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AssistChip
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.majasociet.nafusitemobileapp.R

@Composable
fun CategoryTab(
    name: String,
    onClick: () -> Unit
){
    AssistChip(
        onClick = onClick,
        label = {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyMedium
            )
        },
    )
}
