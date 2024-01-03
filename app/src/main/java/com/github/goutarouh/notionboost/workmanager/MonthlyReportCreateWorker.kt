package com.github.goutarouh.notionboost.workmanager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.github.goutarouh.notionboost.usecase.MonthlyReportCreateUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class MonthlyReportCreateWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val monthlyReportCreateWorkerUseCase: MonthlyReportCreateUseCase,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        monthlyReportCreateWorkerUseCase.invoke(
            databaseId = "f59002096e874781aa3a659e78aa46d6"
        )
        return Result.success()
    }


    companion object {
        const val EXECUTION_UNIQUE_ID = "EXECUTION_MONTHLY_REPORT_CREATE_WORKER"
    }
}