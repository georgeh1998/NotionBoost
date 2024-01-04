package com.github.goutarouh.notionboost.workmanager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.github.goutarouh.notionboost.usecase.MonthlyWidgetModelCreateUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class MonthlyWidgetUpdateWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val monthlyWidgetModelCreateWorkerUseCase: MonthlyWidgetModelCreateUseCase,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        monthlyWidgetModelCreateWorkerUseCase.invoke(
            databaseId = "f59002096e874781aa3a659e78aa46d6"
        )
        return Result.success()
    }


    companion object {
        const val EXECUTION_UNIQUE_ID = "EXECUTION_MONTHLY_REPORT_CREATE_WORKER"
    }
}