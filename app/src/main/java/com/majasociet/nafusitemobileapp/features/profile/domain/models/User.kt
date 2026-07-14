package com.majasociet.nafusitemobileapp.features.profile.domain.models

/**
 * Domain representation of a User profile.
 */
data class User(
    var id: String = "",
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val dateOfBirth: String = "",
    val preferences: List<String> = emptyList(),
    val profileImgUrl: String? = null
)
