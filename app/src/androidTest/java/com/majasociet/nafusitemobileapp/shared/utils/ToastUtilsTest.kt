package com.majasociet.nafusitemobileapp.shared.utils

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ToastUtilsTest {

    @Test
    fun show_doesNotCrash() {
        // Get the instrumentation context
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        
        // Since Toast display is handled by the system UI, verifying the actual 
        // visibility is complex. This test ensures the utility executes without crashing.
        ToastUtils.show(context, "Test Short Toast", isLong = false)
    }

    @Test
    fun showLong_doesNotCrash() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        ToastUtils.show(context, "Test Long Toast", isLong = true)
    }
}
