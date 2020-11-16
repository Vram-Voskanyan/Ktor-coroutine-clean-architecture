package com.vram.cleanapp.presenter.core

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

// TODO not presenter level service.
@SuppressLint("SimpleDateFormat")
fun getDateFromTimeStamp(date: String): String = try {
    val sdf = SimpleDateFormat("MM/dd/yyyy")
    val netDate = Date(date.toLong() * 1000)
    sdf.format(netDate)
} catch (e: Exception) {
    "Invalid Date"
}