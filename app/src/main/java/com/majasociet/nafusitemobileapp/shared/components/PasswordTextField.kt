package com.majasociet.nafusitemobileapp.shared.components


import android.graphics.drawable.Icon
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.majasociet.nafusitemobileapp.R

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    readOnly: Boolean = false,
    placeholder: String,
    error:String,

){

    val interactionSource = remember { MutableInteractionSource() }
    var isPasswordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        enabled = !readOnly,
        readOnly = readOnly,
        label = { Text("Password") },
        placeholder = { Text(placeholder) },
        shape = RoundedCornerShape(12.dp),
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(
                onClick = {
                   isPasswordVisible = !isPasswordVisible
                }
            ) {
                if(isPasswordVisible){
                    Icon(
                        painter = painterResource(
                            R.drawable.visibility_off_24px
                        ),
                        contentDescription = null
                    )

                }else{
                    Icon(
                        painter = painterResource(
                            R.drawable.visibility_24px
                        ),
                        contentDescription = null
                    )
                }


            }
        },
        onValueChange = onValueChange,
        value = value,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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
        colors = OutlinedTextFieldDefaults.colors(
            disabledTextColor = MaterialTheme.colorScheme.onSurface,
            disabledBorderColor = MaterialTheme.colorScheme.outline,
            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledContainerColor = MaterialTheme.colorScheme.surface
        )
    )



}