package com.github.goutarouh.notionboost.usecase

import com.github.goutarouh.notionboost.repository.NotionDatabaseRepository
import com.github.goutarouh.notionboost.widget.toMonthlyReportModel
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class MonthlyReportCreateUseCase @Inject constructor(
    private val notionDatabaseRepository: NotionDatabaseRepository,
) {

    private val utcDateTime = ZoneId.of("UTC")

    suspend operator fun invoke(
        databaseId: String,
        now: LocalDateTime = LocalDateTime.now(utcDateTime),
    ) {

        val inclusiveStartDate = now
            .withDayOfMonth(1)
            .truncatedTo(ChronoUnit.DAYS)

        val exclusiveEndDate = now
            .withDayOfMonth(1)
            .plusMonths(1)
            .truncatedTo(ChronoUnit.DAYS)

        val queryDatabaseModel = notionDatabaseRepository.queryDatabase(
            databaseId = databaseId,
            now = now,
            inclusiveStartDate = inclusiveStartDate,
            exclusiveEndDate = exclusiveEndDate
        )

        val monthlyReportModel = queryDatabaseModel.toMonthlyReportModel()
        notionDatabaseRepository.updateWidget(monthlyReportModel)
    }

}