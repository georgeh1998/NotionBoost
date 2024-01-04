package com.github.goutarouh.notionboost.workmanager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.github.goutarouh.notionboost.usecase.MonthlyWidgetInitialUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class MonthlyWidgetInitialWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val monthlyWidgetInitialUseCase: MonthlyWidgetInitialUseCase,
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        val appWidgetId = inputData.getIntArray(APP_WIDGET_IDS_KEY) ?: return Result.failure()
        monthlyWidgetInitialUseCase.invoke(
            appWidgetIds = appWidgetId.toList()
        )
        return Result.success()
    }

    companion object {
        const val APP_WIDGET_IDS_KEY = "APP_WIDGET_IDS_KEY"
    }

}