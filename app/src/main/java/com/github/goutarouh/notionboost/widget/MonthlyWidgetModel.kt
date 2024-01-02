package com.github.goutarouh.notionboost.widget

import com.github.goutarouh.notionboost.repository.QueryDatabaseModel
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

data class MonthlyReportModel(
    val isEmpty: Boolean = false,
    val monthlyReport: MonthlyReport
)

data class MonthlyReport (
    val monthName: String,
    val startDate: String,
    val endDate: String,
)

private val monthNameFormatter = DateTimeFormatter.ofPattern("MMMM", Locale.ENGLISH)
private val dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")


fun QueryDatabaseModel.toMonthlyReportModel(): MonthlyReportModel {

    val dailyInfoList = dailyInfoList

    val startDate = now
        .withDayOfMonth(1)
        .truncatedTo(ChronoUnit.DAYS)

    val endDate = now
        .withDayOfMonth(1)
        .plusMonths(1)
        .minusDays(1)
        .truncatedTo(ChronoUnit.DAYS)

    return if (dailyInfoList.isEmpty()) {
        val monthlyReport = MonthlyReport(
            monthName = now.format(monthNameFormatter),
            startDate = startDate.format(dateFormatter),
            endDate = endDate.format(dateFormatter),
        )
        MonthlyReportModel(true, monthlyReport)
    } else {
        val monthlyReport = MonthlyReport(
            monthName = now.format(monthNameFormatter),
            startDate = startDate.format(dateFormatter),
            endDate = endDate.format(dateFormatter),
        )
        MonthlyReportModel(false, monthlyReport)
    }
}