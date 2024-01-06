package com.github.goutarouh.notionboost.util

import com.github.goutarouh.notionboostrepository.repository.util.getFirstDayOfNextMonth
import com.github.goutarouh.notionboostrepository.repository.util.getFirstDayOfThisMonth
import com.github.goutarouh.notionboostrepository.repository.util.getLastDayOfPreviousMonth
import com.github.goutarouh.notionboostrepository.repository.util.getLastDayOfThisMonth
import org.junit.Assert
import org.junit.Test
import java.time.LocalDateTime


class DateUtilKtTest {

    @Test
    fun getFirstDayOfThisMonth() {

        // Arrange
        val dayList = listOf(
            LocalDateTime.of(2024, 1, 31, 23, 59),
            LocalDateTime.of(2024, 2, 1, 0, 0),
            LocalDateTime.of(2024, 2, 1, 0, 1),
        )
        val expectedList = listOf(
            LocalDateTime.of(2024, 1, 1, 0, 0),
            LocalDateTime.of(2024, 2, 1, 0, 0),
            LocalDateTime.of(2024, 2, 1, 0, 0),
        )

        // Act
        val results = dayList.map { it.getFirstDayOfThisMonth() }

        // Assert
        expectedList.zip(results).forEach { (expected, result) ->
            Assert.assertEquals(expected, result)
        }

    }

    @Test
    fun getLastDayOfThisMonth() {

            // Arrange
            val dayList = listOf(
                LocalDateTime.of(2024, 1, 31, 23, 59),
                LocalDateTime.of(2024, 2, 1, 0, 0),
                LocalDateTime.of(2024, 2, 1, 0, 1),
            )
            val expectedList = listOf(
                LocalDateTime.of(2024, 1, 31, 0, 0),
                LocalDateTime.of(2024, 2, 29, 0, 0),
                LocalDateTime.of(2024, 2, 29, 0, 0),
            )

            // Act
            val results = dayList.map { it.getLastDayOfThisMonth() }

            // Assert
            expectedList.zip(results).forEach { (expected, result) ->
                Assert.assertEquals(expected, result)
            }
    }

    @Test
    fun getLastDayOfPreviousMonth() {

        // Arrange
        val dayList = listOf(
            LocalDateTime.of(2024, 1, 31, 23, 59),
            LocalDateTime.of(2024, 2, 1, 0, 0),
            LocalDateTime.of(2024, 2, 1, 0, 1),
        )
        val expectedList = listOf(
            LocalDateTime.of(2023, 12, 31, 0, 0),
            LocalDateTime.of(2024, 1, 31, 0, 0),
            LocalDateTime.of(2024, 1, 31, 0, 0),
        )

        // Act
        val results = dayList.map { it.getLastDayOfPreviousMonth() }

        // Assert
        expectedList.zip(results).forEach { (expected, result) ->
            Assert.assertEquals(expected, result)
        }

    }

    @Test
    fun getFirstDayOfNextMonth() {

        // Arrange
        val dayList = listOf(
            LocalDateTime.of(2024, 1, 31, 23, 59),
            LocalDateTime.of(2024, 2, 1, 0, 0),
            LocalDateTime.of(2024, 2, 1, 0, 1),
        )
        val expectedList = listOf(
            LocalDateTime.of(2024, 2, 1, 0, 0),
            LocalDateTime.of(2024, 3, 1, 0, 0),
            LocalDateTime.of(2024, 3, 1, 0, 0),
        )

        // Act
        val results = dayList.map { it.getFirstDayOfNextMonth() }

        // Assert
        expectedList.zip(results).forEach { (expected, result) ->
            Assert.assertEquals(expected, result)
        }

    }

}