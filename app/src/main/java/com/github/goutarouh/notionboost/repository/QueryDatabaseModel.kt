package com.github.goutarouh.notionboost.repository

import com.github.goutarouh.notionboost.data.QueryDatabaseApiModel
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

data class QueryDatabaseModel(
    val now: LocalDateTime,
    val dailyInfoList: List<DailyInfo>
) {

    fun convertToUserZone(
        userZoneId: ZoneId = ZoneId.systemDefault()
    ) : QueryDatabaseModel {
        return this.copy(
            dailyInfoList = this.dailyInfoList.map {
                it.copy(
                    createdTime = it.createdTime.withZoneSameInstant(userZoneId)
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
    val createdTime: ZonedDateTime,
    val isDoneMap: Map<String, Boolean>,
)

fun QueryDatabaseApiModel.toModel(now: LocalDateTime): QueryDatabaseModel {
    return QueryDatabaseModel(
        now = now,
        dailyInfoList = this.results.map { result ->
            DailyInfo(
                createdTime = result.properties.createdTime.createdTime,
                isDoneMap = result.properties.checkBoxProperties.mapValues { it.value.checkbox }
            )
        }
    )
}