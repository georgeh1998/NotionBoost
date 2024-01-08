package com.github.goutarouh.notionboost.ui.welcome

import androidx.activity.ComponentActivity
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
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(AndroidJUnit4::class)
@Config(application = HiltTestApplication::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class WelcomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @OptIn(ExperimentalRoborazziApi::class, InternalRoborazziApi::class)
    @Test
    fun `Capture WelcomeScreen`() = runTest {

        // Act
        composeTestRule.setContent {
            WelcomeScreen(WelcomeUiModel())
        }

        // Assert
        val filePath = RoborazziContext.outputDirectory + "/${WELCOME_SCREEN_TAG}.png"
        composeTestRule.onNodeWithTag(WELCOME_SCREEN_TAG)
            .captureRoboImage(filePath)
    }

}