package com.github.goutarouh.notionboost.repository

import com.github.goutarouh.notionboost.data.NotionRemoteApi
import com.github.goutarouh.notionboost.data.QueryDatabaseApiAndRequestModel
import com.github.goutarouh.notionboost.data.QueryDatabaseApiAndRequestModel.Filter
import com.github.goutarouh.notionboost.data.QueryDatabaseApiAndRequestModel.And
import com.github.goutarouh.notionboost.data.QueryDatabaseApiAndRequestModel.Date
import com.github.goutarouh.notionboost.data.datastore.DataStoreApi
import java.time.LocalDateTime

interface NotionDatabaseRepository {

    suspend fun saveNotionApiKey(apiKey: String)

    suspend fun getNotionApiKey(): String

    suspend fun addDatabaseId(databaseId: String)

    suspend fun getDatabaseIds(): Set<String>

    suspend fun queryDatabase(
        databaseId: String,
        now: LocalDateTime,
        inclusiveStartDate: LocalDateTime,
        inclusiveEndDate: LocalDateTime
    ) : QueryDatabaseModel


}


class NotionDatabaseRepositoryImpl(
    private val notionRemoteApi: NotionRemoteApi,
    private val dataStoreApi: DataStoreApi,
) : NotionDatabaseRepository {

    override suspend fun saveNotionApiKey(apiKey: String) {

        dataStoreApi.setNotionApiKey(apiKey)
    }

    override suspend fun getNotionApiKey(): String {
        return dataStoreApi.getNotionApiKey()
    }

    override suspend fun addDatabaseId(databaseId: String) {
        dataStoreApi.addDatabaseId(databaseId)
    }

    override suspend fun getDatabaseIds(): Set<String> {
        return dataStoreApi.getDatabaseIds()
    }

    override suspend fun queryDatabase(
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



