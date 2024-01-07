package com.github.goutarouh.notionboost.widget.configuration

import android.appwidget.AppWidgetManager
import java.lang.Exception

data class MonthlyWidgetConfigurationUiModel(
    val appWidgetId: Int = AppWidgetManager.INVALID_APPWIDGET_ID,
    val inputDatabaseId: String = "",
    val configurationResult: ConfigurationResult = ConfigurationResult.Initial,

    val updateInputDatabaseId: (String) -> Unit = {},
    val createMonthlyWidget: (
        appWidgetId: Int,
        databaseId: String,
    ) -> Unit = { _, _ -> },
    val clearConfigurationResult: () -> Unit = {},
) {

    val saveButtonEnabled: Boolean
        get() = inputDatabaseId.length == 32


    sealed interface ConfigurationResult {
        data object Initial : ConfigurationResult

        data object Loading : ConfigurationResult

        data object Success : ConfigurationResult

        data class Failure(val e: Exception) : ConfigurationResult
    }

}
