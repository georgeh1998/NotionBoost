package com.github.goutarouh.notionboost.widget.configuration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthlyWidgetConfigurationScreen(
    uiModel: MonthlyWidgetConfigurationUiModel,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(value = uiModel.inputDatabaseId , onValueChange = uiModel.updateInputDatabaseId)
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedButton(
                onClick = { uiModel.createMonthlyWidget(uiModel.appWidgetId, uiModel.inputDatabaseId) },
                enabled = uiModel.saveButtonEnabled
            ) {
                Text(text = "Create Monthly Widget")
            }
        }
    }
}