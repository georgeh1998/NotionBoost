package com.github.goutarouh.notionboost.usecase

import com.github.goutarouh.notionboost.repository.GlanceRepository
import com.github.goutarouh.notionboost.repository.NotionDatabaseRepository
import com.github.goutarouh.notionboost.util.getFirstDayOfNextMonth
import com.github.goutarouh.notionboost.util.getLastDayOfPreviousMonth
import com.github.goutarouh.notionboost.widget.createMonthlyWidgetModel
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

class MonthlyWidgetInitialUseCase @Inject constructor(
    private val notionDatabaseRepository: NotionDatabaseRepository,
    private val glanceRepository: GlanceRepository,
) {

    suspend operator fun invoke(
        databaseId: String,
        appWidgetId: Int,
        zoneId: ZoneId = ZoneId.systemDefault(),
        now: LocalDateTime = LocalDateTime.now(zoneId),
    ) {

        val map = mapOf(appWidgetId to databaseId)
        notionDatabaseRepository.saveMonthlyWidgetConfiguration(map)

        val lastDayOfPreviousMonth = now.getLastDayOfPreviousMonth()
        val firstDayOfNextMonth = now.getFirstDayOfNextMonth()

        val queryDatabaseModel = notionDatabaseRepository.queryDatabase(
            databaseId = databaseId,
            now = now,
            inclusiveStartDate = lastDayOfPreviousMonth,
            inclusiveEndDate = firstDayOfNextMonth
        )

        val retrieveDatabaseModel = notionDatabaseRepository.retrieveDatabase(databaseId)

        val userZonFilteredModel = queryDatabaseModel
            .convertToUserZone(zoneId)
            .filterByMonth(now.monthValue)

        val monthlyWidgetModel = createMonthlyWidgetModel(retrieveDatabaseModel, userZonFilteredModel)

        glanceRepository.updateMonthlyWidgetByWidgetIds(
            appWidgetIds = listOf(appWidgetId),
            monthlyWidgetModel = monthlyWidgetModel
        )
    }

}