package com.github.goutarouh.notionboost.widget

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import com.github.goutarouh.notionboost.repository.NotionDatabaseRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MonthlyWidgetReceiver : GlanceAppWidgetReceiver() {

    @Inject
    lateinit var notionDatabaseRepository: NotionDatabaseRepository

    override val glanceAppWidget: GlanceAppWidget
        get() = MonthlyWidget()

}