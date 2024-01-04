package com.github.goutarouh.notionboost.widget.configuration

data class MonthlyWidgetConfigurationUiModel(
    val inputDatabaseId: String = "",
    val finishConfiguration: Boolean? = null,

    val updateInputDatabaseId: (String) -> Unit,
    val createMonthlyWidget: () -> Unit,
) {

    val saveButtonEnabled: Boolean
        get() = inputDatabaseId.isNotEmpty()

    val isFinished: Boolean
        get() = finishConfiguration == true
}
