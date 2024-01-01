package com.github.goutarouh.notionboost.widget

import android.content.Context
import android.content.Intent
import androidx.compose.ui.graphics.Color
import androidx.core.net.toUri
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxSize
import androidx.glance.text.Text

class MonthlyWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            Box(
                modifier = GlanceModifier
                    .fillMaxSize()
                    .background(Color.White)
                    .clickable(actionStartActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            "https://www.notion.so/2024-Habit-Tracker-b3ca62aaf06443aa92758511490cf6ce".toUri()
                        )
                    ))
                ,
                contentAlignment = Alignment.Center
            ) {
                Text(text = "MonthlyWidget")
            }
        }
    }
}