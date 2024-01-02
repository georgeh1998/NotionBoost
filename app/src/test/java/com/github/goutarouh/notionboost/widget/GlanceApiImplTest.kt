package com.github.goutarouh.notionboost.widget

class FakeGlanceApiImpl : GlanceApi {

    lateinit var updatedMonthlyReportModel: MonthlyReportModel

    override suspend fun updateMonthlyReportWidget(monthlyReportModel: MonthlyReportModel) {
        updatedMonthlyReportModel = monthlyReportModel
    }

}