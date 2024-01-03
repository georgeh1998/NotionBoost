package com.github.goutarouh.notionboost.data

import com.github.goutarouh.notionboost.data.QueryDatabaseApiModel.Result
import com.github.goutarouh.notionboost.data.QueryDatabaseApiModel.Result.Properties
import com.github.goutarouh.notionboost.data.QueryDatabaseApiModel.Result.Properties.*
import java.time.ZonedDateTime


fun createQueryDatabaseApiModel(
    results: List<Result> = listOf()
) : QueryDatabaseApiModel {
    return QueryDatabaseApiModel(results)
}

fun createResult(
    createdTime: ZonedDateTime = ZonedDateTime.now(),
    checkBoxProperties: Map<String, CheckBoxProperty> = mapOf(),
) : Result {
    return Result(
        Properties(
            createdTime = CreatedTime(
                createdTime = createdTime
            ),
            checkBoxProperties = checkBoxProperties
        )
    )
}