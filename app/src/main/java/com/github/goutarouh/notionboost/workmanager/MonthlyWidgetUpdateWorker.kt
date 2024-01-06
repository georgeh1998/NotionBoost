package com.github.goutarouh.notionboost.workmanager

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.github.goutarouh.notionboost.repository.usecase.MonthlyWidgetModelCreateUseCase
import com.github.goutarouh.notionboost.widget.glanceMonthlyWidget
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class MonthlyWidgetUpdateWorker @AssistedInject constructor(
    @Assisted val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val monthlyWidgetModelCreateWorkerUseCase: MonthlyWidgetModelCreateUseCase,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        monthlyWidgetModelCreateWorkerUseCase.invoke(
            afterStateUpdate = { appWidgetId ->
                val glanceId = GlanceAppWidgetManager(appContext).getGlanceIdBy(appWidgetId)
                glanceMonthlyWidget.update(appContext, glanceId)
            }
        )
        return Result.success()
    }


    companion object {
        const val EXECUTION_UNIQUE_ID = "EXECUTION_MONTHLY_REPORT_CREATE_WORKER"
    }
}