package com.majasociet.nafusitemobileapp.shared.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PasswordValidationBox(
    label: String,
    isChecked: Boolean,
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(
            vertical = 6.dp
        )
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = label,
            modifier = Modifier.weight(1f)
        )
    }
}