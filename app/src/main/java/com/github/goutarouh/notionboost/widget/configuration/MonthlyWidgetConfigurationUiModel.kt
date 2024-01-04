package com.github.goutarouh.notionboost.widget.configuration

data class MonthlyWidgetConfigurationUiModel(
    val appWidgetId: Int,
    val inputDatabaseId: String = "",
    val finishConfiguration: Boolean? = null,

    val updateInputDatabaseId: (String) -> Unit,
    val createMonthlyWidget: (
        appWidgetId: Int,
        databaseId: String,
    ) -> Unit,
) {

    val saveButtonEnabled: Boolean
        get() = inputDatabaseId.isNotEmpty()

    val isSuccess: Boolean
        get() = finishConfiguration == true

    val isFailure: Boolean
        get() = finishConfiguration == false

}
