package com.majasociet.nafusitemobileapp.shared.utils

import android.util.Patterns
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object ValidationUtils {

    /**
     * Validates if the input string contains exactly one word.
     * Returns false if there are spaces separating multiple words, or if it's empty.
     */
    fun isSingleWord(text: String): Boolean {
        val cleanedText = text.trim()
        if (cleanedText.isEmpty()) return false

        // This regex looks for any whitespace character (spaces, tabs, newlines).
        // If a space is found, it means there is more than one word.
        return !cleanedText.contains("\\s".toRegex())
    }

    /**
     * Validates if a string is a properly formatted email address.
     * Uses Android's built-in platform regex patterns.
     */
    fun isValidEmail(email: String): Boolean {
        if (email.isBlank()) return false
        return Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()
    }

    /**
     * Checks if a date string in "dd/MM/yyyy" format represents a person who is at least 18 years old.
     * Compatible with all API versions.
     */
    fun isOlderThan18(dateString: String): Boolean {
        if (dateString.isBlank()) return false

        return try {
            // 1. Parse the formatted string into a standard Date object
            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            formatter.isLenient = false // Strict parsing (rejects invalid dates like 31/11)
            val birthDate: Date = formatter.parse(dateString.trim()) ?: return false

            // 2. Get a Calendar instance configured with the birthdate
            val birthCalendar = Calendar.getInstance().apply {
                time = birthDate
            }

            // 3. Get another Calendar instance tracking exactly 18 years ago today
            val eighteenYearsAgoCalendar = Calendar.getInstance().apply {
                // Subtract 18 years from the current time right now
                add(Calendar.YEAR, -18)
            }

            // 4. If birthCalendar is BEFORE or EQUAL to eighteenYearsAgoCalendar, they are 18+!
            !birthCalendar.after(eighteenYearsAgoCalendar)

        } catch (e: Exception) {
            // Returns false if the text format doesn't match or cannot be mathematically parsed
            false
        }
    }

    /**
     * Validates password based on:
     * - Minimum 8 characters
     * - At least one special character
     * - At least one lowercase letter
     * - At least one numeric value
     */
    fun isValidPassword(password: String): Boolean {
        val hasMinLength = password.length >= 8
        val hasLowerCase = password.any { it.isLowerCase() }
        val hasDigit = password.any { it.isDigit() }
        val hasSpecialChar = password.any { !it.isLetterOrDigit() }

        return hasMinLength && hasLowerCase && hasDigit && hasSpecialChar
    }
}