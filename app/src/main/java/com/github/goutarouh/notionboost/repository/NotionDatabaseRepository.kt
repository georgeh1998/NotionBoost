package com.github.goutarouh.notionboost.repository

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.state.PreferencesGlanceStateDefinition
import com.github.goutarouh.notionboost.data.NotionRemoteApi
import com.github.goutarouh.notionboost.widget.MonthlyWidget
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext

interface NotionDatabaseRepository {

    suspend fun queryDatabase(databaseId: String) : QueryDatabaseModel

    suspend fun updateWidget(queryDatabaseModel: QueryDatabaseModel)

}


class NotionDatabaseRepositoryImpl(
    @ApplicationContext private val applicationContext: Context,
    private val gson: Gson,
    private val notionRemoteApi: NotionRemoteApi,
) : NotionDatabaseRepository {

    override suspend fun queryDatabase(databaseId: String) : QueryDatabaseModel {
        return notionRemoteApi.queryDatabase(databaseId).toModel()
    }

    override suspend fun updateWidget(queryDatabaseModel: QueryDatabaseModel) {
        GlanceAppWidgetManager(applicationContext)
            .getGlanceIds(MonthlyWidget::class.java)
            .forEach { glanceId ->
                updateAppWidgetState(
                    context = applicationContext,
                    definition = PreferencesGlanceStateDefinition,
                    glanceId = glanceId
                ) { preferences ->
                    preferences.toMutablePreferences().apply {
                        val queryDatabaseModelJson = gson.toJson(queryDatabaseModel)
                        this[MonthlyWidget.monthlyInfo] = queryDatabaseModelJson
                    }
                }
                MonthlyWidget().update(applicationContext, glanceId)
            }
    }
}



