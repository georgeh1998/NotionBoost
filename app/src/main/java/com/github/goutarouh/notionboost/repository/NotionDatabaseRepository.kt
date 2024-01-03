package com.github.goutarouh.notionboost.repository

import com.github.goutarouh.notionboost.data.NotionRemoteApi
import com.github.goutarouh.notionboost.data.QueryDatabaseApiAndRequestModel
import com.github.goutarouh.notionboost.data.QueryDatabaseApiAndRequestModel.Filter
import com.github.goutarouh.notionboost.data.QueryDatabaseApiAndRequestModel.And
import com.github.goutarouh.notionboost.data.QueryDatabaseApiAndRequestModel.Date
import com.github.goutarouh.notionboost.widget.GlanceApi
import com.github.goutarouh.notionboost.widget.MonthlyReportModel
import java.time.LocalDateTime

interface NotionDatabaseRepository {

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
    private val glanceApi: GlanceApi,
) : NotionDatabaseRepository {

    override suspend fun queryDatabase(
        databaseId: String,
        now : LocalDateTime,
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
            notionRemoteApi.queryDatabase(databaseId, queryDatabaseApiAndRequestModel)
        }.toModel(now)
    }

    override suspend fun updateWidget(monthlyReportModel: MonthlyReportModel) {
        glanceApi.updateMonthlyReportWidget(monthlyReportModel)
    }
}



