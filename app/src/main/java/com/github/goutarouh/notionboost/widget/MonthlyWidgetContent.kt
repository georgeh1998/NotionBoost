package com.github.goutarouh.notionboost.widget

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.core.net.toUri
import androidx.glance.GlanceModifier
import androidx.glance.action.clickable
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.background
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.text.Text
import com.github.goutarouh.notionboost.repository.QueryDatabaseModel

@Composable
fun MonthlyWidgetContent(
    monthlyInfo: QueryDatabaseModel
) {
    Column(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(Color.White)
            .clickable(
                actionStartActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        "https://www.notion.so/2024-Habit-Tracker-b3ca62aaf06443aa92758511490cf6ce".toUri()
                    )
                )
            ),
    ) {
        val countDays = monthlyInfo.dailyInfo.size
        val countDoneReading = monthlyInfo.dailyInfo.filter { it.doneReading }.size
        val countDoneEnglishLearning = monthlyInfo.dailyInfo.filter { it.doneEnglishLearning }.size

        if (countDays == 0) {
            Text(text = "No Data")
        } else {
            Text(text = "Reading: ${countDoneReading}/${countDays}")
            Text(text = "EnglishLearning: ${countDoneEnglishLearning}/${countDays}")
        }
    }
}



