package com.majasociet.nafusitemobileapp.features.auth.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.majasociet.nafusitemobileapp.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val auth: FirebaseAuth,
    private val database: FirebaseDatabase
) : AuthRepository {
    override suspend fun registerNewUser(
        email: String,
        firstName: String,
        lastName: String,
        dateOfBirth: String,
        password: String,
        preferences: List<String>
    ): Result<Unit> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(
                email.trim(),
                password
            ).await()
            val firebaseUuid = authResult.user?.uid
                ?: return Result.failure(
                    Exception("User registration failed")
                )

            val newUser = mapOf(
                "userId" to firebaseUuid,
                "firstName" to firstName.trim(),
                "lastName" to lastName.trim(),
                "email" to email.trim(),
                "dateOfBirth" to dateOfBirth,
                "createdAt" to System.currentTimeMillis(),
                "preferences" to preferences
            )

            database.reference
                .child("users")
                .child(firebaseUuid)
                .setValue(newUser)
                .await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun loginUser(
        email: String,
        password: String
    ): Result<String> {
        return try {
            val authResult = auth.signInWithEmailAndPassword(
                email.trim(),
                password
            ).await()

            val firebaseUuid = authResult.user?.uid
                ?: return Result.failure(Exception("User instance not found after login"))

            // Check if the user's profile exists in Realtime Database
            val snapshot = database.reference
                .child("users")
                .child(firebaseUuid)
                .get()
                .await()

            if (!snapshot.exists()) {
                // User exists in Authentication but not in Database
                auth.signOut()

                return Result.failure(
                    Exception("User profile not found. Please contact support.")
                )
            }

            Result.success(firebaseUuid)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
