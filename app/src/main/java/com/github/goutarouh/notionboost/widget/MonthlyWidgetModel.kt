package com.github.goutarouh.notionboost.widget

import com.github.goutarouh.notionboost.repository.QueryDatabaseModel
import com.github.goutarouh.notionboost.util.getFirstDayOfThisMonth
import com.github.goutarouh.notionboost.util.getLastDayOfThisMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

data class MonthlyReportModel(
    val isEmpty: Boolean = false,
    val monthlyReport: MonthlyReport
)

data class MonthlyReport (
    val monthName: String,
    val startDate: String,
    val endDate: String,
    val englishLearningProgress: Float = 0f,
    val sleepUntil24Progress: Float = 0f,
    val readingProgress: Float = 0f,
    val workOutProgress: Float = 0f,
) {
    fun calculateProgress(progress: Float): Int {
        return (progress * 100).toInt()
    }
}

private val monthNameFormatter = DateTimeFormatter.ofPattern("MMMM", Locale.ENGLISH)
private val dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")


fun QueryDatabaseModel.toMonthlyReportModel(): MonthlyReportModel {

    val startDate = now.getFirstDayOfThisMonth()
    val endDate = now.getLastDayOfThisMonth()

    return if (dailyInfoList.isEmpty()) {
        val monthlyReport = MonthlyReport(
            monthName = now.format(monthNameFormatter),
            startDate = startDate.format(dateFormatter),
            endDate = endDate.format(dateFormatter),
        )
        MonthlyReportModel(true, monthlyReport)
    } else {
        val countAllDay = dailyInfoList.size.toFloat()
        val englishLearningProgress = dailyInfoList.filter { it.doneEnglishLearning }.size
        val workOutProgress = dailyInfoList.filter { it.doneMuscleTraining }.size
        val sleepUntil24Progress = dailyInfoList.filter { it.doneSleepUntil24 }.size
        val readingProgress = dailyInfoList.filter { it.doneReading }.size
        val monthlyReport = MonthlyReport(
            monthName = now.format(monthNameFormatter),
            startDate = startDate.format(dateFormatter),
            endDate = endDate.format(dateFormatter),
            englishLearningProgress = englishLearningProgress / countAllDay,
            workOutProgress = workOutProgress / countAllDay,
            sleepUntil24Progress = sleepUntil24Progress / countAllDay,
            readingProgress = readingProgress / countAllDay,
        )
        MonthlyReportModel(false, monthlyReport)
    }
}