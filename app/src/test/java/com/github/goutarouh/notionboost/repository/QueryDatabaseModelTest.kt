package com.github.goutarouh.notionboost.repository

import org.junit.Assert
import org.junit.Test
import java.time.LocalDateTime
import java.time.ZoneId


class QueryDatabaseModelTest {

    private val baseLocalDateTime = LocalDateTime.of(2023, 12, 31, 20, 0, 0)

    @Test
    fun convertToUserZone() {
        // Arrange
        val utcBasedModel = createQueryDatabaseModel(
            dailyInfoList = listOf(
                createDailyInfo(baseLocalDateTime)
            )
        )

        val expectedList = createQueryDatabaseModel(
            dailyInfoList = listOf(
                createDailyInfo(baseLocalDateTime.plusHours(9))
            )
        ).dailyInfoList

        // Act
        val results = utcBasedModel.convertToUserZone(ZoneId.of("Asia/Tokyo"))

        // Assert
        expectedList.zip(results.dailyInfoList).forEach { (expected, result) ->
            Assert.assertEquals(expected, result)
        }
    }

}

private fun createQueryDatabaseModel(
    now: LocalDateTime = LocalDateTime.now(),
    dailyInfoList: List<DailyInfo> = listOf(),
) : QueryDatabaseModel {
    return QueryDatabaseModel(
        now = now,
        dailyInfoList = dailyInfoList
    )
}

private fun createDailyInfo(
    createdTime: LocalDateTime = LocalDateTime.now(),
    doneEnglishLearning: Boolean = false,
    doneMuscleTraining: Boolean = false,
    doneReading: Boolean = false,
    doneSleepUntil24: Boolean = false,
) : DailyInfo {
    return DailyInfo(
        createdTime = createdTime,
        doneEnglishLearning = doneEnglishLearning,
        doneMuscleTraining = doneMuscleTraining,
        doneReading = doneReading,
        doneSleepUntil24 = doneSleepUntil24,
    )
}