package com.github.goutarouh.notionboost.usecase

import com.github.goutarouh.notionboost.repository.GlanceRepository
import com.github.goutarouh.notionboost.repository.NotionDatabaseRepository
import com.github.goutarouh.notionboost.util.getFirstDayOfNextMonth
import com.github.goutarouh.notionboost.util.getLastDayOfPreviousMonth
import com.github.goutarouh.notionboost.widget.toMonthlyWidgetModel
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

class MonthlyWidgetInitialUseCase @Inject constructor(
    private val notionDatabaseRepository: NotionDatabaseRepository,
    private val glanceRepository: GlanceRepository,
) {

    suspend operator fun invoke(
        appWidgetIds: List<Int>,
        zoneId: ZoneId = ZoneId.systemDefault(),
        now: LocalDateTime = LocalDateTime.now(zoneId),
    ) {

        val databaseId = notionDatabaseRepository.getDatabaseId()

        val lastDayOfPreviousMonth = now.getLastDayOfPreviousMonth()
        val firstDayOfNextMonth = now.getFirstDayOfNextMonth()

        val queryDatabaseModel = notionDatabaseRepository.queryDatabase(
            databaseId = databaseId,
            now = now,
            inclusiveStartDate = lastDayOfPreviousMonth,
            inclusiveEndDate = firstDayOfNextMonth
        )

        val userZonFilteredModel = queryDatabaseModel
            .convertToUserZone(zoneId)
            .filterByMonth(now.monthValue)

        val monthlyWidgetModel = userZonFilteredModel.toMonthlyWidgetModel()
        glanceRepository.updateMonthlyWidgetByWidgetIds(appWidgetIds, monthlyWidgetModel)

        notionDatabaseRepository.removeDatabaseId()

    }

}