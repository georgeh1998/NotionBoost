package com.github.goutarouh.notionboost.widget.configuration

import androidx.activity.ComponentActivity
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.goutarouh.notionboost.ui.monthlyWidgetList.MONTHLY_WIDGET_LIST_SCREEN_TAG
import com.github.takahirom.roborazzi.ExperimentalRoborazziApi
import com.github.takahirom.roborazzi.InternalRoborazziApi
import com.github.takahirom.roborazzi.RoborazziContext
import com.github.takahirom.roborazzi.captureRoboImage
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(AndroidJUnit4::class)
@Config(application = HiltTestApplication::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class MonthlyWidgetConfigurationScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @OptIn(InternalRoborazziApi::class, ExperimentalRoborazziApi::class)
    @Test
    fun `Capture MonthlyWidgetConfigurationScreen`() = runTest {

        // Act
        composeTestRule.setContent {
            val hostState = remember { SnackbarHostState() }
            MonthlyWidgetConfigurationScreen(
                uiModel = MonthlyWidgetConfigurationUiModel(),
                hostState = hostState
            )
        }

        // Assert
        val filePath = RoborazziContext.outputDirectory + "/${MONTHLY_WIDGET_CONFIGURATION_SCREEN_TAG}.png"
        composeTestRule.onNodeWithTag(MONTHLY_WIDGET_CONFIGURATION_SCREEN_TAG)
            .captureRoboImage(filePath)
    }

}
