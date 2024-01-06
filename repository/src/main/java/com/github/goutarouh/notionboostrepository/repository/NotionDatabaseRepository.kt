package com.github.goutarouh.notionboostrepository.repository

import com.github.goutarouh.notionboost.data.api.NotionRemoteApi
import com.github.goutarouh.notionboost.data.api.queryDatabase.QueryDatabaseApiAndRequestModel
import com.github.goutarouh.notionboost.data.api.queryDatabase.QueryDatabaseApiAndRequestModel.Filter
import com.github.goutarouh.notionboost.data.api.queryDatabase.QueryDatabaseApiAndRequestModel.And
import com.github.goutarouh.notionboost.data.api.queryDatabase.QueryDatabaseApiAndRequestModel.Date
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
        return getDataStoreCall {
            dataStoreApi.getNotionApiKey()
        }
    }

    suspend fun saveMonthlyWidgetConfiguration(config: Map<Int, String>) {
        dataStoreApi.saveMonthlyWidgetConfiguration(config)
    }

    suspend fun getMonthlyWidgetConfiguration(): Map<Int, String> {
        return getDataStoreCall {
            dataStoreApi.getMonthlyWidgetConfiguration()
        }
    }

    suspend fun removeMonthlyWidgetConfiguration(appWidgetIds: List<Int>) {
        dataStoreApi.removeMonthlyWidgetConfiguration(appWidgetIds)
    }

    fun monthlyWidgetConfigurationFlow() = dataStoreApi.monthlyWidgetConfigurationFlow()

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

        // TODO NotionAPIキーを保存しないままWidgetを作成するとエラーになる
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

    suspend fun retrieveDatabase(
        databaseId: String,
    ) : RetrieveDatabaseModel {
        return safeApiCall {
            notionRemoteApi.retrieveDatabase(
                authorization = "Bearer ${getNotionApiKey()}",
                databaseId = databaseId,
            )
        }.toModel()
    }
}



