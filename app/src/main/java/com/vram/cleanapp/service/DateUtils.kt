package com.vram.cleanapp.service

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

// Note. date, pattern fail case not handled. = try { ... } catch throw WrappedDateException...
@SuppressLint("SimpleDateFormat")
fun convertToDateString(date: String, pattern: String): String {
    val sdf = SimpleDateFormat(pattern)
    val netDate = Date(date.toLong() * 1000)
    return sdf.format(netDate)
}