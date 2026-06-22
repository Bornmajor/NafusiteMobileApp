package com.majasociet.nafusitemobileapp.features.auth.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.majasociet.nafusitemobileapp.features.auth.ui.components.AuthOnboardingScaffold
import com.majasociet.nafusitemobileapp.shared.components.PasswordTextField
import com.majasociet.nafusitemobileapp.shared.components.PasswordValidationBox
import com.majasociet.nafusitemobileapp.shared.utils.ValidationUtils
import com.majasociet.nafusitemobileapp.ui.theme.AppTheme

@Composable
fun PasswordSetupScreen(
    navigateHome: () -> Unit
){
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var passwordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }
    val isFormValid = ValidationUtils.isValidPassword(password)
            && password.isNotEmpty()
            && password == confirmPassword
            && confirmPassword.isNotEmpty()


    AuthOnboardingScaffold(
        currentStep = 3,
        bottomAction = navigateHome,
        canProceed = isFormValid,
        content = {
            Column(
                modifier = Modifier.padding(
                    top = AppTheme.spacing.medium,
                    bottom = AppTheme.spacing.medium
                )
            ) {
                Text(
                    "Complete your signup by creating a password",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier =  Modifier.padding(AppTheme.spacing.small))
                PasswordTextField(
                    value =  password,
                    onValueChange = {
                        password  = it
                    },
                    placeholder = "New password",
                    error = passwordError
                )
                PasswordTextField(
                    value =  confirmPassword,
                    onValueChange = {
                        confirmPassword  = it
                    },
                    placeholder = "Confirm new password",
                    error = confirmPasswordError
                )

                PasswordValidationBox(
                    isChecked = password.length >= 8,
                    label = "At least 8 characters"
                )
                PasswordValidationBox(
                    isChecked = password.any { it.isUpperCase() || it.isLowerCase() },
                    label = "At least uppercase and lowercase letter"
                )
                PasswordValidationBox(
                    isChecked = password.any { it.isDigit() },
                    label = "At least one number"
                )
                PasswordValidationBox(
                    isChecked = password.any { !it.isLetterOrDigit() },
                    label = "At least one special character"
                )
                PasswordValidationBox(
                    isChecked = password.isNotEmpty()
                            && confirmPassword.isNotEmpty()
                            && password == confirmPassword,
                    label = "Passwords match"
                )

            }
        }
    )

}
