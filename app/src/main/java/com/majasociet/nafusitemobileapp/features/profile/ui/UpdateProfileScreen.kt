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

@Composable
fun UpdateProfileScreen(
    profileViewModel: ProfileViewModel,
    navigateBack: () -> Unit
){
    val profileState = profileViewModel.profileState.collectAsStateWithLifecycle().value
    var firstName by remember { mutableStateOf(profileState.user?.firstName ?: "") }
    var lastName by remember { mutableStateOf(profileState.user?.lastName ?: "") }

    val isFormValid = firstName.isNotEmpty() && lastName.isNotEmpty()

    var profileUrlImg by remember { mutableStateOf(profileState.user?.profileImgUrl) }
    //This is image picked by user from gallery and one used to update profile image
    var selectedImageUri by remember { mutableStateOf<String?>(null) }
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if(uri != null){
                selectedImageUri = uri.toString()
            }

        }
    )
    val context = LocalContext.current

    LaunchedEffect(profileViewModel.profileEvent) {
        profileViewModel.profileEvent.collectLatest {
            event ->
            when(event) {
                is ProfileEvent.SuccessProfileUpdate ->{
                    ToastUtils.show(context, "Profile updated successfully")
                }
                is ProfileEvent.FailureProfileUpdate ->{
                    ToastUtils.show(context, event.message)
                }

                else -> {}
            }
        }


    }


    ProfileScaffold(
        bottomAction = {
            AppButton(
                text = "Save",
                disabled = !isFormValid,
                isLoading = !isFormValid || profileState.isLoading,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                   val user = User(
                       id = profileState.user?.id ?: "",  // preserve existing ID
                       email = profileState.user?.email ?: "",
                       firstName = firstName,
                       lastName = lastName,
                       dateOfBirth = profileState.user?.dateOfBirth ?: "",
                       preferences = profileState.user?.preferences ?: emptyList(),
                       profileImgUrl = profileState.user?.profileImgUrl
                   )
                    val imageUri = selectedImageUri?.let { Uri.parse(it) }
                    profileViewModel.updateProfileWithImage(user = user, imageUri = imageUri)

                }
            )
        },
        content = {
            Column(
               modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.medium)
            ){
                ProfileAvatar(
                    imageUrl = profileUrlImg ?: "",
                    imagePreviewUrl = selectedImageUri,
                    onChangeAvatar = {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    }
                )
                Spacer(
                    modifier = Modifier.padding(AppTheme.spacing.medium)
                )
                CustomTextField(
                    value = firstName,
                    onValueChange = {
                        firstName = it
                    },
                    label = "First Name",
                    placeholder = "Enter your first name",
                    error = ""
                )
                CustomTextField(
                    value = lastName,
                    onValueChange = {
                        lastName = it
                    },
                    label = "Last Name",
                    placeholder = "Enter your last name",
                    error = ""
                )
            }
        }
    )
}