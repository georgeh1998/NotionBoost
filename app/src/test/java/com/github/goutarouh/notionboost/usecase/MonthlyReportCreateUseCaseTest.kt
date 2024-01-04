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

class MonthlyReportCreateUseCaseTest {


    private lateinit var monthlyReportCreateUseCase: MonthlyReportCreateUseCase
    private lateinit var fakeGlanceApi: FakeGlanceApiImpl

    @Before
    fun setUp() {
      fakeGlanceApi = FakeGlanceApiImpl()
    }

    @Test
    fun `Test if count is correct`() = runTest {

        // Arrange
        monthlyReportCreateUseCase = MonthlyReportCreateUseCase(
            notionDatabaseRepository = NotionDatabaseRepositoryImpl(
                notionRemoteApi = createNotionRemoteApi(
                    queryDatabaseApiModel = MonthlyReportCreateUseCaseTestData
                        .createDateBordered("a")
                ),
                dataStoreApi = FakeDataStoreApi(),
                glanceApi = fakeGlanceApi,
            )
        )

        // Act
        monthlyReportCreateUseCase.invoke(
            databaseId = "databaseId",
            zoneId = ZoneId.of("Asia/Tokyo"),
            now = LocalDateTime.of(2024, 1, 1, 0, 0, 0),
        )

        // Assert
        val updatedMonthlyReportModel = fakeGlanceApi.updatedMonthlyReportModel
        val monthlyReport = updatedMonthlyReportModel.monthlyReport
        Assert.assertEquals(0f, monthlyReport.mapProgress["a"])
    }

}