package com.lambui.common.extension.context

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.TimeZone

private const val SECOND_MILLIS = 1000
private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
private const val DAY_MILLIS = 24 * HOUR_MILLIS

@Throws(ParseException::class)
fun getLocalTime(timestamp: String?, simpleDateFormat: String?): Date? {
    val formatter = SimpleDateFormat(simpleDateFormat).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }
    return timestamp?.let { formatter.parse(it) }
}

@Throws(ParseException::class)
fun timestampToMilli(timestamp: String?, simpleDateFormat: String?): Long {
    val dateUtc = getLocalTime(timestamp, simpleDateFormat)
    val dateFormatter =
        SimpleDateFormat(simpleDateFormat).apply { timeZone = TimeZone.getDefault() }
    val localTimeString = dateUtc?.let { dateFormatter.format(it) }

    val date = localTimeString?.let { SimpleDateFormat(simpleDateFormat).parse(it) }
    return date!!.time
}

@SuppressLint("NewApi")
fun milliToStringTime(milli: Long): String {
    val instant = Instant.ofEpochMilli(milli)
    val date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    return formatter.format(date)
}

