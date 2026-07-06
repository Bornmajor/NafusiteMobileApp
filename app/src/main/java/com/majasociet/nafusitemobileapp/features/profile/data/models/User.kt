package com.majasociet.nafusitemobileapp.features.profile.data.models

data class User(
    var id: String = "",
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val dateOfBirth: String = "",
    val preferences: List<String> = emptyList(),
    val profileImgUrl: String? = null
)