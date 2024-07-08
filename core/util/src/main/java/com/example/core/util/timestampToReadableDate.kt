package com.example.core.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun timestampToReadableDate(timestamp: Long, format: String = "yyyy-MM-dd HH:mm:ss"): String {
    val date = Date(timestamp)
    val simpleDateFormat = SimpleDateFormat(format, Locale.getDefault())
    return simpleDateFormat.format(date)
}