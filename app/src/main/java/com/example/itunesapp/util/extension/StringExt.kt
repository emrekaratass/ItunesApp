package com.example.itunesapp.util.extension

import android.os.Build
import android.text.Html
import android.text.Spanned

fun String?.ignoreNull(defaultValue: String = ""): String = this ?: defaultValue

@Suppress("DEPRECATION")
fun String.toSpanned(): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(this)
    }
}
