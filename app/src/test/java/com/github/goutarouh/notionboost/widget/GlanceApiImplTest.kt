package com.github.goutarouh.notionboost.widget

class FakeGlanceApiImpl : com.github.goutarouh.notionboost.data.glance.GlanceApi {

    lateinit var updatedMonthlyWidgetModel: MonthlyWidgetModel

    override suspend fun updateMonthlyWidgetsByWidgetIds(
        appWidgetIds: List<Int>,
        monthlyWidgetModel: MonthlyWidgetModel
    ) {
        updatedMonthlyWidgetModel = monthlyWidgetModel

    }

}