package com.majasociet.nafusitemobileapp.features.auth.domain.models

import kotlinx.serialization.Serializable

/**
 * Domain representation of registration info used in Safe Args Navigation.
 */
@Serializable
data class BasicRegistrationInfo(
    val firstName: String,
    val lastName: String,
    val email: String,
    val dateOfBirth: String,
)
