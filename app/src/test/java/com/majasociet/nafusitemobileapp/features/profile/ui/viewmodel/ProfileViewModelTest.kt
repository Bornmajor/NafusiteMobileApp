package com.majasociet.nafusitemobileapp.features.profile.ui.viewmodel

import android.net.Uri
import app.cash.turbine.test
import com.majasociet.nafusitemobileapp.features.profile.data.models.User
import com.majasociet.nafusitemobileapp.features.profile.domain.repository.ProfileRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {

    private lateinit var viewModel: ProfileViewModel
    private val profileRepository: ProfileRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ProfileViewModel(profileRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getUserProfile success updates state with user`() = runTest {
        // Given
        val userId = "user123"
        val user = User(id = userId, firstName = "John", lastName = "Doe")
        coEvery { profileRepository.getUserProfile(userId) } returns Result.success(user)

        // When
        viewModel.getUserProfile(userId)
        advanceUntilIdle()

        // Then
        assertEquals(user, viewModel.profileState.value.user)
        assertEquals(false, viewModel.profileState.value.isLoading)
        assertEquals(null, viewModel.profileState.value.error)
    }

    @Test
    fun `getUserProfile failure updates state with error and emits event`() = runTest {
        // Given
        val userId = "user123"
        val errorMessage = "User not found"
        coEvery { profileRepository.getUserProfile(userId) } returns Result.failure(Exception(errorMessage))

        // When & Then
        viewModel.profileEvent.test {
            viewModel.getUserProfile(userId)
            val event = awaitItem()
            assert(event is ProfileEvent.FailureFetchProfile)
            assertEquals(errorMessage, (event as ProfileEvent.FailureFetchProfile).message)
        }

        assertEquals(errorMessage, viewModel.profileState.value.error)
        assertEquals(false, viewModel.profileState.value.isLoading)
    }

    @Test
    fun `updateProfileWithImage without image success`() = runTest {
        // Given
        val user = User(id = "user123", firstName = "John")
        coEvery { profileRepository.updateUserProfile(user) } returns Result.success(Unit)

        // When & Then
        viewModel.profileEvent.test {
            viewModel.updateProfileWithImage(user, null)
            val event = awaitItem()
            assert(event is ProfileEvent.SuccessProfileUpdate)
        }

        assertEquals(user, viewModel.profileState.value.user)
        assertEquals(false, viewModel.profileState.value.isLoading)
    }

    @Test
    fun `updateProfileWithImage with image success`() = runTest {
        // Given
        val user = User(id = "user123", firstName = "John")
        val imageUri: Uri = mockk()
        val cloudinaryUrl = "https://cloudinary.com/image.jpg"
        val updatedUser = user.copy(profileImgUrl = cloudinaryUrl)

        coEvery { profileRepository.uploadProfileImage(imageUri) } returns Result.success(cloudinaryUrl)
        coEvery { profileRepository.updateUserProfile(updatedUser) } returns Result.success(Unit)

        // When & Then
        viewModel.profileEvent.test {
            viewModel.updateProfileWithImage(user, imageUri)
            val event = awaitItem()
            assert(event is ProfileEvent.SuccessProfileUpdate)
        }

        assertEquals(updatedUser, viewModel.profileState.value.user)
        assertEquals(false, viewModel.profileState.value.isLoading)
    }

    @Test
    fun `updateProfileWithImage image upload failure emits error event`() = runTest {
        // Given
        val user = User(id = "user123", firstName = "John")
        val imageUri: Uri = mockk()
        val errorMessage = "Upload failed"

        coEvery { profileRepository.uploadProfileImage(imageUri) } returns Result.failure(Exception(errorMessage))

        // When & Then
        viewModel.profileEvent.test {
            viewModel.updateProfileWithImage(user, imageUri)
            val event = awaitItem()
            assert(event is ProfileEvent.FailureProfileUpdate)
            assertEquals(errorMessage, (event as ProfileEvent.FailureProfileUpdate).message)
        }

        assertEquals(errorMessage, viewModel.profileState.value.error)
        assertEquals(false, viewModel.profileState.value.isLoading)
    }
}
