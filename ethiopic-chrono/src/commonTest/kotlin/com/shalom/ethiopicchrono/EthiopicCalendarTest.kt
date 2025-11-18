package com.shalom.ethiopicchrono

import com.shalom.ethiopicchrono.domain.DateLocale
import com.shalom.ethiopicchrono.domain.EthiopicCalendarConstants
import com.shalom.ethiopicchrono.domain.EthiopicCalendarRules
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class EthiopicCalendarTest {

    @Test
    fun testDateCreation() {
        val date = EthiopicCalendar.of(2016, 3, 15)
        assertNotNull(date)
        assertEquals(2016, date.year)
        assertEquals(3, date.month)
        assertEquals(15, date.day)
    }

    @Test
    fun testLeapYear() {
        // In Ethiopic calendar, leap years are when year % 4 == 3
        assertTrue(EthiopicCalendar.isLeapYear(2015)) // 2015 % 4 == 3
        assertTrue(EthiopicCalendar.isLeapYear(2019)) // 2019 % 4 == 3
        assertTrue(EthiopicCalendar.isLeapYear(2023)) // 2023 % 4 == 3

        assertFalse(EthiopicCalendar.isLeapYear(2016)) // 2016 % 4 == 0
        assertFalse(EthiopicCalendar.isLeapYear(2020)) // 2020 % 4 == 0
    }

    @Test
    fun testDateOperations() {
        val date = EthiopicCalendar.of(2016, 3, 15)

        val nextDay = date.plusDays(1)
        assertEquals(16, nextDay.day)
        assertEquals(3, nextDay.month)

        val nextMonth = date.plusMonths(1)
        assertEquals(4, nextMonth.month)
        assertEquals(2016, nextMonth.year)

        val nextYear = date.plusYears(1)
        assertEquals(2017, nextYear.year)
        assertEquals(3, nextYear.month)
    }

    @Test
    fun testMonthNames() {
        val date = EthiopicCalendar.of(2016, 3, 15)
        assertEquals("Megabit", date.getMonthNameEnglish())
        assertEquals("መጋቢት", date.getMonthNameAmharic())
    }

    @Test
    fun testDayOfYear() {
        val date = EthiopicCalendar.of(2016, 3, 15)
        assertEquals(75, date.getDayOfYear()) // (3-1)*30 + 15 = 60 + 15 = 75
    }

    @Test
    fun testDateFormatting() {
        val date = EthiopicCalendar.of(2016, 3, 15)
        assertEquals("15 Megabit 2016", date.format(DateLocale.ENGLISH))
        assertEquals("15 መጋቢት 2016", date.format(DateLocale.AMHARIC))
    }

    @Test
    fun testFromYearDay() {
        val date = EthiopicCalendar.ofYearDay(2016, 75)
        assertEquals(2016, date.year)
        assertEquals(3, date.month)
        assertEquals(15, date.day)
    }

    @Test
    fun testDaysBetween() {
        val date1 = EthiopicCalendar.of(2016, 1, 1)
        val date2 = EthiopicCalendar.of(2016, 1, 10)
        assertEquals(9, date1.daysUntil(date2))
        assertEquals(-9, date2.daysUntil(date1))
    }

    @Test
    fun testPagumenMonth() {
        // Pagumen (13th month) has 5 days in normal year, 6 in leap year
        val normalYear = EthiopicCalendar.of(2016, 13, 5) // Valid
        assertEquals(13, normalYear.month)

        val leapYear = EthiopicCalendar.of(2015, 13, 6) // Valid in leap year
        assertEquals(13, leapYear.month)
        assertTrue(leapYear.isInLeapYear())
    }

    @Test
    fun testCalendarConstants() {
        assertEquals(13, EthiopicCalendarConstants.MONTHS_PER_YEAR)
        assertEquals(30, EthiopicCalendarConstants.DAYS_PER_REGULAR_MONTH)
        assertEquals(5, EthiopicCalendarConstants.PAGUMEN_DAYS_NORMAL)
        assertEquals(6, EthiopicCalendarConstants.PAGUMEN_DAYS_LEAP)
        assertEquals(365, EthiopicCalendarConstants.DAYS_PER_YEAR_NORMAL)
        assertEquals(366, EthiopicCalendarConstants.DAYS_PER_YEAR_LEAP)
    }

    @Test
    fun testCalendarRules() {
        // Test days in month
        assertEquals(30, EthiopicCalendarRules.daysInMonth(2016, 1))
        assertEquals(30, EthiopicCalendarRules.daysInMonth(2016, 12))
        assertEquals(5, EthiopicCalendarRules.daysInMonth(2016, 13)) // Non-leap year
        assertEquals(6, EthiopicCalendarRules.daysInMonth(2015, 13)) // Leap year

        // Test days in year
        assertEquals(365, EthiopicCalendarRules.daysInYear(2016))
        assertEquals(366, EthiopicCalendarRules.daysInYear(2015))
    }
}
