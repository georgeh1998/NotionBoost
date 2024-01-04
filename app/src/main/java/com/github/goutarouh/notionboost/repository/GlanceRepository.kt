package com.github.goutarouh.notionboost.repository

import com.github.goutarouh.notionboost.widget.GlanceApi
import com.github.goutarouh.notionboost.widget.MonthlyWidgetModel
import javax.inject.Inject


class GlanceRepository @Inject constructor(
    private val glanceApi: GlanceApi,
) {

    suspend fun updateMonthlyWidgetByWidgetIds(
        appWidgetIds: List<Int>,
        monthlyWidgetModel: MonthlyWidgetModel
    ) {
        glanceApi.updateMonthlyWidgetsByWidgetIds(appWidgetIds, monthlyWidgetModel)
    }

    suspend fun updateMonthlyWidgetByDatabaseId(
        monthlyWidgetModel: MonthlyWidgetModel
    ) {
        glanceApi.updateMonthlyWidgetsByDatabaseId(monthlyWidgetModel)
    }

}