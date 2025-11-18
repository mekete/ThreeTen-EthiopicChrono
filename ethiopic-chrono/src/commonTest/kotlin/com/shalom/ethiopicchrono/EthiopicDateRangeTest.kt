package com.shalom.ethiopicchrono

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class EthiopicDateRangeTest {

    @Test
    fun testRangeCreation() {
        val start = EthiopicCalendar.of(2016, 1, 1)
        val end = EthiopicCalendar.of(2016, 1, 10)
        val range = start..end

        assertEquals(start, range.start)
        assertEquals(end, range.endInclusive)
    }

    @Test
    fun testRangeIteration() {
        val start = EthiopicCalendar.of(2016, 1, 1)
        val end = EthiopicCalendar.of(2016, 1, 5)
        val range = start..end

        val dates = range.toList()
        assertEquals(5, dates.size)
        assertEquals(EthiopicCalendar.of(2016, 1, 1), dates[0])
        assertEquals(EthiopicCalendar.of(2016, 1, 2), dates[1])
        assertEquals(EthiopicCalendar.of(2016, 1, 3), dates[2])
        assertEquals(EthiopicCalendar.of(2016, 1, 4), dates[3])
        assertEquals(EthiopicCalendar.of(2016, 1, 5), dates[4])
    }

    @Test
    fun testRangeLength() {
        val start = EthiopicCalendar.of(2016, 1, 1)
        val end = EthiopicCalendar.of(2016, 1, 10)
        val range = start..end

        assertEquals(10, range.lengthInDays())
    }

    @Test
    fun testRangeContains() {
        val start = EthiopicCalendar.of(2016, 1, 5)
        val end = EthiopicCalendar.of(2016, 1, 15)
        val range = start..end

        assertTrue(EthiopicCalendar.of(2016, 1, 10) in range)
        assertFalse(EthiopicCalendar.of(2016, 1, 1) in range)
        assertFalse(EthiopicCalendar.of(2016, 1, 20) in range)
    }

    @Test
    fun testRangeWithStep() {
        val start = EthiopicCalendar.of(2016, 1, 1)
        val end = EthiopicCalendar.of(2016, 1, 10)
        val range = start..end

        val dates = (range step 3).toList()
        assertEquals(4, dates.size) // Days 1, 4, 7, 10
        assertEquals(EthiopicCalendar.of(2016, 1, 1), dates[0])
        assertEquals(EthiopicCalendar.of(2016, 1, 4), dates[1])
        assertEquals(EthiopicCalendar.of(2016, 1, 7), dates[2])
        assertEquals(EthiopicCalendar.of(2016, 1, 10), dates[3])
    }

    @Test
    fun testRangeAcrossMonths() {
        val start = EthiopicCalendar.of(2016, 1, 28)
        val end = EthiopicCalendar.of(2016, 2, 3)
        val range = start..end

        val dates = range.toList()
        assertEquals(6, dates.size) // Days 28, 29, 30 of month 1, and days 1, 2, 3 of month 2
    }

    @Test
    fun testComparison() {
        val date1 = EthiopicCalendar.of(2016, 1, 1)
        val date2 = EthiopicCalendar.of(2016, 1, 10)
        val date3 = EthiopicCalendar.of(2016, 1, 1)

        assertTrue(date1 < date2)
        assertTrue(date2 > date1)
        assertTrue(date1 <= date3)
        assertTrue(date1 >= date3)
        assertFalse(date1 > date2)
    }
}
