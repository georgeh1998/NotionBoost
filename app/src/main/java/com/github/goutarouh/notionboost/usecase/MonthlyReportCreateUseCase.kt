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

    private val utcZoneId = ZoneId.of("UTC")
    private val userZoneId = ZoneId.systemDefault()

    suspend operator fun invoke(
        databaseId: String,
        now: LocalDateTime = LocalDateTime.now(ZoneId.systemDefault()),
    ) {

        val lastDayOfPreviousMonth = now
            .withDayOfMonth(1)
            .minusDays(1)
            .truncatedTo(ChronoUnit.DAYS)

        val firstDayOfNextMonth = now
            .withDayOfMonth(1)
            .plusMonths(1)
            .truncatedTo(ChronoUnit.DAYS)

        val queryDatabaseModel = notionDatabaseRepository.queryDatabase(
            databaseId = databaseId,
            now = now,
            inclusiveStartDate = lastDayOfPreviousMonth,
            inclusiveEndDate = firstDayOfNextMonth
        )

        val userZoneFilteredModel = queryDatabaseModel.copy(
            dailyInfoList = queryDatabaseModel.dailyInfoList.map {
                it.copy(
                    createdTime = it.createdTime.atZone(utcZoneId).withZoneSameInstant(userZoneId).toLocalDateTime()
                )
            }.filter {
                it.createdTime.monthValue == now.monthValue
            }
        )

        val monthlyReportModel = userZoneFilteredModel.toMonthlyReportModel()
        notionDatabaseRepository.updateWidget(monthlyReportModel)
    }

}