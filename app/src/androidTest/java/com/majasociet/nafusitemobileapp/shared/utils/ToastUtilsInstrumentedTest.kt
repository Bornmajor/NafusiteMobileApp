package com.majasociet.nafusitemobileapp.shared.utils
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Test
import org.junit.runner.RunWith
@RunWith(AndroidJUnit4::class)
class ToastUtilsInstrumentedTest {
    @Test
    fun show_shortToast_doesNotCrash() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            ToastUtils.show(context, "Short toast")
        }
    }
    @Test
    fun show_longToast_doesNotCrash() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            ToastUtils.show(context, "Long toast", isLong = true)
        }
    }
}
