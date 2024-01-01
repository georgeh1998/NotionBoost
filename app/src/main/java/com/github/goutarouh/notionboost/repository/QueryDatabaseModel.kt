package com.github.goutarouh.notionboost.repository

import com.github.goutarouh.notionboost.data.QueryDatabaseApiModel
import java.time.LocalDate

data class QueryDatabaseModel(
    val dailyInfo: List<DailyInfo>
)

data class DailyInfo(
    val createdTime: LocalDate,
    val doneEnglishLearning: Boolean,
    val doneMuscleTraining: Boolean,
    val doneReading: Boolean,
    val doneSleepUntil24: Boolean,
)

fun QueryDatabaseApiModel.toModel(): QueryDatabaseModel {
    return QueryDatabaseModel(
        dailyInfo = this.results.map { result ->
            DailyInfo(
                createdTime = LocalDate.parse(result.properties.createdTime.createdTime),
                doneEnglishLearning = result.properties.englishLearning.checkbox,
                doneMuscleTraining = result.properties.muscleTraining.checkbox,
                doneReading = result.properties.reading.checkbox,
                doneSleepUntil24 = result.properties.sleepUntil24.checkbox,
            )
        }
    )
}