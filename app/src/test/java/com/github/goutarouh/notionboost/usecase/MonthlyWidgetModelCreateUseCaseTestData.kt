package com.github.goutarouh.notionboost.usecase

import com.github.goutarouh.notionboost.data.api.queryDatabase.QueryDatabaseApiResponseModel
import com.github.goutarouh.notionboost.data.createQueryDatabaseApiModel
import com.github.goutarouh.notionboost.data.createResult
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import com.github.goutarouh.notionboost.data.api.queryDatabase.QueryDatabaseApiResponseModel.Result.Properties.CheckBoxProperty


object MonthlyWidgetModelCreateUseCaseTestData {

    private val utcZoneId = ZoneId.of("UTC")

    fun createDateBordered(itemTitle: String) : QueryDatabaseApiResponseModel =
        com.github.goutarouh.notionboost.data.createQueryDatabaseApiModel(
            results = listOf(
                com.github.goutarouh.notionboost.data.createResult(
                    createdTime = ZonedDateTime.of(
                        LocalDateTime.of(2023, 12, 31, 20, 0, 0),
                        utcZoneId
                    ),
                    checkBoxProperties = mapOf(itemTitle to CheckBoxProperty(false)),
                ),
                com.github.goutarouh.notionboost.data.createResult(
                    createdTime = ZonedDateTime.of(
                        LocalDateTime.of(2024, 1, 1, 20, 0, 0),
                        utcZoneId
                    ),
                    checkBoxProperties = mapOf(itemTitle to CheckBoxProperty(false)),
                ),
                com.github.goutarouh.notionboost.data.createResult(
                    createdTime = ZonedDateTime.of(
                        LocalDateTime.of(2024, 1, 2, 20, 0, 0),
                        utcZoneId
                    ),
                    checkBoxProperties = mapOf(itemTitle to CheckBoxProperty(false)),
                ),
                com.github.goutarouh.notionboost.data.createResult(
                    createdTime = ZonedDateTime.of(
                        LocalDateTime.of(2024, 1, 31, 20, 0, 0),
                        utcZoneId,
                    ),
                    checkBoxProperties = mapOf(itemTitle to CheckBoxProperty(false)),
                ),
                com.github.goutarouh.notionboost.data.createResult(
                    createdTime = ZonedDateTime.of(
                        LocalDateTime.of(2024, 2, 1, 20, 0, 0),
                        utcZoneId,
                    ),
                    checkBoxProperties = mapOf(itemTitle to CheckBoxProperty(true)),
                ),
            )
        )

}