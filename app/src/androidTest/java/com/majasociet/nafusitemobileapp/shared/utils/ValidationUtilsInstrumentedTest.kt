package com.majasociet.nafusitemobileapp.shared.utils
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
@RunWith(AndroidJUnit4::class)
class ValidationUtilsInstrumentedTest {
    @Test
    fun isValidEmail_validEmails_returnsTrue() {
        assertTrue(ValidationUtils.isValidEmail("user@example.com"))
        assertTrue(ValidationUtils.isValidEmail("  user.name+tag@sample.co  "))
    }
    @Test
    fun isValidEmail_invalidEmails_returnsFalse() {
        assertFalse(ValidationUtils.isValidEmail(""))
        assertFalse(ValidationUtils.isValidEmail("   "))
        assertFalse(ValidationUtils.isValidEmail("invalid-email"))
        assertFalse(ValidationUtils.isValidEmail("user@"))
    }
}
