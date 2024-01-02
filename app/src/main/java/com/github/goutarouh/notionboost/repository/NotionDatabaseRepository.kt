package com.github.goutarouh.notionboost.repository

import com.github.goutarouh.notionboost.data.NotionRemoteApi
import com.github.goutarouh.notionboost.data.QueryDatabaseApiAndRequestModel
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
            filter = QueryDatabaseApiAndRequestModel.Filter(
                and = listOf(
                    QueryDatabaseApiAndRequestModel.And(
                        date = QueryDatabaseApiAndRequestModel.Date.OnOrAfter(
                            onOrAfter = inclusiveStartDate.toString()
                        ),
                        property = "Created time"
                    ),
                    QueryDatabaseApiAndRequestModel.And(
                        date = QueryDatabaseApiAndRequestModel.Date.OnOrBefore(
                            onOrBefore = inclusiveEndDate.toString()
                        ),
                        property = "Created time"
                    )
                )
            )
        )

        return notionRemoteApi.queryDatabase(databaseId, queryDatabaseApiAndRequestModel).toModel(now)
    }

    override suspend fun updateWidget(monthlyReportModel: MonthlyReportModel) {
        glanceApi.updateMonthlyReportWidget(monthlyReportModel)
    }
}



