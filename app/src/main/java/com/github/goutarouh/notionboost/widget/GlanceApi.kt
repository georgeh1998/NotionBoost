package com.github.goutarouh.notionboost.widget

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.state.PreferencesGlanceStateDefinition
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext

interface GlanceApi {

    suspend fun updateMonthlyWidgetsByWidgetIds(
        appWidgetIds: List<Int>,
        monthlyWidgetModel: MonthlyWidgetModel,
    )

    suspend fun updateMonthlyWidgetsByDatabaseId(
        monthlyWidgetModel: MonthlyWidgetModel
    )

}

class GlanceApiImpl(
    @ApplicationContext private val applicationContext: Context,
    private val gson: Gson,
) : GlanceApi {

    override suspend fun updateMonthlyWidgetsByWidgetIds(
        appWidgetIds: List<Int>,
        monthlyWidgetModel: MonthlyWidgetModel
    ) {
        val manager = GlanceAppWidgetManager(applicationContext)
        appWidgetIds.forEach { appWidgetId ->
            val glanceId = manager.getGlanceIdBy(appWidgetId)
            updateAppWidgetState(
                context = applicationContext,
                definition = PreferencesGlanceStateDefinition,
                glanceId = glanceId
            ) { preferences ->
                preferences.toMutablePreferences().apply {
                    val monthlyWidgetModelJson = gson.toJson(monthlyWidgetModel)
                    this[MonthlyWidget.monthlyWidgetModel] = monthlyWidgetModelJson
                }
            }
            MonthlyWidget().update(applicationContext, glanceId)
        }
    }

    override suspend fun updateMonthlyWidgetsByDatabaseId(
        monthlyWidgetModel: MonthlyWidgetModel
    ) {
        GlanceAppWidgetManager(applicationContext)
            .getGlanceIds(MonthlyWidget::class.java)
            .forEach { glanceId ->
                updateAppWidgetState(
                    context = applicationContext,
                    definition = PreferencesGlanceStateDefinition,
                    glanceId = glanceId
                ) { preferences ->
                    val pref = preferences.toMutablePreferences()
                    val prevModelJson = pref[MonthlyWidget.monthlyWidgetModel]
                    val prevModel = gson.fromJson(prevModelJson, MonthlyWidgetModel::class.java)
                    if (prevModel == null) {
                        pref
                    } else {
                        if (prevModel.databaseId == monthlyWidgetModel.databaseId) {
                            val monthlyWidgetModelJson = gson.toJson(monthlyWidgetModel)
                            pref[MonthlyWidget.monthlyWidgetModel] = monthlyWidgetModelJson
                        }
                        pref
                    }
                }
                MonthlyWidget().update(applicationContext, glanceId)
            }
    }
}