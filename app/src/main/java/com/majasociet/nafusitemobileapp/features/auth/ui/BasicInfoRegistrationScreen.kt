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
import com.majasociet.nafusitemobileapp.features.auth.data.models.BasicRegistrationInfo
import com.majasociet.nafusitemobileapp.features.auth.ui.components.AuthOnboardingScaffold
import com.majasociet.nafusitemobileapp.shared.components.AppDatePicker
import com.majasociet.nafusitemobileapp.shared.components.CustomTextField
import com.majasociet.nafusitemobileapp.shared.utils.ValidationUtils
import com.majasociet.nafusitemobileapp.ui.theme.AppTheme

/**
 * User enters basic Info  during registration journey.
 * @param onNextClick - on next click
 */
@Composable
fun BasicInfoRegistrationScreen(
    onNextClick:(BasicRegistrationInfo) -> Unit,
) {
    //TODO -refactor composable state to custom state class holder for composable with more than 4 states.
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }

    val isFormValid = firstName.isNotEmpty() &&
            lastName.isNotEmpty() &&
            email.isNotEmpty() &&
            dateOfBirth.isNotEmpty()

    var emailError by remember { mutableStateOf("") }
    var dateError by remember { mutableStateOf("") }
    var firstNameError by remember { mutableStateOf("") }
    var lastNameError by remember { mutableStateOf("") }

    fun submitForm() {
        // 1. Reset error states before evaluating the inputs
        emailError = ""
        dateError = ""

        val isFirstNameSingleWord = ValidationUtils.isSingleWord(firstName)
        val isLastNameSingleWord = ValidationUtils.isSingleWord(lastName)
        val isEmailValid = ValidationUtils.isValidEmail(email)
        val isAgeValid = ValidationUtils.isOlderThan18(dateOfBirth)

        if (!isFirstNameSingleWord) {
            firstNameError = "First name must be a single word"
        }
        if (!isLastNameSingleWord) {
            lastNameError = "Last name must be a single word"
        }
        if (!isEmailValid) {
            emailError = "Invalid email address"
        }
        if (!isAgeValid) {
            dateError = "You must be at least 18 years old"
        }

        // 3. Only proceed forward if both criteria are cleanly met
        if (isEmailValid && isAgeValid) {
           val basicInfo = BasicRegistrationInfo(
                firstName = firstName,
                lastName = lastName,
                email = email,
                dateOfBirth = dateOfBirth
            )
            onNextClick(basicInfo)
        }
    }

    AuthOnboardingScaffold(
        currentStep = 1,
        bottomAction = { submitForm() },
        canProceed = isFormValid,
        content = {
            Column(
                modifier = Modifier.padding(
                    top = AppTheme.spacing.medium,
                    bottom = AppTheme.spacing.medium
                )
            ) {
                Text(
                    "We need to know a little more about you please fill in the fields below",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier =  Modifier.padding(AppTheme.spacing.small))
                CustomTextField(
                    label = "First Name",
                    value = firstName,
                    onValueChange = { firstName = it },
                    placeholder = "Enter your first name",
                    error = ""
                )
                CustomTextField(
                    label = "Last Name",
                    value = lastName,
                    onValueChange = { lastName = it },
                    placeholder = "Enter your last name",
                    error = ""
                )
                CustomTextField(
                    label = "Email",
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = "" // 4. Clear the validation error line as the user types
                    },
                    placeholder = "Enter your email address",
                    error = emailError
                )

                AppDatePicker(
                    label = "Date of Birth",
                    selectedValue = dateOfBirth,
                    onDateSelected = { selectedDate ->
                        dateOfBirth = selectedDate
                        dateError = "" // 5. Clear the age validation error line immediately when a new date is picked
                    },
                    errorMessage = dateError.ifEmpty { null }
                )
            }
        }
    )
}