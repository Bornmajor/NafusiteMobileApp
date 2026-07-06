package com.majasociet.nafusitemobileapp.features.auth.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.majasociet.nafusitemobileapp.features.auth.data.models.BasicRegistrationInfo
import com.majasociet.nafusitemobileapp.features.auth.ui.components.AuthOnboardingScaffold
import com.majasociet.nafusitemobileapp.features.auth.ui.viewmodel.AuthViewModel
import com.majasociet.nafusitemobileapp.features.auth.ui.viewmodel.UIEvent
import com.majasociet.nafusitemobileapp.shared.components.PasswordTextField
import com.majasociet.nafusitemobileapp.shared.components.PasswordValidationBox
import com.majasociet.nafusitemobileapp.shared.utils.ToastUtils
import com.majasociet.nafusitemobileapp.shared.utils.ValidationUtils
import com.majasociet.nafusitemobileapp.ui.theme.AppTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PasswordSetupScreen(
    basicInfo: BasicRegistrationInfo,
    selectedPreferences: List<String>,
    authViewModel: AuthViewModel,
    navigateLogin: () -> Unit
) {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var passwordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }
    val isFormValid = ValidationUtils.isValidPassword(password)
            && password.isNotEmpty()
            && password == confirmPassword
            && confirmPassword.isNotEmpty()

    val authState = authViewModel.authState.collectAsStateWithLifecycle().value


    fun submitPassword(){
        authViewModel.registerUser(
            email = basicInfo.email,
            password = password,
            firstName = basicInfo.firstName,
            lastName = basicInfo.lastName,
            dateOfBirth = basicInfo.dateOfBirth,
            selectedPreferences = selectedPreferences,
        )
    }
    val context = LocalContext.current

    LaunchedEffect(
        authViewModel.uiEvent
    ) {
        authViewModel.uiEvent.collectLatest { event ->
            when(event) {
                is UIEvent.SubmitRegistrationSuccess ->{
                    navigateLogin()
                }
                is UIEvent.SubmitRegistrationFailure ->{
                    ToastUtils.show(context,event.message)
                }
                else -> {}
            }
        }

    }


    AuthOnboardingScaffold(
        currentStep = 3,
        bottomAction = { submitPassword() },
        isButtonLoading = authState.isLoading,
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
                Spacer(modifier = Modifier.padding(AppTheme.spacing.small))
                PasswordTextField(
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    placeholder = "New password",
                    error = passwordError
                )
                PasswordTextField(
                    value = confirmPassword,
                    onValueChange = {
                        confirmPassword = it
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
