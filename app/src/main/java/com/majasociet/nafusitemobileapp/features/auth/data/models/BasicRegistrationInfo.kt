package com.majasociet.nafusitemobileapp.features.auth.data.models

import kotlinx.serialization.Serializable

@Serializable
data class BasicRegistrationInfo(
    val firstName: String,
    val lastName: String,
    val email: String,
    val dateOfBirth: String,
)
