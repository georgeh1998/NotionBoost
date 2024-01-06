package com.github.goutarouh.notionboost.repository.usecase

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.goutarouh.notionboost.data.api.NotionRemoteApi
import com.github.goutarouh.notionboost.data.api.queryDatabase.QueryDatabaseApiResponseModel
import com.github.goutarouh.notionboost.data.api.queryDatabase.QueryDatabaseApiResponseModel.Result
import com.github.goutarouh.notionboost.data.api.queryDatabase.QueryDatabaseApiResponseModel.Result.Properties
import com.github.goutarouh.notionboost.data.api.queryDatabase.QueryDatabaseApiResponseModel.Result.Properties.CheckBoxProperty
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
class MonthlyWidgetModelCreateUseCaseTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject lateinit var fakeGlanceApi: GlanceApi
    @Inject lateinit var fakeNotionRemoteApi: NotionRemoteApi
    @Inject lateinit var fakeDataStoreApi: DataStoreApi
    @Inject lateinit var monthlyWidgetModelCreateUseCase: MonthlyWidgetModelCreateUseCase

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun `Test if count is correct`() = runTest {

        // Arrange
        val configuration = mapOf(1 to "DATABASE_ID_1",)
        fakeDataStoreApi.setNotionApiKey("NOTION_API_KEY")
        fakeDataStoreApi.saveMonthlyWidgetConfiguration(configuration)
        fakeNotionRemoteApi.setQueryDatabase(QueryDatabaseApiResponseModel(
            results = listOf(
                Result(Properties(
                    checkBoxProperties = mapOf("title" to CheckBoxProperty(true))
                )),
                Result(Properties(
                    checkBoxProperties = mapOf("title" to CheckBoxProperty(false))
                )),
            )
        ))
        fakeNotionRemoteApi.setRetrieveDatabase(RetrieveDatabaseApiResponseModel())

        // Act
        monthlyWidgetModelCreateUseCase.invoke(
            afterStateUpdate = {}
        )

        // Assert
        val state = fakeGlanceApi.getMonthlyWidgetStateByAppWidgetId(1)
        Assert.assertEquals("DATABASE_ID_1", state?.databaseId)
        Assert.assertEquals(0.5f, state?.mapProgress?.get("title"))
    }

}