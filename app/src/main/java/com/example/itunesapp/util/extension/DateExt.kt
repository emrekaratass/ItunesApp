package com.example.itunesapp.util.extension

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun String.formattedReleaseTime(): String {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val date: Date? = simpleDateFormat.parse(this)
    return SimpleDateFormat("dd/MM/yyyy").format(date)
}
