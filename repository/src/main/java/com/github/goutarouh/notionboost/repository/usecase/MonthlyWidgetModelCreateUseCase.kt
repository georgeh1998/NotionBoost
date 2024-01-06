package com.github.goutarouh.notionboost.repository.usecase

import com.github.goutarouh.notionboost.repository.GlanceRepository
import com.github.goutarouh.notionboost.repository.NotionDatabaseRepository
import com.github.goutarouh.notionboost.repository.model.createMonthlyWidgetModel
import com.github.goutarouh.notionboost.repository.util.getFirstDayOfNextMonth
import com.github.goutarouh.notionboost.repository.util.getLastDayOfPreviousMonth
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

class MonthlyWidgetModelCreateUseCase @Inject constructor(
    private val notionDatabaseRepository: NotionDatabaseRepository,
    private val glanceRepository: GlanceRepository,
) {

    suspend operator fun invoke(
        afterStateUpdate: suspend (Int) -> Unit,
        zoneId: ZoneId = ZoneId.systemDefault(),
        now: LocalDateTime = LocalDateTime.now(zoneId),
    ) {

        val lastDayOfPreviousMonth = now.getLastDayOfPreviousMonth()
        val firstDayOfNextMonth = now.getFirstDayOfNextMonth()

        val map = notionDatabaseRepository.getMonthlyWidgetConfiguration()

        val databaseIds = map.values.toSet()

        databaseIds.forEach { databaseId ->
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
            val appWidgetIds = map.filter {
                it.value == databaseId
            }.keys.toSet()

            glanceRepository.updateMonthlyWidgetByWidgetIds(
                appWidgetIds = appWidgetIds.toList(),
                monthlyWidgetModel = monthlyWidgetModel,
                afterStateUpdate = afterStateUpdate
            )
        }
    }

}