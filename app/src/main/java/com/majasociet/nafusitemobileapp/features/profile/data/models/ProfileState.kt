package com.majasociet.nafusitemobileapp.features.profile.data.models

data class ProfileState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String?

)
