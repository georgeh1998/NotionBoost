package com.github.goutarouh.notionboost.ui.welcome

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.goutarouh.notionboost.data.datastore.DataStoreApi
import com.github.goutarouh.notionboost.ui.main.MainActivity
import com.github.takahirom.roborazzi.captureRoboImage
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import javax.inject.Inject


@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@Config(application = HiltTestApplication::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class WelcomeScreenTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject lateinit var fakeDataStoreApi: DataStoreApi

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun `Test WelcomeScreen`() = runTest {

        // Arrange
        fakeDataStoreApi.setNotionApiKey("")

        // Act
        composeTestRule.waitUntilExactlyOneExists(
            matcher = hasTestTag(WELCOME_SCREEN_TAG),
        )

        // Assert
        composeTestRule.onNodeWithTag(WELCOME_SCREEN_TAG)
            .captureRoboImage()
    }

}