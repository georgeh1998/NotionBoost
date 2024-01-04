package com.github.goutarouh.notionboost.widget.configuration

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.goutarouh.notionboost.ui.theme.NotionBoostTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MonthlyWidgetConfigurationActivity : ComponentActivity() {

    private val viewModel by viewModels<MonthlyWidgetConfigurationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appWidgetId = intent?.extras?.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        ) ?: AppWidgetManager.INVALID_APPWIDGET_ID

        setContent {
            NotionBoostTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val uiModel = viewModel.uiModel.collectAsStateWithLifecycle(
                        lifecycle = lifecycle,
                        minActiveState = Lifecycle.State.STARTED
                    ).value

                    LaunchedEffect(key1 = uiModel) {
                        if (uiModel.isFinished) {
                            finishWithSuccess(appWidgetId)
                        }
                    }

                    MonthlyWidgetConfigurationScreen(
                        uiModel = uiModel,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }

    private fun finishWithSuccess(appWidgetId: Int) {
        val resultValue = Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        setResult(Activity.RESULT_OK, resultValue)
        finish()
    }
}