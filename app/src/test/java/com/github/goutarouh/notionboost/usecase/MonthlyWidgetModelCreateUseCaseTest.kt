package com.github.goutarouh.notionboost.usecase

import com.github.goutarouh.notionboost.repository.GlanceRepository
import com.github.goutarouh.notionboost.repository.NotionDatabaseRepository
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
            notionDatabaseRepository = NotionDatabaseRepository(
                notionRemoteApi = com.github.goutarouh.notionboost.data.createNotionRemoteApi(
                    queryDatabaseApiModel = MonthlyWidgetModelCreateUseCaseTestData
                        .createDateBordered("title")
                ),
                dataStoreApi = com.github.goutarouh.notionboost.data.datastore.createFakeDataStoreApi(
                    mapOf(0 to "title")
                ),
            ),
            glanceRepository = GlanceRepository(
                glanceApi = fakeGlanceApi
            )
        )

        // Act
        monthlyWidgetModelCreateUseCase.invoke(
            zoneId = ZoneId.of("Asia/Tokyo"),
            now = LocalDateTime.of(2024, 1, 1, 0, 0, 0),
        )

        // Assert
        val updatedMonthlyWidgetModel = fakeGlanceApi.updatedMonthlyWidgetModel
        Assert.assertEquals(0f, updatedMonthlyWidgetModel.mapProgress["title"])
    }

}