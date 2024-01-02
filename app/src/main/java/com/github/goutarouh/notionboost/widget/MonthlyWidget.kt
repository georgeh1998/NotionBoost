package com.github.goutarouh.notionboost.widget

import android.content.Context
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.currentState
import androidx.glance.layout.padding
import com.google.gson.Gson

class MonthlyWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            val preferences = currentState<Preferences>()
            val monthlyReportModelJson = preferences[monthlyReportModel]
            val monthlyWidgetUiState = if (monthlyReportModelJson == null) {
                MonthlyWidgetUiState.Loading
            } else {
                val monthlyReportModel = Gson().fromJson(monthlyReportModelJson, MonthlyReportModel::class.java)
                if (monthlyReportModel.isEmpty) {
                    MonthlyWidgetUiState.NoData
                } else {
                    MonthlyWidgetUiState.Success(monthlyReportModel.monthlyReport)
                }
            }
            MonthlyWidgetContent(
                monthlyWidgetUiState = monthlyWidgetUiState,
                modifier = GlanceModifier.padding(8.dp)
            )
        }
    }

    companion object {
        val monthlyReportModel = stringPreferencesKey("monthlyReportModel")
    }
}