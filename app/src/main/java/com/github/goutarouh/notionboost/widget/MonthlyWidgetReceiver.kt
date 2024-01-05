package com.github.goutarouh.notionboost.widget

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.github.goutarouh.notionboost.workmanager.MonthlyWidgetUpdateWorker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MonthlyWidgetReceiver : GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget
        get() = MonthlyWidget()


    // Called when the widget is added for the first time
    override fun onEnabled(context: Context) {
        super.onEnabled(context)

        val updateRequest = PeriodicWorkRequestBuilder<MonthlyWidgetUpdateWorker>(
            24, TimeUnit.HOURS
        )
            .setInitialDelay(24, TimeUnit.HOURS)
            .build()

        WorkManager
            .getInstance(context)
            .enqueueUniquePeriodicWork(
                MonthlyWidgetUpdateWorker.EXECUTION_UNIQUE_ID,
                ExistingPeriodicWorkPolicy.UPDATE,
                updateRequest
            )
    }

    // Called when the widget is removed for the last time
    override fun onDisabled(context: Context) {
        super.onDisabled(context)
        WorkManager
            .getInstance(context)
            .cancelUniqueWork(MonthlyWidgetUpdateWorker.EXECUTION_UNIQUE_ID)
    }

}