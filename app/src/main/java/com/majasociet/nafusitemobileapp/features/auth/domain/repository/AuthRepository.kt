package com.majasociet.nafusitemobileapp.features.auth.domain.repository

interface AuthRepository {
    /**
     * Register new user
     * @param email - user email
     *  @param firstName - user first name
     *  @param lastName - user last name
     *  @param dateOfBirth - user date of birth
     *  @param password - user password
     */
    suspend fun registerNewUser(
        email: String,
        firstName: String,
        lastName: String,
        dateOfBirth: String,
        password: String,
        preferences: List<String>
    ): Result<Unit>

    /**
     * Login user
     * @param email - user email
     * @param password - user password
     * @return - result of login
     */
    suspend fun loginUser(
        email: String,
        password: String
    ): Result<String>
}