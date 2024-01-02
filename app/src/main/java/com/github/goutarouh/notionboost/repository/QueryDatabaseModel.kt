package com.github.goutarouh.notionboost.repository

import com.github.goutarouh.notionboost.data.QueryDatabaseApiModel
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class QueryDatabaseModel(
    val now: LocalDateTime,
    val dailyInfoList: List<DailyInfo>
) {

    fun convertToUserZone(
        userZoneId: ZoneId = ZoneId.systemDefault()
    ) : QueryDatabaseModel {
        val utcZoneId = ZoneId.of("UTC")

        return this.copy(
            dailyInfoList = this.dailyInfoList.map {
                it.copy(
                    createdTime = it.createdTime.atZone(utcZoneId).withZoneSameInstant(userZoneId).toLocalDateTime()
                )
            }
        )
    }

    /**
     * (ex) JANUARY -> 1
     * (ex) DECEMBER -> 12
     */
    fun filterByMonth(monthValue: Int) : QueryDatabaseModel {
        return this.copy(
            dailyInfoList = this.dailyInfoList.filter {
                it.createdTime.monthValue == monthValue
            }
        )
    }
}

data class DailyInfo(
    val createdTime: LocalDateTime,
    val doneEnglishLearning: Boolean,
    val doneMuscleTraining: Boolean,
    val doneReading: Boolean,
    val doneSleepUntil24: Boolean,
)

private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

fun QueryDatabaseApiModel.toModel(now: LocalDateTime): QueryDatabaseModel {
    return QueryDatabaseModel(
        now = now,
        dailyInfoList = this.results.map { result ->
            DailyInfo(
                createdTime = LocalDateTime.parse(result.properties.createdTime.createdTime, formatter),
                doneEnglishLearning = result.properties.englishLearning.checkbox,
                doneMuscleTraining = result.properties.muscleTraining.checkbox,
                doneReading = result.properties.reading.checkbox,
                doneSleepUntil24 = result.properties.sleepUntil24.checkbox,
            )
        }
    )
}