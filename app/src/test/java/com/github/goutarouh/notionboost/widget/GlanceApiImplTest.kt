package com.github.goutarouh.notionboost.widget

class FakeGlanceApiImpl : GlanceApi {

    lateinit var updatedMonthlyWidgetModel: MonthlyWidgetModel

    override suspend fun updateMonthlyWidget(monthlyWidgetModel: MonthlyWidgetModel) {
        updatedMonthlyWidgetModel = monthlyWidgetModel
    }

}