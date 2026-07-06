package com.majasociet.nafusitemobileapp.features.profile.domain.repository

import android.net.Uri
import com.majasociet.nafusitemobileapp.features.profile.data.models.User

/**
 *
 */
interface ProfileRepository {
    /**
     * Get user profile
     * @param userId - user id
     * @return - result of getting user profile
     */
    suspend fun getUserProfile(userId: String): Result<User>
    suspend fun updateUserProfile(user: User): Result<Unit>
    suspend fun uploadProfileImage(imageUri: Uri):Result<String>


}