package com.github.goutarouh.notionboost.repository

import org.junit.Assert
import org.junit.Test
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime


class QueryDatabaseModelTest {

    private val baseZonedDateTime = ZonedDateTime.of(
        LocalDateTime.of(2023, 12, 31, 20, 0, 0),
        ZoneId.of("UTC")
    )

    @Test
    fun convertToUserZone() {
        // Arrange
        val utcBasedModel = createQueryDatabaseModel(
            dailyInfoList = listOf(
                createDailyInfo(baseZonedDateTime)
            )
        )

        val userZoneId = ZoneId.of("Asia/Tokyo")
        val expectedList = createQueryDatabaseModel(
            dailyInfoList = listOf(
                createDailyInfo(baseZonedDateTime.withZoneSameInstant(userZoneId))
            )
        ).dailyInfoList

        // Act
        val results = utcBasedModel.convertToUserZone(userZoneId)

        // Assert
        expectedList.zip(results.dailyInfoList).forEach { (expected, result) ->
            Assert.assertEquals(expected, result)
        }
    }

}

fun createQueryDatabaseModel(
    now: LocalDateTime = LocalDateTime.now(),
    dailyInfoList: List<DailyInfo> = listOf(),
) : QueryDatabaseModel {
    return QueryDatabaseModel(
        now = now,
        dailyInfoList = dailyInfoList,
        databaseId = ""
    )
}

fun createDailyInfo(
    createdTime: ZonedDateTime = ZonedDateTime.now(),
    isDoneMap: Map<String, Boolean> = mapOf(),
) : DailyInfo {
    return DailyInfo(
        createdTime = createdTime,
        isDoneMap = isDoneMap
    )
}