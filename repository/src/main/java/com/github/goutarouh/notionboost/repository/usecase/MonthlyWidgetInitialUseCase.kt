package com.github.goutarouh.notionboost.repository.usecase

import android.util.Log
import com.github.goutarouh.notionboost.repository.model.createMonthlyWidgetModel
import com.github.goutarouh.notionboost.repository.repository.GlanceRepository
import com.github.goutarouh.notionboost.repository.repository.NotionDatabaseRepository
import com.github.goutarouh.notionboost.repository.util.getFirstDayOfNextMonth
import com.github.goutarouh.notionboost.repository.util.getLastDayOfPreviousMonth
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
        afterStateUpdate: suspend (Int) -> Unit,
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
            monthlyWidgetModel = monthlyWidgetModel,
            afterStateUpdate = { appWidgetId ->
                afterStateUpdate.invoke(appWidgetId)
            }
        )
    }

}