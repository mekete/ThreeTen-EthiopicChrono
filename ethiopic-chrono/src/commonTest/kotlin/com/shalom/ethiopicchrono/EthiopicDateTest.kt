package com.shalom.ethiopicchrono

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class EthiopicDateTest {

    @Test
    fun testDateCreation() {
        val date = EthiopicDate.of(2016, 3, 15)
        assertNotNull(date)
        assertEquals(2016, date.getLong(java.time.temporal.ChronoField.YEAR))
        assertEquals(3, date.getLong(java.time.temporal.ChronoField.MONTH_OF_YEAR))
        assertEquals(15, date.getLong(java.time.temporal.ChronoField.DAY_OF_MONTH))
    }

    @Test
    fun testChronology() {
        val chrono = EthiopicChronology.INSTANCE
        assertNotNull(chrono)
        assertEquals("Ethiopic", chrono.id)
    }

    @Test
    fun testLeapYear() {
        val chrono = EthiopicChronology.INSTANCE
        // In Ethiopic calendar, leap years are when year % 4 == 3
        assertTrue(chrono.isLeapYear(2015)) // 2015 % 4 == 3
        assertTrue(chrono.isLeapYear(2019)) // 2019 % 4 == 3
    }

    @Test
    fun testEra() {
        val era = EthiopicEra.INCARNATION
        assertNotNull(era)
        assertEquals("INCARNATION", era.name)
    }

    @Test
    fun testDateOperations() {
        val date = EthiopicDate.of(2016, 3, 15)
        val nextDay = date.plusDays(1)
        assertEquals(16, nextDay.getLong(java.time.temporal.ChronoField.DAY_OF_MONTH))

        val nextMonth = date.plusMonths(1)
        assertEquals(4, nextMonth.getLong(java.time.temporal.ChronoField.MONTH_OF_YEAR))

        val nextYear = date.plusYears(1)
        assertEquals(2017, nextYear.getLong(java.time.temporal.ChronoField.YEAR))
    }
}
