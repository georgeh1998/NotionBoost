package com.github.goutarouh.notionboost.repository

import com.github.goutarouh.notionboost.data.NotionRemoteApi
import com.github.goutarouh.notionboost.data.QueryDatabaseApiAndRequestModel
import com.github.goutarouh.notionboost.data.QueryDatabaseApiAndRequestModel.Filter
import com.github.goutarouh.notionboost.data.QueryDatabaseApiAndRequestModel.And
import com.github.goutarouh.notionboost.data.QueryDatabaseApiAndRequestModel.Date
import com.github.goutarouh.notionboost.data.datastore.DataStoreApi
import com.github.goutarouh.notionboost.widget.GlanceApi
import com.github.goutarouh.notionboost.widget.MonthlyReportModel
import java.time.LocalDateTime

interface NotionDatabaseRepository {

    suspend fun saveNotionApiKey(apiKey: String)

    suspend fun getNotionApiKey(): String

    suspend fun queryDatabase(
        databaseId: String,
        now: LocalDateTime,
        inclusiveStartDate: LocalDateTime,
        inclusiveEndDate: LocalDateTime
    ) : QueryDatabaseModel

    suspend fun updateWidget(monthlyReportModel: MonthlyReportModel)

}


class NotionDatabaseRepositoryImpl(
    private val notionRemoteApi: NotionRemoteApi,
    private val dataStoreApi: DataStoreApi,
    private val glanceApi: GlanceApi,
) : NotionDatabaseRepository {

    override suspend fun saveNotionApiKey(apiKey: String) {

        dataStoreApi.setNotionApiKey(apiKey)
    }

    override suspend fun getNotionApiKey(): String {
        return dataStoreApi.getNotionApiKey()
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
        }.toModel(now)
    }

    override suspend fun updateWidget(monthlyReportModel: MonthlyReportModel) {
        glanceApi.updateMonthlyReportWidget(monthlyReportModel)
    }
}



