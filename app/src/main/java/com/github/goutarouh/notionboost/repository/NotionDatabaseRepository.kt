package com.github.goutarouh.notionboost.repository

import com.github.goutarouh.notionboost.data.NotionRemoteApi
import com.github.goutarouh.notionboost.data.QueryDatabaseApiAndRequestModel
import com.github.goutarouh.notionboost.data.QueryDatabaseApiAndRequestModel.Filter
import com.github.goutarouh.notionboost.data.QueryDatabaseApiAndRequestModel.And
import com.github.goutarouh.notionboost.data.QueryDatabaseApiAndRequestModel.Date
import com.github.goutarouh.notionboost.data.datastore.DataStoreApi
import java.time.LocalDateTime


class NotionDatabaseRepository(
    private val notionRemoteApi: NotionRemoteApi,
    private val dataStoreApi: DataStoreApi,
) {

    suspend fun saveNotionApiKey(apiKey: String) {

        dataStoreApi.setNotionApiKey(apiKey)
    }

    suspend fun getNotionApiKey(): String {
        return dataStoreApi.getNotionApiKey()
    }

    suspend fun saveMonthlyWidgetConfiguration(config: Map<Int, String>) {
        dataStoreApi.saveMonthlyWidgetConfiguration(config)
    }

    suspend fun getMonthlyWidgetConfiguration(): Map<Int, String> {
        return dataStoreApi.getMonthlyWidgetConfiguration()
    }

    suspend fun queryDatabase(
        databaseId: String,
        now: LocalDateTime,
        inclusiveStartDate: LocalDateTime,
        inclusiveEndDate: LocalDateTime
    ) : QueryDatabaseModel {

        val queryDatabaseApiAndRequestModel = QueryDatabaseApiAndRequestModel(
            filter = Filter(
                and = listOf(
                    And(date = Date.OnOrAfter(onOrAfter = inclusiveStartDate.toString())),
                    And(date = Date.OnOrBefore(onOrBefore = inclusiveEndDate.toString())),
                )
            )
        )

        return safeApiCall {
            notionRemoteApi.queryDatabase(
                authorization = "Bearer ${getNotionApiKey()}",
                databaseId = databaseId,
                queryDatabaseApiAndRequestModel = queryDatabaseApiAndRequestModel,
            )
        }.toModel(
            now = now,
            databaseId = databaseId
        )
    }
}



