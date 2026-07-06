package com.majasociet.nafusitemobileapp.features.profile.data.models

import com.google.gson.annotations.SerializedName

data class CloudinaryResponse(
    @SerializedName("secure_url")
    val secureUrl: String,
    @SerializedName("public_id")
    val publicId: String? = null,
)
