package com.shalom.ethiopicchrono

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class EthiopicDateUtilsTest {

    @Test
    fun testFirstDayOfMonth() {
        val date = EthiopicCalendar.of(2016, 3, 15)
        val firstDay = date.firstDayOfMonth()

        assertEquals(2016, firstDay.year)
        assertEquals(3, firstDay.month)
        assertEquals(1, firstDay.day)
    }

    @Test
    fun testLastDayOfMonth() {
        val date = EthiopicCalendar.of(2016, 3, 15)
        val lastDay = date.lastDayOfMonth()

        assertEquals(2016, lastDay.year)
        assertEquals(3, lastDay.month)
        assertEquals(30, lastDay.day) // Regular month has 30 days
    }

    @Test
    fun testLastDayOfPagumen() {
        val normalYear = EthiopicCalendar.of(2016, 13, 3)
        assertEquals(5, normalYear.lastDayOfMonth().day) // Non-leap year

        val leapYear = EthiopicCalendar.of(2015, 13, 3)
        assertEquals(6, leapYear.lastDayOfMonth().day) // Leap year
    }

    @Test
    fun testFirstAndLastDayOfYear() {
        val date = EthiopicCalendar.of(2016, 5, 20)

        val firstDay = date.firstDayOfYear()
        assertEquals(2016, firstDay.year)
        assertEquals(1, firstDay.month)
        assertEquals(1, firstDay.day)

        val lastDay = date.lastDayOfYear()
        assertEquals(2016, lastDay.year)
        assertEquals(13, lastDay.month)
        assertEquals(5, lastDay.day) // 2016 is not a leap year
    }

    @Test
    fun testDatesInMonth() {
        val dates = EthiopicDateUtils.datesInMonth(2016, 1)
        assertEquals(30, dates.size)
        assertEquals(EthiopicCalendar.of(2016, 1, 1), dates.first())
        assertEquals(EthiopicCalendar.of(2016, 1, 30), dates.last())
    }

    @Test
    fun testDatesInYear() {
        val normalYearDates = EthiopicDateUtils.datesInYear(2016)
        assertEquals(365, normalYearDates.size)

        val leapYearDates = EthiopicDateUtils.datesInYear(2015)
        assertEquals(366, leapYearDates.size)
    }

    @Test
    fun testIsSameMonth() {
        val date1 = EthiopicCalendar.of(2016, 3, 15)
        val date2 = EthiopicCalendar.of(2016, 3, 20)
        val date3 = EthiopicCalendar.of(2016, 4, 15)

        assertTrue(date1.isSameMonth(date2))
        assertFalse(date1.isSameMonth(date3))
    }

    @Test
    fun testIsSameYear() {
        val date1 = EthiopicCalendar.of(2016, 3, 15)
        val date2 = EthiopicCalendar.of(2016, 10, 20)
        val date3 = EthiopicCalendar.of(2017, 3, 15)

        assertTrue(date1.isSameYear(date2))
        assertFalse(date1.isSameYear(date3))
    }

    @Test
    fun testMonthsBetween() {
        val date1 = EthiopicCalendar.of(2016, 1, 15)
        val date2 = EthiopicCalendar.of(2016, 5, 20)

        assertEquals(4, EthiopicDateUtils.monthsBetween(date1, date2))
        assertEquals(-4, EthiopicDateUtils.monthsBetween(date2, date1))
    }

    @Test
    fun testYearsBetween() {
        val date1 = EthiopicCalendar.of(2010, 5, 15)
        val date2 = EthiopicCalendar.of(2016, 3, 20)

        assertEquals(6, EthiopicDateUtils.yearsBetween(date1, date2))
        assertEquals(-6, EthiopicDateUtils.yearsBetween(date2, date1))
    }

    @Test
    fun testLeapYearsInRange() {
        val leapYears = EthiopicDateUtils.leapYearsInRange(2010, 2020)

        // Leap years when year % 4 == 3: 2011, 2015, 2019
        assertEquals(3, leapYears.size)
        assertTrue(2011 in leapYears)
        assertTrue(2015 in leapYears)
        assertTrue(2019 in leapYears)
    }

    @Test
    fun testNextAndPreviousLeapYear() {
        assertEquals(2015, EthiopicDateUtils.nextLeapYear(2014))
        assertEquals(2015, EthiopicDateUtils.previousLeapYear(2016))
    }

    @Test
    fun testCalculateAge() {
        val birthDate = EthiopicCalendar.of(2000, 1, 15)
        val currentDate = EthiopicCalendar.of(2016, 5, 20)

        val age = birthDate.ageAt(currentDate)
        assertEquals(16, age)
    }

    @Test
    fun testCalculateAgeBeforeBirthday() {
        val birthDate = EthiopicCalendar.of(2000, 5, 15)
        val currentDate = EthiopicCalendar.of(2016, 3, 10) // Before birthday

        val age = birthDate.ageAt(currentDate)
        assertEquals(15, age) // Not yet 16
    }

    @Test
    fun testCalculateAgeOnBirthday() {
        val birthDate = EthiopicCalendar.of(2000, 5, 15)
        val currentDate = EthiopicCalendar.of(2016, 5, 15) // Exact birthday

        val age = birthDate.ageAt(currentDate)
        assertEquals(16, age)
    }

    @Test
    fun testNextDayOfMonth() {
        val date = EthiopicCalendar.of(2016, 1, 10)
        val next15th = EthiopicDateUtils.nextDayOfMonth(date, 15)

        assertEquals(2016, next15th.year)
        assertEquals(1, next15th.month)
        assertEquals(15, next15th.day)
    }

    @Test
    fun testNextDayOfMonthWrapsToNextMonth() {
        val date = EthiopicCalendar.of(2016, 1, 20)
        val next15th = EthiopicDateUtils.nextDayOfMonth(date, 15)

        assertEquals(2016, next15th.year)
        assertEquals(2, next15th.month) // Wraps to next month
        assertEquals(15, next15th.day)
    }
}
