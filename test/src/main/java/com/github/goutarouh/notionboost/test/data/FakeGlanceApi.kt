package com.github.goutarouh.notionboost.test.data

import com.github.goutarouh.notionboost.data.glance.GlanceApi
import com.github.goutarouh.notionboost.data.glance.monthly.MonthlyWidgetLocalModel

class FakeGlanceApi : GlanceApi {

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