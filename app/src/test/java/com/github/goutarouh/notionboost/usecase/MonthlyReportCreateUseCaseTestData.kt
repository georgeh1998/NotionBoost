package com.github.goutarouh.notionboost.usecase

import com.github.goutarouh.notionboost.data.QueryDatabaseApiModel
import com.github.goutarouh.notionboost.data.createQueryDatabaseApiModel
import com.github.goutarouh.notionboost.data.createResult
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import com.github.goutarouh.notionboost.data.QueryDatabaseApiModel.Result.Properties.CheckBoxProperty


object MonthlyReportCreateUseCaseTestData {

    private val utcZoneId = ZoneId.of("UTC")

    fun createDateBordered(itemTitle: String) : QueryDatabaseApiModel = createQueryDatabaseApiModel(
        results = listOf(
            createResult(
                createdTime = ZonedDateTime.of(
                    LocalDateTime.of(2023, 12, 31, 20, 0, 0),
                    utcZoneId
                ),
                checkBoxProperties = mapOf(itemTitle to CheckBoxProperty(false)),
            ),
            createResult(
                createdTime = ZonedDateTime.of(
                    LocalDateTime.of(2024, 1, 1, 20, 0, 0),
                    utcZoneId
                ),
                checkBoxProperties = mapOf(itemTitle to CheckBoxProperty(false)),
            ),
            createResult(
                createdTime = ZonedDateTime.of(
                    LocalDateTime.of(2024, 1, 2, 20, 0, 0),
                    utcZoneId
                ),
                checkBoxProperties = mapOf(itemTitle to CheckBoxProperty(false)),
            ),
            createResult(
                createdTime = ZonedDateTime.of(
                    LocalDateTime.of(2024, 1, 31, 20, 0, 0),
                    utcZoneId,
                ),
                checkBoxProperties = mapOf(itemTitle to CheckBoxProperty(false)),
            ),
            createResult(
                createdTime = ZonedDateTime.of(
                    LocalDateTime.of(2024, 2, 1, 20, 0, 0),
                    utcZoneId,
                ),
                checkBoxProperties = mapOf(itemTitle to CheckBoxProperty(true)),
            ),
        )
    )

}