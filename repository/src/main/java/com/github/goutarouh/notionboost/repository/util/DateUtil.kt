package com.github.goutarouh.notionboost.repository.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

object DateFormat {
    val ISO_8601: DateTimeFormatter = DateTimeFormatter
        .ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

    val MONTH_NAME: DateTimeFormatter = DateTimeFormatter
        .ofPattern("MMMM", Locale.ENGLISH)

    val YYYY_MM_DD: DateTimeFormatter = DateTimeFormatter
        .ofPattern("yyyy/MM/dd")

    val MM_DD_HH_MM: DateTimeFormatter = DateTimeFormatter
        .ofPattern("MM/dd HH:mm")
}

fun LocalDateTime.getFirstDayOfThisMonth() : LocalDateTime {
    return this
        .withDayOfMonth(1)
        .truncatedTo(ChronoUnit.DAYS)
}

fun LocalDateTime.getLastDayOfThisMonth() : LocalDateTime {
    return this
        .withDayOfMonth(1)
        .plusMonths(1)
        .minusDays(1)
        .truncatedTo(ChronoUnit.DAYS)
}

fun LocalDateTime.getLastDayOfPreviousMonth() : LocalDateTime {
    return this
        .getFirstDayOfThisMonth()
        .minusDays(1)
}

fun LocalDateTime.getFirstDayOfNextMonth() : LocalDateTime {
    return this
        .getLastDayOfThisMonth()
        .plusDays(1)
}
