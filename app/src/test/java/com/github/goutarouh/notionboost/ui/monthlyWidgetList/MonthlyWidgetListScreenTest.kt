package com.github.goutarouh.notionboost.ui.monthlyWidgetList

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.ExperimentalRoborazziApi
import com.github.takahirom.roborazzi.InternalRoborazziApi
import com.github.takahirom.roborazzi.RoborazziContext
import com.github.takahirom.roborazzi.captureRoboImage
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(AndroidJUnit4::class)
@Config(application = HiltTestApplication::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class MonthlyWidgetListScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @OptIn(ExperimentalRoborazziApi::class, InternalRoborazziApi::class)
    @Test
    fun `Capture MonthlyWidgetListScreen`() = runTest {

        // Act
        composeTestRule.setContent {
            MonthlyWidgetListScreen(MonthlyWidgetListUiModel(
                widgets = listOf(
                    MonthlyWidgetListItemModel(title = "title1"),
                    MonthlyWidgetListItemModel(title = "title2"),
                )
            ))
        }

        // Assert
        val filePath = RoborazziContext.outputDirectory + "/${MONTHLY_WIDGET_LIST_SCREEN_TAG}.png"
        composeTestRule.onNodeWithTag(MONTHLY_WIDGET_LIST_SCREEN_TAG)
            .captureRoboImage(filePath)

    }


}