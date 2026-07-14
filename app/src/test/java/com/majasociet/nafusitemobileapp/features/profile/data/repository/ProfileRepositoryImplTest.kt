package com.majasociet.nafusitemobileapp.features.profile.data.repository

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.majasociet.nafusitemobileapp.features.profile.data.models.User
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileRepositoryImplTest {

    private lateinit var repository: ProfileRepositoryImpl
    private val database: FirebaseDatabase = mockk()
    private val databaseReference: DatabaseReference = mockk()
    private val cloudinaryRepository: CloudinaryRepository = mockk()

    @Before
    fun setUp() {
        mockkStatic("kotlinx.coroutines.tasks.TasksKt")
        every { database.reference } returns databaseReference
        repository = ProfileRepositoryImpl(database, cloudinaryRepository)
    }

    @After
    fun tearDown() {
        unmockkStatic("kotlinx.coroutines.tasks.TasksKt")
    }

    @Test
    fun `getUserProfile success returns user`() = runTest {
        // Given
        val userId = "uid123"
        val user = User(id = userId, firstName = "John")
        val task: Task<DataSnapshot> = mockk()
        val snapshot: DataSnapshot = mockk()
        val userChild: DatabaseReference = mockk()

        every { databaseReference.child("users") } returns databaseReference
        every { databaseReference.child(userId) } returns userChild
        every { userChild.get() } returns task
        coEvery { task.await() } returns snapshot
        every { snapshot.getValue(User::class.java) } returns user
        every { snapshot.key } returns userId

        // When
        val result = repository.getUserProfile(userId)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(user, result.getOrNull())
    }

    @Test
    fun `getUserProfile failure when user not found`() = runTest {
        // Given
        val userId = "uid123"
        val task: Task<DataSnapshot> = mockk()
        val snapshot: DataSnapshot = mockk()

        every { databaseReference.child("users") } returns databaseReference
        every { databaseReference.child(userId) } returns databaseReference
        every { databaseReference.get() } returns task
        coEvery { task.await() } returns snapshot
        every { snapshot.getValue(User::class.java) } returns null

        // When
        val result = repository.getUserProfile(userId)

        // Then
        assertTrue(result.isFailure)
        assertEquals("User not found", result.exceptionOrNull()?.message)
    }

    @Test
    fun `updateUserProfile success`() = runTest {
        // Given
        val user = User(id = "uid123", firstName = "John")
        val task: Task<Void> = mockk()
        val userChild: DatabaseReference = mockk()

        every { databaseReference.child("users") } returns databaseReference
        every { databaseReference.child(user.id) } returns userChild
        every { userChild.setValue(user) } returns task
        coEvery { task.await() } returns mockk()

        // When
        val result = repository.updateUserProfile(user)

        // Then
        assertTrue(result.isSuccess)
    }

    @Test
    fun `updateUserProfile failure when id is blank`() = runTest {
        // Given
        val user = User(id = "", firstName = "John")

        // When
        val result = repository.updateUserProfile(user)

        // Then
        assertTrue(result.isFailure)
        assertEquals("Cannot update profile: User ID is blank", result.exceptionOrNull()?.message)
    }

    @Test
    fun `uploadProfileImage calls cloudinaryRepository`() = runTest {
        // Given
        val uri: Uri = mockk()
        val imageUrl = "http://image.url"
        coEvery { cloudinaryRepository.uploadImage(uri) } returns Result.success(imageUrl)

        // When
        val result = repository.uploadProfileImage(uri)

        // Then
        assertEquals(imageUrl, result.getOrNull())
    }
}
