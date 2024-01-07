package com.github.goutarouh.notionboost.ui.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

const val WELCOME_SCREEN_TAG = "WelcomeScreen"

@Composable
fun WelcomeScreen(
    uiModel: WelcomeUiModel,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .testTag(WELCOME_SCREEN_TAG),
        contentAlignment = Alignment.Center
    ) {
        WelcomeContent(
            uiModel = uiModel,
            modifier = Modifier,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WelcomeContent(
    uiModel: WelcomeUiModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Welcome to Notion Boost",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = 12.dp)
        )
        Text(
            text = "This app enhances Notion management.\n" +
                    "To use, register your Notion Integration Secrets\n",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(vertical = 12.dp)
        )
        OutlinedTextField(
            value = uiModel.inputApiKey,
            onValueChange = uiModel.updateInputApiKey,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            singleLine = true,
            placeholder = { Text(text = "Integration Secret") },
        )
        OutlinedButton(
            onClick = { uiModel.saveApiKey(uiModel.inputApiKey) },
            enabled = uiModel.saveButtonEnabled,
            modifier = Modifier.padding(vertical = 12.dp)
        ) {
            Text(text = "Start using Notion Boost")
        }
    }
}