package com.majasociet.nafusitemobileapp.features.auth.ui.viewmodel

import app.cash.turbine.test
import com.majasociet.nafusitemobileapp.features.auth.domain.repository.AuthRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest {

    private lateinit var viewModel: AuthViewModel
    private val authRepository: AuthRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = AuthViewModel(authRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `registerUser success should emit SubmitRegistrationSuccess event`() = runTest {
        // Given
        val email = "test@example.com"
        val firstName = "John"
        val lastName = "Doe"
        val dob = "1990-01-01"
        val password = "password123"
        val preferences = listOf("Tech", "Science")

        coEvery {
            authRepository.registerNewUser(email, firstName, lastName, dob, password, preferences)
        } returns Result.success(Unit)

        // When & Then
        viewModel.uiEvent.test {
            viewModel.registerUser(email, firstName, lastName, dob, password, preferences)
            val event = awaitItem()
            assert(event is UIEvent.SubmitRegistrationSuccess)
        }

        viewModel.authState.test {
            // Initial state
            assertEquals(false, awaitItem().isLoading)
        }
    }

    @Test
    fun `registerUser failure should emit SubmitRegistrationFailure event`() = runTest {
        // Given
        val errorMessage = "Registration failed"
        coEvery {
            authRepository.registerNewUser(any(), any(), any(), any(), any(), any())
        } returns Result.failure(Exception(errorMessage))

        // When & Then
        viewModel.uiEvent.test {
            viewModel.registerUser("test@example.com", "John", "Doe", "1990-01-01", "password", emptyList())
            val event = awaitItem()
            assert(event is UIEvent.SubmitRegistrationFailure)
            assertEquals(errorMessage, (event as UIEvent.SubmitRegistrationFailure).message)
        }
    }

    @Test
    fun `loginUser success should update state and emit SubmitLoginSuccess event`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "password123"
        val userId = "user123"

        coEvery {
            authRepository.loginUser(email, password)
        } returns Result.success(userId)

        // When & Then
        viewModel.uiEvent.test {
            viewModel.loginUser(email, password)
            val event = awaitItem()
            assert(event is UIEvent.SubmitLoginSuccess)
            assertEquals(userId, (event as UIEvent.SubmitLoginSuccess).userId)
        }

        assertEquals(true, viewModel.authState.value.isLogin)
        assertEquals(false, viewModel.authState.value.isLoading)
    }

    @Test
    fun `loginUser failure should emit SubmitLoginFailure event`() = runTest {
        // Given
        val errorMessage = "Invalid credentials"
        coEvery {
            authRepository.loginUser(any(), any())
        } returns Result.failure(Exception(errorMessage))

        // When & Then
        viewModel.uiEvent.test {
            viewModel.loginUser("test@example.com", "wrong_password")
            val event = awaitItem()
            assert(event is UIEvent.SubmitLoginFailure)
            assertEquals(errorMessage, (event as UIEvent.SubmitLoginFailure).message)
        }

        assertEquals(false, viewModel.authState.value.isLogin)
        assertEquals(false, viewModel.authState.value.isLoading)
    }

    @Test
    fun `logoutUser should set isLogin to false`() = runTest {
        // When
        viewModel.logoutUser()

        // Then
        assertEquals(false, viewModel.authState.value.isLogin)
    }
}
