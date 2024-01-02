package com.github.goutarouh.notionboost.repository

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.state.PreferencesGlanceStateDefinition
import com.github.goutarouh.notionboost.data.NotionRemoteApi
import com.github.goutarouh.notionboost.data.QueryDatabaseApiAndRequestModel
import com.github.goutarouh.notionboost.widget.MonthlyReportModel
import com.github.goutarouh.notionboost.widget.MonthlyWidget
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalDateTime

interface NotionDatabaseRepository {

    suspend fun queryDatabase(
        databaseId: String,
        now: LocalDateTime,
        inclusiveStartDate: LocalDateTime,
        exclusiveEndDate: LocalDateTime
    ) : QueryDatabaseModel

    suspend fun updateWidget(monthlyReportModel: MonthlyReportModel)

}


class NotionDatabaseRepositoryImpl(
    @ApplicationContext private val applicationContext: Context,
    private val gson: Gson,
    private val notionRemoteApi: NotionRemoteApi,
) : NotionDatabaseRepository {

    override suspend fun queryDatabase(
        databaseId: String,
        now : LocalDateTime,
        inclusiveStartDate: LocalDateTime,
        exclusiveEndDate: LocalDateTime
    ) : QueryDatabaseModel {

        val queryDatabaseApiAndRequestModel = QueryDatabaseApiAndRequestModel(
            filter = QueryDatabaseApiAndRequestModel.Filter(
                and = listOf(
                    QueryDatabaseApiAndRequestModel.Filter.And(
                        date = QueryDatabaseApiAndRequestModel.Filter.And.Date.OnOrAfter(
                            onOrAfter = inclusiveStartDate.toString()
                        ),
                        property = "Created time"
                    ),
                    QueryDatabaseApiAndRequestModel.Filter.And(
                        date = QueryDatabaseApiAndRequestModel.Filter.And.Date.Before(
                            before = exclusiveEndDate.toString()
                        ),
                        property = "Created time"
                    )
                )
            )
        )

        return notionRemoteApi.queryDatabase(databaseId, queryDatabaseApiAndRequestModel).toModel(now)
    }

    override suspend fun updateWidget(monthlyReportModel: MonthlyReportModel) {
        GlanceAppWidgetManager(applicationContext)
            .getGlanceIds(MonthlyWidget::class.java)
            .forEach { glanceId ->
                updateAppWidgetState(
                    context = applicationContext,
                    definition = PreferencesGlanceStateDefinition,
                    glanceId = glanceId
                ) { preferences ->
                    preferences.toMutablePreferences().apply {
                        val monthlyReportModelJson = gson.toJson(monthlyReportModel)
                        this[MonthlyWidget.monthlyReportModel] = monthlyReportModelJson
                    }
                }
                MonthlyWidget().update(applicationContext, glanceId)
            }
    }
}



