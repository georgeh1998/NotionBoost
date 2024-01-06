package com.github.goutarouh.notionboost.widget

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.getAppWidgetState
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.state.PreferencesGlanceStateDefinition
import com.github.goutarouh.notionboost.data.datastore.DataStoreKey
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext

interface GlanceApi {

    suspend fun getMonthlyWidgetModeByWidgetByGlanceId(appWidgetId: Int) : MonthlyWidgetModel?

    suspend fun updateMonthlyWidgetsByWidgetIds(
        appWidgetIds: List<Int>,
        monthlyWidgetModel: MonthlyWidgetModel,
    )
}

class GlanceApiImpl(
    @ApplicationContext private val applicationContext: Context,
    private val gson: Gson,
) : GlanceApi {

    private val manager = GlanceAppWidgetManager(applicationContext)

    override suspend fun getMonthlyWidgetModeByWidgetByGlanceId(
        appWidgetId: Int
    ) : MonthlyWidgetModel? {
        val glanceId = manager.getGlanceIdBy(appWidgetId)
        val monthlyWidgetModelJson = getAppWidgetState(
            context = applicationContext,
            definition = PreferencesGlanceStateDefinition,
            glanceId = glanceId
        )[DataStoreKey.monthlyWidgetModel] ?: return null
        return gson.fromJson(monthlyWidgetModelJson, MonthlyWidgetModel::class.java)
    }

    override suspend fun updateMonthlyWidgetsByWidgetIds(
        appWidgetIds: List<Int>,
        monthlyWidgetModel: MonthlyWidgetModel
    ) {
        appWidgetIds.forEach { appWidgetId ->
            val glanceId = manager.getGlanceIdBy(appWidgetId)
            updateAppWidgetState(
                context = applicationContext,
                definition = PreferencesGlanceStateDefinition,
                glanceId = glanceId
            ) { preferences ->
                preferences.toMutablePreferences().apply {
                    val monthlyWidgetModelJson = gson.toJson(monthlyWidgetModel)
                    this[DataStoreKey.monthlyWidgetModel] = monthlyWidgetModelJson
                }
            }
            MonthlyWidget().update(applicationContext, glanceId)
        }
    }
}