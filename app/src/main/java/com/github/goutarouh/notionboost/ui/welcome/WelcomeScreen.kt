package com.github.goutarouh.notionboost.ui.welcome

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
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(
    uiModel: WelcomeUiModel,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "Welcome to Notion Boost")
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(value = uiModel.inputApiKey , onValueChange = uiModel.updateInputApiKey)
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedButton(
            onClick = { uiModel.saveApiKey(uiModel.inputApiKey) },
            enabled = uiModel.saveButtonEnabled
        ) {
            Text(text = "Save API Key")
        }
    }
}