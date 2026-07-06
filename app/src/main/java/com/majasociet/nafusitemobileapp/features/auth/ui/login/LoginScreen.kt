package com.majasociet.nafusitemobileapp.features.auth.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.majasociet.nafusitemobileapp.R
import com.majasociet.nafusitemobileapp.features.auth.ui.viewmodel.AuthViewModel
import com.majasociet.nafusitemobileapp.features.auth.ui.viewmodel.UIEvent
import com.majasociet.nafusitemobileapp.features.profile.ui.viewmodel.ProfileViewModel
import com.majasociet.nafusitemobileapp.shared.components.AppButton
import com.majasociet.nafusitemobileapp.shared.components.CustomTextField
import com.majasociet.nafusitemobileapp.shared.components.PasswordTextField
import com.majasociet.nafusitemobileapp.shared.utils.ToastUtils
import com.majasociet.nafusitemobileapp.shared.utils.ValidationUtils
import com.majasociet.nafusitemobileapp.ui.theme.AppTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    profileViewModel: ProfileViewModel,
    navigateRegistrationScreen: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }

    val isFormValid = email.isNotEmpty() && password.isNotEmpty()
    val scrollState = rememberScrollState()
    val authState = authViewModel.authState.collectAsStateWithLifecycle().value
    val context = LocalContext.current

    LaunchedEffect(authViewModel.uiEvent) {
        authViewModel.uiEvent.collectLatest { event ->
            when (event) {
                is UIEvent.SubmitLoginFailure -> {
                    ToastUtils.show(context, event.message)
                    passwordError = "Authentication failed"
                }
                is UIEvent.SubmitLoginSuccess -> {
                    ToastUtils.show(context, "Login successful")
                    profileViewModel.getUserProfile(event.userId)
                }
                else -> {}
            }
        }
    }

    fun submitForm() {
        emailError = ""
        passwordError = ""
        val isEmailValid = ValidationUtils.isValidEmail(email)

        if (!isEmailValid) {
            emailError = "Invalid email address"
            return
        }
        authViewModel.loginUser(email, password)
    }

    // 1. The Outer Box fills the screen space
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(AppTheme.spacing.medium),
        contentAlignment = Alignment.Center // Keeps the column centered when keyboard is closed
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState), // Handles the keyboard resize scaling perfectly
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.square_logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(150.dp),
                contentScale = ContentScale.Fit
            )

            Text("Verify your identity to login", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.padding(AppTheme.spacing.medium))

            CustomTextField(
                value = email,
                onValueChange = { email = it },
                label = "Email",
                placeholder = "Enter your email address",
                error = emailError
            )
            PasswordTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = "Enter your password",
                error = passwordError
            )
            Spacer(modifier = Modifier.padding(AppTheme.spacing.medium))

            AppButton(
                modifier = Modifier.fillMaxWidth(),
                isLoading = authState.isLoading,
                disabled = !isFormValid && authState.isLogin,
                text = "Login",
                onClick = { submitForm() }
            )
            Spacer(modifier = Modifier.padding(AppTheme.spacing.small))

            AppButton(
                modifier = Modifier.fillMaxWidth(),
                isLoading = authState.isLoading,
                disabled = !isFormValid && authState.isLogin,
                text = "Register instead",
                isOutlined = true,
                onClick = { navigateRegistrationScreen() }
            )
        }
    }
}