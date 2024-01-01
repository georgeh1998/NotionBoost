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
import com.github.goutarouh.notionboost.repository.QueryDatabaseModel
import com.google.gson.Gson

class MonthlyWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            val preferences = currentState<Preferences>()
            val monthlyInfoJson = preferences[monthlyInfo]
            val monthlyInfo = if (monthlyInfoJson == null) {
                QueryDatabaseModel(listOf())
            } else {
                Gson().fromJson(monthlyInfoJson, QueryDatabaseModel::class.java)
            }
            MonthlyWidgetContent(
                monthlyInfo = monthlyInfo,
                modifier = GlanceModifier.padding(8.dp)
            )
        }
    }

    companion object {
        val monthlyInfo = stringPreferencesKey("monthlyInfo")
    }
}