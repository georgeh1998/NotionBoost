package com.github.goutarouh.notionboost.widget

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.state.PreferencesGlanceStateDefinition
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext

interface GlanceApi {

    suspend fun updateMonthlyWidget(monthlyWidgetModel: MonthlyWidgetModel)

}

class GlanceApiImpl(
    @ApplicationContext private val applicationContext: Context,
    private val gson: Gson,
) : GlanceApi {

    override suspend fun updateMonthlyWidget(monthlyWidgetModel: MonthlyWidgetModel) {
        GlanceAppWidgetManager(applicationContext)
            .getGlanceIds(MonthlyWidget::class.java)
            .forEach { glanceId ->
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
}