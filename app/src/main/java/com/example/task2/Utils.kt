package com.example.task2

import java.text.SimpleDateFormat
import java.util.*

class Utils {
    fun formatTime(time: Long): String? {
        val dateFormat = SimpleDateFormat("mm:ss")
        val data = Date(time)
        return dateFormat.format(data)
    }
}