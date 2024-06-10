package com.lambui.demomodular.utils

import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.ISODateTimeFormat
import java.util.Locale

fun String.convertToLocalDate(): LocalDate? {
    return try {
        val formatter = DateTimeFormat.forPattern("MM-dd-yyyy").withLocale(Locale.getDefault())
        LocalDate.parse(this, formatter)
    } catch (e: Exception) {
        null
    }
}

fun String.convertToDateTime(): DateTime? {
    return try {
        val formatter = ISODateTimeFormat.dateTime()
        formatter.parseDateTime(this)
    } catch (e: Exception) {
        null
    }
}