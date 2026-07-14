package com.majasociet.nafusitemobileapp.features.profile.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.majasociet.nafusitemobileapp.features.profile.data.models.User
import com.majasociet.nafusitemobileapp.features.profile.ui.components.ProfileAvatar
import com.majasociet.nafusitemobileapp.features.profile.ui.components.ProfileScaffold
import com.majasociet.nafusitemobileapp.features.profile.ui.viewmodel.ProfileEvent
import com.majasociet.nafusitemobileapp.features.profile.ui.viewmodel.ProfileViewModel
import com.majasociet.nafusitemobileapp.shared.components.AppButton
import com.majasociet.nafusitemobileapp.shared.components.CustomTextField
import com.majasociet.nafusitemobileapp.shared.utils.ToastUtils
import com.majasociet.nafusitemobileapp.ui.theme.AppTheme
import kotlinx.coroutines.flow.collectLatest

@Immutable
data class UpdateProfileContentState(
    val profileImageUrl: String,
    val imagePreviewUrl: String?,
    val firstName: String,
    val lastName: String,
    val isFormValid: Boolean,
    val isLoading: Boolean,
    val onChangeAvatar: () -> Unit,
    val onFirstNameChange: (String) -> Unit,
    val onLastNameChange: (String) -> Unit,
    val onSave: () -> Unit
)

/**
 * Stateful wrapper: reads state from ViewModel and maps to UI state.
 * @param profileViewModel - profile view model
 * @param navigateBack - navigate back
 * @param navigateToSearch - navigate to search
 */
@Composable
fun UpdateProfileScreen(
    profileViewModel: ProfileViewModel,
    navigateBack: () -> Unit,
    navigateToSearch: () -> Unit
) {
    val profileState = profileViewModel.profileState.collectAsStateWithLifecycle().value

    var firstName by remember { mutableStateOf(profileState.user?.firstName ?: "") }
    var lastName by remember { mutableStateOf(profileState.user?.lastName ?: "") }
    var selectedImageUri by remember { mutableStateOf<String?>(null) }

    val isFormValid = firstName.isNotEmpty() && lastName.isNotEmpty()

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                selectedImageUri = uri.toString()
            }
        }
    )

    val context = LocalContext.current

    LaunchedEffect(profileViewModel.profileEvent) {
        profileViewModel.profileEvent.collectLatest { event ->
            when (event) {
                is ProfileEvent.SuccessProfileUpdate -> {
                    ToastUtils.show(context, "Profile updated successfully")
                }
                is ProfileEvent.FailureProfileUpdate -> {
                    ToastUtils.show(context, event.message)
                }
                else -> Unit
            }
        }
    }

    fun submit() {
        val user = User(
            id = profileState.user?.id ?: "",
            email = profileState.user?.email ?: "",
            firstName = firstName,
            lastName = lastName,
            dateOfBirth = profileState.user?.dateOfBirth ?: "",
            preferences = profileState.user?.preferences ?: emptyList(),
            profileImgUrl = profileState.user?.profileImgUrl
        )
        val imageUri = selectedImageUri?.let(Uri::parse)
        profileViewModel.updateProfileWithImage(user = user, imageUri = imageUri)
    }

    val contentState = UpdateProfileContentState(
        profileImageUrl = profileState.user?.profileImgUrl.orEmpty(),
        imagePreviewUrl = selectedImageUri,
        firstName = firstName,
        lastName = lastName,
        isFormValid = isFormValid,
        isLoading = profileState.isLoading,
        onChangeAvatar = {
            photoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        },
        onFirstNameChange = { firstName = it },
        onLastNameChange = { lastName = it },
        onSave = ::submit
    )

    ProfileScaffold(
        navigateBack = navigateBack,
        navigateToSearch = navigateToSearch,
        content = {
            UpdateProfileContent(state = contentState)
        },
        bottomAction = {
            AppButton(
                text = "Save",
                disabled = !contentState.isFormValid,
                isLoading = !contentState.isFormValid || contentState.isLoading,
                modifier = Modifier.fillMaxWidth(),
                onClick = contentState.onSave
            )
        }
    )
}

/**
 * Stateless layout: only depends on provided state.
 * @param state - update profile content state
 */
@Composable
fun UpdateProfileContent(
    state: UpdateProfileContentState
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.medium)
    ) {
        ProfileAvatar(
            imageUrl = state.profileImageUrl,
            imagePreviewUrl = state.imagePreviewUrl,
            onChangeAvatar = state.onChangeAvatar
        )

        Spacer(modifier = Modifier.padding(AppTheme.spacing.medium))

        CustomTextField(
            value = state.firstName,
            onValueChange = state.onFirstNameChange,
            label = "First Name",
            placeholder = "Enter your first name",
            error = ""
        )

        CustomTextField(
            value = state.lastName,
            onValueChange = state.onLastNameChange,
            label = "Last Name",
            placeholder = "Enter your last name",
            error = ""
        )
    }
}