package com.example.itunesapp.util.extension

fun Int?.ignoreNull(defaultValue: Int = 0): Int = this ?: defaultValue
