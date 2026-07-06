package com.majasociet.nafusitemobileapp.features.profile.ui.viewmodel

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.majasociet.nafusitemobileapp.features.profile.data.models.ProfileState
import com.majasociet.nafusitemobileapp.features.profile.data.models.User
import com.majasociet.nafusitemobileapp.features.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Profile view model - handles profile logic
 * @param profileRepository - profile repository
 */
class ProfileViewModel(
    private val profileRepository: ProfileRepository
): ViewModel() {


    // immutable state flow for profile state only access via model
    private val _profileState = MutableStateFlow<ProfileState>(ProfileState(
        error = null
    ))

    // expose immutable state flow to UI
    val profileState = _profileState.asStateFlow()

    //Events
    private val _profileEvent = MutableSharedFlow<ProfileEvent>()
    // expose immutable event flow to UI
    val profileEvent = _profileEvent.asSharedFlow()

    /**
     * Get user profile
     * @param userId - user id
     */
    fun getUserProfile(userId: String){
        _profileState.update {
            it.copy(
                isLoading = true
            )
        }

        viewModelScope.launch {
            val result = profileRepository.getUserProfile(userId)

            result.onSuccess { fetchedUser ->
                _profileState.update {
                    it.copy(
                        isLoading = false,
                        user = fetchedUser
                    )
                }
            }.onFailure { exception ->
                _profileState.update {
                    it.copy(
                        isLoading = false,
                        error = exception.localizedMessage?: "An error occurred"
                    )
                }
                _profileEvent.emit(ProfileEvent.FailureFetchProfile(exception.localizedMessage ?: "An error occurred"))
            }
        }
    }

    /**
     * Update user profile
     * @param user - user profile
     */
    fun updateProfileWithImage(user: User, imageUri: Uri?) {
        _profileState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                // 1. If there's a new image, upload it first
                val finalUser = if (imageUri != null) {
                    val uploadResult = profileRepository.uploadProfileImage(imageUri)

                    uploadResult.getOrElse { error ->
                        _profileState.update { it.copy(isLoading = false, error = error.message) }
                        _profileEvent.emit(ProfileEvent.FailureProfileUpdate(error.message ?: "Image upload failed"))
                        return@launch
                    }.let { cloudinaryUrl ->
                        user.copy(profileImgUrl = cloudinaryUrl)
                    }
                } else {
                    user
                }

                // 2. Now update Firebase with the user (including new image URL if uploaded)
                val updateResult = profileRepository.updateUserProfile(finalUser)

                updateResult.onSuccess {
                    _profileState.update { it.copy(isLoading = false, user = finalUser) }
                    _profileEvent.emit(ProfileEvent.SuccessProfileUpdate)
                }.onFailure { exception ->
                    _profileState.update { it.copy(isLoading = false, error = exception.message) }
                    _profileEvent.emit(ProfileEvent.FailureProfileUpdate(exception.message ?: "Update failed"))
                }

            } catch (e: Exception) {
                _profileState.update { it.copy(isLoading = false, error = e.message) }
                _profileEvent.emit(ProfileEvent.FailureProfileUpdate(e.message ?: "An error occurred"))
            }
        }
    }


}

sealed interface ProfileEvent{
    object SuccessProfileUpdate: ProfileEvent
    data class FailureProfileUpdate(val message: String): ProfileEvent
    data class FailureFetchProfile(val message: String): ProfileEvent

}