package com.github.goutarouh.notionboost.data.glance

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.getAppWidgetState
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.state.PreferencesGlanceStateDefinition
import com.github.goutarouh.notionboost.data.datastore.DataStoreKey
import com.github.goutarouh.notionboost.data.glance.monthly.MonthlyWidgetLocalModel
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext

interface GlanceApi {

    suspend fun getMonthlyWidgetStateByAppWidgetId(appWidgetId: Int) : MonthlyWidgetLocalModel?

    suspend fun updateMonthlyWidgetsStateByWidgetIds(
        appWidgetIds: List<Int>,
        monthlyWidgetLocalModel: MonthlyWidgetLocalModel,
        afterStateUpdate: suspend (appWidgetId: Int) -> Unit = {},
    )
}

class GlanceApiImpl(
    @ApplicationContext private val applicationContext: Context,
    private val gson: Gson,
) : GlanceApi {

    private val manager = GlanceAppWidgetManager(applicationContext)

    override suspend fun getMonthlyWidgetStateByAppWidgetId(
        appWidgetId: Int
    ) : MonthlyWidgetLocalModel? {
        val glanceId = manager.getGlanceIdBy(appWidgetId)
        val monthlyWidgetLocalModel = getAppWidgetState(
            context = applicationContext,
            definition = PreferencesGlanceStateDefinition,
            glanceId = glanceId
        )[DataStoreKey.monthlyWidgetKey] ?: return null
        return gson.fromJson(monthlyWidgetLocalModel, MonthlyWidgetLocalModel::class.java)
    }

    override suspend fun updateMonthlyWidgetsStateByWidgetIds(
        appWidgetIds: List<Int>,
        monthlyWidgetLocalModel: MonthlyWidgetLocalModel,
        afterStateUpdate: suspend (appWidgetId: Int) -> Unit,
    ) {
        appWidgetIds.forEach { appWidgetId ->
            val glanceId = manager.getGlanceIdBy(appWidgetId)
            updateAppWidgetState(
                context = applicationContext,
                definition = PreferencesGlanceStateDefinition,
                glanceId = glanceId
            ) { preferences ->
                preferences.toMutablePreferences().apply {
                    val monthlyWidgetModelJson = gson.toJson(monthlyWidgetLocalModel)
                    this[DataStoreKey.monthlyWidgetKey] = monthlyWidgetModelJson
                }
            }
            afterStateUpdate(appWidgetId)
        }
    }
}