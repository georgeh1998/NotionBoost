package com.github.goutarouh.notionboost.usecase

import com.github.goutarouh.notionboost.data.createNotionRemoteApi
import com.github.goutarouh.notionboost.data.datastore.FakeDataStoreApi
import com.github.goutarouh.notionboost.repository.NotionDatabaseRepositoryImpl
import com.github.goutarouh.notionboost.widget.FakeGlanceApiImpl
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime
import java.time.ZoneId

class MonthlyWidgetModelCreateUseCaseTest {


    private lateinit var monthlyWidgetModelCreateUseCase: MonthlyWidgetModelCreateUseCase
    private lateinit var fakeGlanceApi: FakeGlanceApiImpl

    @Before
    fun setUp() {
      fakeGlanceApi = FakeGlanceApiImpl()
    }

    @Test
    fun `Test if count is correct`() = runTest {

        // Arrange
        monthlyWidgetModelCreateUseCase = MonthlyWidgetModelCreateUseCase(
            notionDatabaseRepository = NotionDatabaseRepositoryImpl(
                notionRemoteApi = createNotionRemoteApi(
                    queryDatabaseApiModel = MonthlyWidgetModelCreateUseCaseTestData
                        .createDateBordered("a")
                ),
                dataStoreApi = FakeDataStoreApi(),
                glanceApi = fakeGlanceApi,
            )
        )

        // Act
        monthlyWidgetModelCreateUseCase.invoke(
            databaseId = "databaseId",
            zoneId = ZoneId.of("Asia/Tokyo"),
            now = LocalDateTime.of(2024, 1, 1, 0, 0, 0),
        )

        // Assert
        val updatedMonthlyWidgetModel = fakeGlanceApi.updatedMonthlyWidgetModel
        Assert.assertEquals(0f, updatedMonthlyWidgetModel.mapProgress["a"])
    }

}