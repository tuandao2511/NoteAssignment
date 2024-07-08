package com.example.core.util.view

import android.os.SystemClock
import android.view.View

fun View.safeOnClickListener(debounceTime: Long = 600L, action: () -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0L

        override fun onClick(v: View) {
            val currentClickTime = SystemClock.elapsedRealtime()
            if (currentClickTime - lastClickTime <= debounceTime) return
            lastClickTime = currentClickTime
            action()
        }
    })
}
