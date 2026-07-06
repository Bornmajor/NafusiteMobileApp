// CloudinaryRepository.kt
package com.majasociet.nafusitemobileapp.features.profile.data.repository

import android.content.Context
import android.net.Uri
import com.majasociet.nafusitemobileapp.features.profile.data.remote.api.CloudinaryApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream

/**
 * Repository for uploading images to Cloudinary.
 * @param cloudinaryApiService The Cloudinary API service.
 * @param context The application context.
 */
class CloudinaryRepository(
    private val cloudinaryApiService: CloudinaryApiService,
    private val context: Context
) {
    companion object {
        // Create this preset in your Cloudinary dashboard (Settings > Upload > Upload Presets)
        private const val UPLOAD_PRESET = "ml_default"
    }

    suspend fun uploadImage(imageUri: Uri): Result<String> {
        return try {
            // 1. Convert content:// URI to a temp File
            val file = uriToFile(imageUri)
                ?: return Result.failure(Exception("Failed to read image file"))

            // 2. Build the multipart request body
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            val filePart = MultipartBody.Part.createFormData("file", file.name, requestFile)
            val presetPart = UPLOAD_PRESET.toRequestBody("text/plain".toMediaTypeOrNull())

            // 3. Call Cloudinary API
            val response = cloudinaryApiService.uploadImage(filePart, presetPart)

            if (response.isSuccessful) {
                val cloudinaryUrl = response.body()?.secureUrl
                    ?: return Result.failure(Exception("Empty response from Cloudinary"))
                Result.success(cloudinaryUrl)
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                Result.failure(Exception("Upload failed: $errorBody"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Convert a content:// URI to a File.
     * @param uri The URI to convert.
     */
    private fun uriToFile(uri: Uri): File? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val tempFile = File.createTempFile("upload_", ".jpg", context.cacheDir)
            FileOutputStream(tempFile).use { output ->
                inputStream.copyTo(output)
            }
            inputStream.close()
            tempFile
        } catch (e: Exception) {
            null
        }
    }
}