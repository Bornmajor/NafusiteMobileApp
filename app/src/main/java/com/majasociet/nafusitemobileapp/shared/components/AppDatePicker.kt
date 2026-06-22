package com.majasociet.nafusitemobileapp.shared.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.majasociet.nafusitemobileapp.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDatePicker(
    label: String,
    selectedValue: String,
    onDateSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    errorMessage: String? = null
) {
    var showDialog by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState()

    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                showDialog = true
            }
    ) {
        OutlinedTextField(
            value = selectedValue,
            onValueChange = {},
            label = { Text(label) },
            placeholder = { Text("dd/MM/yyyy") },
            readOnly = true,
            enabled = false,
            shape = RoundedCornerShape(12.dp),
            trailingIcon = {
                Icon(
                    painter = painterResource(R.drawable.date_range_24px),
                    contentDescription = "Select Date"
                )
            },
            isError = !errorMessage.isNullOrBlank(),
            supportingText = {
                if (!errorMessage.isNullOrBlank()) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = MaterialTheme.colorScheme.onSurface,

                // 👇 FIX: Dynamically assign the border color based on the error state
                disabledBorderColor = if (!errorMessage.isNullOrBlank()) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.outline
                },

                // 👇 FIX: Also turn the label red when an error occurs
                disabledLabelColor = if (!errorMessage.isNullOrBlank()) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                },

                // 👇 FIX: Turn the trailing calendar icon red when an error occurs
                disabledTrailingIconColor = if (!errorMessage.isNullOrBlank()) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                },

                disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledContainerColor = MaterialTheme.colorScheme.surface
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }

    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = {
                showDialog = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val formattedDate = SimpleDateFormat(
                                "dd/MM/yyyy",
                                Locale.getDefault()
                            ).format(Date(millis))

                            onDateSelected(formattedDate)
                        }

                        showDialog = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(
                state = datePickerState
            )
        }
    }
}