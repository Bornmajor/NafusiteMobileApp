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
import androidx.compose.runtime.Stable
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

@Stable
data class LoginScreenContentState(
    val email: String,
    val password: String,
    val emailError: String,
    val passwordError: String,
    val isLoading: Boolean,
    val isLogin: Boolean,
    val isFormValid: Boolean,
    val onEmailChange: (String) -> Unit,
    val onPasswordChange: (String) -> Unit,
    val onSubmit: () -> Unit,
    val onNavigateRegistration: () -> Unit
)

/**
 * Login screen
 * @param authViewModel - auth view model
 * @param profileViewModel - profile view model
 * @param navigateRegistrationScreen - navigate to registration screen
 */
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
                else -> Unit
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

    val contentState = remember(
        email,
        password,
        emailError,
        passwordError,
        authState.isLoading,
        authState.isLogin,
        isFormValid,
        navigateRegistrationScreen
    ) {
        LoginScreenContentState(
            email = email,
            password = password,
            emailError = emailError,
            passwordError = passwordError,
            isLoading = authState.isLoading,
            isLogin = authState.isLogin,
            isFormValid = isFormValid,
            onEmailChange = { email = it },
            onPasswordChange = { password = it },
            onSubmit = ::submitForm,
            onNavigateRegistration = navigateRegistrationScreen
        )
    }

    LoginScreenContent(state = contentState)
}

@Composable
fun LoginScreenContent(state: LoginScreenContentState) {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(AppTheme.spacing.medium),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.square_logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(150.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                text = "Verify your identity to login",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.padding(AppTheme.spacing.medium))

            CustomTextField(
                value = state.email,
                onValueChange = state.onEmailChange,
                label = "Email",
                placeholder = "Enter your email address",
                error = state.emailError
            )

            PasswordTextField(
                value = state.password,
                onValueChange = state.onPasswordChange,
                placeholder = "Enter your password",
                error = state.passwordError
            )

            Spacer(modifier = Modifier.padding(AppTheme.spacing.medium))

            AppButton(
                modifier = Modifier.fillMaxWidth(),
                isLoading = state.isLoading,
                disabled = !state.isFormValid && state.isLogin,
                text = "Login",
                onClick = state.onSubmit
            )

            Spacer(modifier = Modifier.padding(AppTheme.spacing.small))

            AppButton(
                modifier = Modifier.fillMaxWidth(),
                isLoading = state.isLoading,
                disabled = !state.isFormValid && state.isLogin,
                text = "Register instead",
                isOutlined = true,
                onClick = state.onNavigateRegistration
            )
        }
    }
}