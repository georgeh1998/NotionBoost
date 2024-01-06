package com.github.goutarouh.notionboost.data

import com.github.goutarouh.notionboost.data.api.queryDatabase.QueryDatabaseApiResponseModel
import com.github.goutarouh.notionboost.data.api.queryDatabase.QueryDatabaseApiResponseModel.Result
import com.github.goutarouh.notionboost.data.api.queryDatabase.QueryDatabaseApiResponseModel.Result.Properties
import com.github.goutarouh.notionboost.data.api.queryDatabase.QueryDatabaseApiResponseModel.Result.Properties.*
import java.time.ZonedDateTime


fun createQueryDatabaseApiResponseModel(
    results: List<Result> = listOf()
) : QueryDatabaseApiResponseModel {
    return QueryDatabaseApiResponseModel(results)
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