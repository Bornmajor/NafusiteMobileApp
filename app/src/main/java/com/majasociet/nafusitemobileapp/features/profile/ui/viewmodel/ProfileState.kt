package com.majasociet.nafusitemobileapp.features.profile.ui.viewmodel

import com.majasociet.nafusitemobileapp.features.profile.domain.models.User

/**
 * UI State for the profile screen.
 */
data class ProfileState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String? = null
)
