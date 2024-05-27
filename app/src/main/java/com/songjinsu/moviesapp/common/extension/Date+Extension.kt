package com.songjinsu.moviesapp.common.extension

import android.icu.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.toFormattedString(): String {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return simpleDateFormat.format(this)
}