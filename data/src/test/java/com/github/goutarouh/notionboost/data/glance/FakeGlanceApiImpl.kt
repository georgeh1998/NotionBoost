package com.github.goutarouh.notionboost.data.glance

import com.github.goutarouh.notionboost.data.glance.monthly.MonthlyWidgetLocalModel


class FakeGlanceApiImpl : GlanceApi {

    private var monthlyWidgetState: MonthlyWidgetLocalModel? = null

    override suspend fun getMonthlyWidgetStateByAppWidgetId(appWidgetId: Int): MonthlyWidgetLocalModel? {
        return monthlyWidgetState
    }

    override suspend fun updateMonthlyWidgetsStateByWidgetIds(
        appWidgetIds: List<Int>,
        monthlyWidgetLocalModel: MonthlyWidgetLocalModel,
        afterStateUpdate: suspend (appWidgetId: Int) -> Unit
    ) {
        this.monthlyWidgetState = monthlyWidgetLocalModel
    }

}