package com.majasociet.nafusitemobileapp.features.profile.data.repository

import android.net.Uri
import com.google.firebase.database.FirebaseDatabase
import com.majasociet.nafusitemobileapp.features.profile.data.models.User
import com.majasociet.nafusitemobileapp.features.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.tasks.await

class ProfileRepositoryImpl(
    private val database: FirebaseDatabase,
    private val cloudinaryRepository: CloudinaryRepository
) : ProfileRepository {
    override suspend fun getUserProfile(userId: String): Result<User> {
        return try {
            val snapshot = database.reference.child("users").child(userId).get().await()
            val user = snapshot.getValue(User::class.java)
            if (user != null) {
                user.id = snapshot.key ?: userId
                Result.success(user)
                Result.success(user)
            } else {
                Result.failure(Exception("User not found"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateUserProfile(user: User): Result<Unit> {
        return try {
            // 1. Guard against empty or missing IDs before updating
            if (user.id.isBlank()) {
                return Result.failure(Exception("Cannot update profile: User ID is blank"))
            }

            // 2. Target the specific user node path using the object's ID
            database.reference
                .child("users")
                .child(user.id)
                // 3. Set the new values. Firebase reads your @get:PropertyName annotations to match keys!
                .setValue(user)
                .await()

            Result.success(Unit)
        } catch (e: Exception) {
            // Catch any network timeouts, write-permission rules rejections, or database drops
            Result.failure(e)
        }
    }

    override suspend fun uploadProfileImage(imageUri: Uri): Result<String> {
        return cloudinaryRepository.uploadImage(imageUri)
    }

}