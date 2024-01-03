package com.github.goutarouh.notionboost.data

import java.time.LocalDateTime
import com.github.goutarouh.notionboost.data.QueryDatabaseApiModel.Result
import com.github.goutarouh.notionboost.data.QueryDatabaseApiModel.Result.Properties
import com.github.goutarouh.notionboost.data.QueryDatabaseApiModel.Result.Properties.*
import java.time.ZoneId
import java.time.ZonedDateTime


fun createQueryDatabaseApiModel(
    results: List<Result> = listOf()
) : QueryDatabaseApiModel {
    return QueryDatabaseApiModel(results)
}

fun createResult(
    createdTime: ZonedDateTime = ZonedDateTime.now(),
    englishLearning: Boolean = false,
    muscleTraining: Boolean = false,
    reading: Boolean = false,
    sleepUntil24: Boolean = false,
) : Result {
    return Result(
        Properties(
            createdTime = CreatedTime(
                createdTime = createdTime
            ),
            englishLearning = EnglishLearning(
                checkbox = englishLearning
            ),
            muscleTraining = MuscleTraining(
                checkbox = muscleTraining
            ),
            reading = Reading(
                checkbox = reading
            ),
            sleepUntil24 = SleepUntil24(
                checkbox = sleepUntil24
            )
        )
    )
}

object QueryDatabaseApiModelTest {
    private val utcZoneId = ZoneId.of("UTC")

    val EnglishLearning_0 = createQueryDatabaseApiModel(
        results = listOf(
            createResult(
                createdTime = ZonedDateTime.of(
                    LocalDateTime.of(2023, 12, 31, 20, 0, 0, 0),
                    utcZoneId
                ),
                englishLearning = false,
            ),
            createResult(
                createdTime = ZonedDateTime.of(
                    LocalDateTime.of(2024, 1, 1, 20, 0, 0),
                    utcZoneId
                ),
                englishLearning = false,
            ),
            createResult(
                createdTime = ZonedDateTime.of(
                    LocalDateTime.of(2024, 1, 2, 20, 0, 0),
                    utcZoneId
                ),
                englishLearning = false,
            ),
            createResult(
                createdTime = ZonedDateTime.of(
                    LocalDateTime.of(2024, 1, 31, 20, 0, 0),
                    utcZoneId,
                ),
                englishLearning = true,
            ),
            createResult(
                createdTime = ZonedDateTime.of(
                    LocalDateTime.of(2024, 2, 1, 20, 0, 0),
                    utcZoneId,
                ),
                englishLearning = true,
            ),
        )
    )
}