package com.github.goutarouh.notionboost.widget

import com.github.goutarouh.notionboost.repository.createDailyInfo
import com.github.goutarouh.notionboost.repository.createQueryDatabaseModel
import org.junit.Assert
import org.junit.Test

class MonthlyWidgetModelKtTest {

    @Test
    fun `Test if the summary is correct`() {

        // Arrange
        val sut = createQueryDatabaseModel(dailyInfoList = listOf(
            createDailyInfo(isDoneMap = mapOf("a" to true, "b" to true, "c" to true, "d" to false)),
            createDailyInfo(isDoneMap = mapOf("a" to true, "b" to true, "c" to false, "d" to false)),
            createDailyInfo(isDoneMap = mapOf("a" to true, "b" to false, "c" to false, "d" to false)),
        ))

        // Act
        val result = sut.toMonthlyReportModel()

        // Assert
        val mapProgress = result.monthlyReport.mapProgress
        Assert.assertEquals(3 / 3f, mapProgress["a"])
        Assert.assertEquals(2f / 3f, mapProgress["b"])
        Assert.assertEquals(1f / 3f, mapProgress["c"])
        Assert.assertEquals(0f / 3f, mapProgress["d"])
    }

    @Test
    fun `Test if mapProgress is empty`() {

        // Arrange
        val sut = createQueryDatabaseModel()

        // Act
        val result = sut.toMonthlyReportModel()

        // Assert
        val mapProgress = result.monthlyReport.mapProgress
        Assert.assertEquals(0, mapProgress.size)
    }

}