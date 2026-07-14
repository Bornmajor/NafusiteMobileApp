package com.majasociet.nafusitemobileapp.features.auth.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.tasks.await
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AuthRepositoryImplTest {

    private lateinit var repository: AuthRepositoryImpl
    private val auth: FirebaseAuth = mockk()
    private val database: FirebaseDatabase = mockk()
    private val databaseReference: DatabaseReference = mockk()

    @Before
    fun setUp() {
        mockkStatic("kotlinx.coroutines.tasks.TasksKt")
        every { database.reference } returns databaseReference
        repository = AuthRepositoryImpl(auth, database)
    }

    @After
    fun tearDown() {
        unmockkStatic("kotlinx.coroutines.tasks.TasksKt")
    }

    @Test
    fun `registerNewUser success`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "password"
        val userId = "uid123"
        val authResult: AuthResult = mockk()
        val firebaseUser: FirebaseUser = mockk()
        val taskAuth: Task<AuthResult> = mockk()
        val taskDb: Task<Void> = mockk()

        every { auth.createUserWithEmailAndPassword(email, password) } returns taskAuth
        coEvery { taskAuth.await() } returns authResult
        every { authResult.user } returns firebaseUser
        every { firebaseUser.uid } returns userId

        val userChild: DatabaseReference = mockk()
        every { databaseReference.child("users") } returns databaseReference
        every { databaseReference.child(userId) } returns userChild
        every { userChild.setValue(any()) } returns taskDb
        coEvery { taskDb.await() } returns mockk() // Void task returns null or mock

        // When
        val result = repository.registerNewUser(email, "John", "Doe", "1990-01-01", password, emptyList())

        // Then
        assertTrue(result.isSuccess)
    }

    @Test
    fun `registerNewUser failure during auth`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "password"
        val taskAuth: Task<AuthResult> = mockk()

        every { auth.createUserWithEmailAndPassword(email, password) } returns taskAuth
        coEvery { taskAuth.await() } throws Exception("Auth error")

        // When
        val result = repository.registerNewUser(email, "John", "Doe", "1990-01-01", password, emptyList())

        // Then
        assertTrue(result.isFailure)
        assertEquals("Auth error", result.exceptionOrNull()?.message)
    }

    @Test
    fun `loginUser success when profile exists`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "password"
        val userId = "uid123"
        val authResult: AuthResult = mockk()
        val firebaseUser: FirebaseUser = mockk()
        val taskAuth: Task<AuthResult> = mockk()
        val taskDb: Task<DataSnapshot> = mockk()
        val snapshot: DataSnapshot = mockk()

        every { auth.signInWithEmailAndPassword(email, password) } returns taskAuth
        coEvery { taskAuth.await() } returns authResult
        every { authResult.user } returns firebaseUser
        every { firebaseUser.uid } returns userId

        val userChild: DatabaseReference = mockk()
        every { databaseReference.child("users") } returns databaseReference
        every { databaseReference.child(userId) } returns userChild
        every { userChild.get() } returns taskDb
        coEvery { taskDb.await() } returns snapshot
        every { snapshot.exists() } returns true

        // When
        val result = repository.loginUser(email, password)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(userId, result.getOrNull())
    }

    @Test
    fun `loginUser failure when profile does not exist`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "password"
        val userId = "uid123"
        val authResult: AuthResult = mockk()
        val firebaseUser: FirebaseUser = mockk()
        val taskAuth: Task<AuthResult> = mockk()
        val taskDb: Task<DataSnapshot> = mockk()
        val snapshot: DataSnapshot = mockk()

        every { auth.signInWithEmailAndPassword(email, password) } returns taskAuth
        coEvery { taskAuth.await() } returns authResult
        every { authResult.user } returns firebaseUser
        every { firebaseUser.uid } returns userId

        every { databaseReference.child("users") } returns databaseReference
        every { databaseReference.child(userId) } returns databaseReference
        every { databaseReference.get() } returns taskDb
        coEvery { taskDb.await() } returns snapshot
        every { snapshot.exists() } returns false
        every { auth.signOut() } returns Unit

        // When
        val result = repository.loginUser(email, password)

        // Then
        assertTrue(result.isFailure)
        assertEquals("User profile not found. Please contact support.", result.exceptionOrNull()?.message)
    }
}
