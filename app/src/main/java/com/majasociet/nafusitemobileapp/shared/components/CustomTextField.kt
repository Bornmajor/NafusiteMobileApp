package com.majasociet.nafusitemobileapp.shared.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.unit.dp

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    label: String,
    onTap:() -> Unit = {},
    value: String,
    onValueChange: (String) -> Unit,
    readOnly: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    placeholder: String,
    maxLines: Int = 1,
    error:String,
){

    val interactionSource = remember { MutableInteractionSource() }

    OutlinedTextField(
        modifier = modifier.clickable(
            enabled = readOnly, // tap action only when field is read-only
            interactionSource = interactionSource,
            indication = null
        ) { onTap() }.fillMaxWidth(),
        enabled = !readOnly,
        readOnly = readOnly,
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        leadingIcon = leadingIcon,
        onValueChange = onValueChange,
        value = value,
        maxLines = maxLines,
        singleLine = false,
        isError = error.isNotEmpty(),
        supportingText = {
            if(error.isNotEmpty()) {
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        },
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            disabledTextColor = MaterialTheme.colorScheme.onSurface,
            disabledBorderColor = MaterialTheme.colorScheme.outline,
            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
            errorBorderColor = MaterialTheme.colorScheme.error,
             errorLabelColor = MaterialTheme.colorScheme.error,
             errorLeadingIconColor = MaterialTheme.colorScheme.error
        )


    )




}