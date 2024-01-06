package com.github.goutarouh.notionboost.widget

import android.content.Context
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.Preferences
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.currentState
import androidx.glance.layout.padding
import com.github.goutarouh.notionboostrepository.repository.GlanceKey
import com.github.goutarouh.notionboostrepository.repository.model.MonthlyWidgetModel

import com.google.gson.Gson

val glanceMonthlyWidget = object : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            val preferences = currentState<Preferences>()
            val monthlyWidgetModelJson = preferences[GlanceKey.monthlyWidgetKey]
            val monthlyWidgetUiState = if (monthlyWidgetModelJson == null) {
                MonthlyWidgetUiState.Preparing
            } else {
                val monthlyWidgetModel = Gson().fromJson(monthlyWidgetModelJson, MonthlyWidgetModel::class.java)
                MonthlyWidgetUiState.Success(monthlyWidgetModel)
            }
            MonthlyWidgetContent(
                monthlyWidgetUiState = monthlyWidgetUiState,
                modifier = GlanceModifier.padding(8.dp)
            )
        }
    }
}