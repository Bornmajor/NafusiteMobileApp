package com.majasociet.nafusitemobileapp.shared.utils

import android.content.Context
import android.widget.Toast

object ToastUtils {
    /**
     * Show a toast message
     * @param context - context to show the toast from
     * @param message - message to show
     * @param isLong - whether the toast should be long or short
     */
    fun show(context: Context, message: String, isLong: Boolean = false) {
        Toast.makeText(
            context,
            message,
            if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
        ).show()
    }
}

// Usage in Compose:
// val context = LocalContext.current
// ToastUtils.show(context, "Login Successful!")