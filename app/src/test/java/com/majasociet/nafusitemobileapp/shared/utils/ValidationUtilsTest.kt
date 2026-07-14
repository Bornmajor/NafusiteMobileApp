package com.majasociet.nafusitemobileapp.shared.utils

import org.junit.Assert.*
import org.junit.Test

class ValidationUtilsTest {

    @Test
    fun isSingleWord_validWord_returnsTrue() {
        assertTrue(ValidationUtils.isSingleWord("Nafusite"))
        assertTrue(ValidationUtils.isSingleWord("  Nafusite  "))
    }

    @Test
    fun isSingleWord_multipleWords_returnsFalse() {
        assertFalse(ValidationUtils.isSingleWord("Nafusite App"))
        assertFalse(ValidationUtils.isSingleWord("Hello World"))
    }

    @Test
    fun isSingleWord_emptyOrBlank_returnsFalse() {
        assertFalse(ValidationUtils.isSingleWord(""))
        assertFalse(ValidationUtils.isSingleWord("   "))
    }

    @Test
    fun isValidPassword_validPassword_returnsTrue() {
        assertTrue(ValidationUtils.isValidPassword("Password@123"))
        assertTrue(ValidationUtils.isValidPassword("mY_p@ss1"))
    }

    @Test
    fun isValidPassword_invalidPassword_returnsFalse() {
        assertFalse(ValidationUtils.isValidPassword("short")) // Too short
        assertFalse(ValidationUtils.isValidPassword("password123")) // No uppercase/special (wait, code only checks lowercase, digit, special)
        // Check code: hasMinLength && hasLowerCase && hasDigit && hasSpecialChar
        assertFalse(ValidationUtils.isValidPassword("PASSWORD@123")) // No lowercase
        assertFalse(ValidationUtils.isValidPassword("Password123")) // No special
        assertFalse(ValidationUtils.isValidPassword("P@ssword")) // No digit
    }

    @Test
    fun isOlderThan18_adultDate_returnsTrue() {
        // Using a fixed date far in the past
        assertTrue(ValidationUtils.isOlderThan18("01/01/1990"))
        assertTrue(ValidationUtils.isOlderThan18("31/12/2000"))
    }

    @Test
    fun isOlderThan18_minorDate_returnsFalse() {
        // Using a date that is definitely less than 18 years ago (at the time of writing)
        assertFalse(ValidationUtils.isOlderThan18("01/01/2020"))
    }

    @Test
    fun isOlderThan18_invalidFormat_returnsFalse() {
        assertFalse(ValidationUtils.isOlderThan18("2000-01-01"))
        assertFalse(ValidationUtils.isOlderThan18("invalid"))
        assertFalse(ValidationUtils.isOlderThan18(""))
    }
}
