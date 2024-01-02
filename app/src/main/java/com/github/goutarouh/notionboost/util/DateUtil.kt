package com.github.goutarouh.notionboost.util

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

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
