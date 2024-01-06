package com.github.goutarouh.notionboost.repository.usecase

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.goutarouh.notionboost.data.api.NotionRemoteApi
import com.github.goutarouh.notionboost.data.api.queryDatabase.QueryDatabaseApiResponseModel
import com.github.goutarouh.notionboost.data.api.retrieveDatabase.RetrieveDatabaseApiResponseModel
import com.github.goutarouh.notionboost.data.datastore.DataStoreApi
import com.github.goutarouh.notionboost.data.glance.GlanceApi
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import javax.inject.Inject

@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(AndroidJUnit4::class)
class MonthlyWidgetInitialUseCaseTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject lateinit var fakeGlanceApi: GlanceApi
    @Inject lateinit var fakeNotionRemoteApi: NotionRemoteApi
    @Inject lateinit var fakeDataStoreApi: DataStoreApi
    @Inject lateinit var monthlyWidgetModelInitialUseCase: MonthlyWidgetInitialUseCase

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun `Test if configuration is saved`() = runTest {

        // Arrange
        fakeDataStoreApi.setNotionApiKey("notionApiKey")
        fakeNotionRemoteApi.setQueryDatabase(
            QueryDatabaseApiResponseModel()
        )
        fakeNotionRemoteApi.setRetrieveDatabase(
            RetrieveDatabaseApiResponseModel()
        )

        // Act
        monthlyWidgetModelInitialUseCase.invoke(
            databaseId = "databaseId",
            appWidgetId = 100,
            afterStateUpdate = {}
        )

        // Assert
        val configuration = fakeDataStoreApi.getMonthlyWidgetConfiguration()
        Assert.assertTrue(configuration.size == 1)
        val databaseId = configuration[100]
        Assert.assertEquals("databaseId", databaseId)
    }

}