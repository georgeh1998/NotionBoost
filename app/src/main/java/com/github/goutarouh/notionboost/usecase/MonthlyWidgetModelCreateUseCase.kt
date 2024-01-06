package com.github.goutarouh.notionboost.usecase

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetManager
import com.github.goutarouh.notionboost.widget.glanceMonthlyWidget
import com.github.goutarouh.notionboostrepository.repository.GlanceRepository
import com.github.goutarouh.notionboostrepository.repository.NotionDatabaseRepository
import com.github.goutarouh.notionboostrepository.repository.model.createMonthlyWidgetModel
import com.github.goutarouh.notionboostrepository.repository.util.getFirstDayOfNextMonth
import com.github.goutarouh.notionboostrepository.repository.util.getLastDayOfPreviousMonth
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

class MonthlyWidgetModelCreateUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val notionDatabaseRepository: NotionDatabaseRepository,
    private val glanceRepository: GlanceRepository,
) {

    suspend operator fun invoke(
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
                afterStateUpdate = { appWidgetId ->
                    val glanceId = GlanceAppWidgetManager(context).getGlanceIdBy(appWidgetId)
                    glanceMonthlyWidget.update(context, glanceId)
                }
            )
        }
    }

}