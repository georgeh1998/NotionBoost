package com.github.goutarouh.notionboost.widget

import android.appwidget.AppWidgetManager
import android.content.Context
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.github.goutarouh.notionboost.workmanager.MonthlyWidgetInitialWorker
import com.github.goutarouh.notionboost.workmanager.MonthlyWidgetUpdateWorker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MonthlyWidgetReceiver : GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget
        get() = MonthlyWidget()

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)


        val myData = workDataOf(
            MonthlyWidgetInitialWorker.APP_WIDGET_IDS_KEY to appWidgetIds
        )

        val initialRequest = OneTimeWorkRequestBuilder<MonthlyWidgetInitialWorker>()
            .setInputData(myData)
            .build()

        WorkManager
            .getInstance(context)
            .enqueue(initialRequest)
    }

    // Called when the widget is added for the first time
    override fun onEnabled(context: Context) {
        super.onEnabled(context)

        val updateRequest = PeriodicWorkRequestBuilder<MonthlyWidgetUpdateWorker>(
            24, TimeUnit.HOURS
        )
            .setInitialDelay(5, TimeUnit.MINUTES)
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