package com.majasociet.nafusitemobileapp.features.auth.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.majasociet.nafusitemobileapp.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Auth view model - handles authentication logic
 * @param authRepository - auth repository
 */
class AuthViewModel(
    private val authRepository: AuthRepository
): ViewModel() {

    // immutable state flow for auth state only access via model
   private val _authState = MutableStateFlow<AuthState>(AuthState())
    //For collecting UI events
    private val _uiEvent = MutableSharedFlow<UIEvent>()

    val authState = _authState.asStateFlow() // expose immutable state flow to UI
    val uiEvent = _uiEvent.asSharedFlow() // expose immutable event flow to UI

    /**
     * Register user
     * @param email - user email
     * @param firstName - user first name
     * @param lastName - user last name
     * @param dateOfBirth - user date of birth
     * @param password - user password
     */
    fun registerUser(
        email:String,
        firstName:String,
        lastName: String,
        dateOfBirth:String,
        password:String,
        selectedPreferences: List<String>
    ){
        //Show loader
        _authState.update {
            it.copy(
                isLoading = true
            )
        }

        viewModelScope.launch {
            val result = authRepository.registerNewUser(
                email = email,
                firstName = firstName,
                lastName = lastName,
                dateOfBirth = dateOfBirth,
                password = password,
                preferences = selectedPreferences
            )

            result.onSuccess {
                _authState.update {
                    it.copy(
                      isLoading = false
                    )
                }
                _uiEvent.emit(UIEvent.SubmitRegistrationSuccess)
            }

            result.onFailure { exception ->
                _authState.update {
                    it.copy(
                        isLoading = false
                    )
                }
                _uiEvent.emit(UIEvent.SubmitRegistrationFailure(exception.localizedMessage ?:"An unexpected error occurred."))

            }
        }
    }

    /**
     * Login user
     * @param email - user email
     * @param password - user password
     */
    fun loginUser(
        email: String,
        password:String
    ){
        _authState.update{
            it.copy(
                isLoading = true
            )
        }
        viewModelScope.launch {
            val result = authRepository.loginUser(
                email = email,
                password = password
            )

            result.onSuccess { userId ->
                _authState.update {
                    it.copy(
                        isLoading = false,
                        isLogin = true
                    )
                }
                _uiEvent.emit(UIEvent.SubmitLoginSuccess(userId))
            }

            result.onFailure { exception ->
                _authState.update {
                    it.copy(
                        isLoading = false
                    )
                }
                _uiEvent.emit(UIEvent.SubmitLoginFailure(exception.localizedMessage ?: "An unexpected error occurred."))
            }
        }

    }

    fun logoutUser(){
        _authState.update {
            it.copy(
                isLogin = false
            )
        }
    }

}

// Declare all possible UI events
sealed interface UIEvent{
    object SubmitRegistrationSuccess: UIEvent
    data class SubmitRegistrationFailure(val message: String): UIEvent
    data class SubmitLoginSuccess(val userId:String): UIEvent
    data class SubmitLoginFailure(val message: String): UIEvent
}