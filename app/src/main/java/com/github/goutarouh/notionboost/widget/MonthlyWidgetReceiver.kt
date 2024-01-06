package com.github.goutarouh.notionboost.widget

import android.content.Context
import androidx.glance.ExperimentalGlanceApi
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.github.goutarouh.notionboost.repository.NotionDatabaseRepository
import com.github.goutarouh.notionboost.workmanager.MonthlyWidgetUpdateWorker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class MonthlyWidgetReceiver : GlanceAppWidgetReceiver() {

    @Inject lateinit var notionDatabaseRepository: NotionDatabaseRepository

    override val glanceAppWidget: GlanceAppWidget
        get() = glanceMonthlyWidget


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

    // Called when widget is removed every time
    @OptIn(ExperimentalGlanceApi::class)
    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        super.onDeleted(context, appWidgetIds)

        CoroutineScope(coroutineContext).launch {
            withContext(NonCancellable) {
                notionDatabaseRepository.removeMonthlyWidgetConfiguration(appWidgetIds.toList())
            }
        }
    }

}