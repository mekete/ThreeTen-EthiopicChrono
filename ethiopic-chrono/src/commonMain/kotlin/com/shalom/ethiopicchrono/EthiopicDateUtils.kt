package com.shalom.ethiopicchrono

import com.shalom.ethiopicchrono.domain.EthiopicCalendarConstants
import com.shalom.ethiopicchrono.domain.EthiopicCalendarRules

/**
 * Utility functions for working with Ethiopic calendar dates.
 */
object EthiopicDateUtils {

    /**
     * Returns the first day of the month for a given date.
     */
    fun firstDayOfMonth(date: EthiopicCalendar): EthiopicCalendar {
        return EthiopicCalendar.of(date.year, date.month, 1)
    }

    /**
     * Returns the last day of the month for a given date.
     */
    fun lastDayOfMonth(date: EthiopicCalendar): EthiopicCalendar {
        val daysInMonth = EthiopicCalendarRules.daysInMonth(date.year, date.month)
        return EthiopicCalendar.of(date.year, date.month, daysInMonth)
    }

    /**
     * Returns the first day of the year for a given date.
     */
    fun firstDayOfYear(date: EthiopicCalendar): EthiopicCalendar {
        return EthiopicCalendar.of(date.year, 1, 1)
    }

    /**
     * Returns the last day of the year for a given date.
     */
    fun lastDayOfYear(date: EthiopicCalendar): EthiopicCalendar {
        val lastMonth = EthiopicCalendarConstants.MONTHS_PER_YEAR
        val daysInLastMonth = EthiopicCalendarRules.daysInMonth(date.year, lastMonth)
        return EthiopicCalendar.of(date.year, lastMonth, daysInLastMonth)
    }

    /**
     * Returns all dates in a given month.
     */
    fun datesInMonth(year: Int, month: Int): List<EthiopicCalendar> {
        val daysInMonth = EthiopicCalendarRules.daysInMonth(year, month)
        return (1..daysInMonth).map { day ->
            EthiopicCalendar.of(year, month, day)
        }
    }

    /**
     * Returns all dates in a given year.
     */
    fun datesInYear(year: Int): List<EthiopicCalendar> {
        return (1..EthiopicCalendarConstants.MONTHS_PER_YEAR).flatMap { month ->
            datesInMonth(year, month)
        }
    }

    /**
     * Checks if two dates are in the same month.
     */
    fun isSameMonth(date1: EthiopicCalendar, date2: EthiopicCalendar): Boolean {
        return date1.year == date2.year && date1.month == date2.month
    }

    /**
     * Checks if two dates are in the same year.
     */
    fun isSameYear(date1: EthiopicCalendar, date2: EthiopicCalendar): Boolean {
        return date1.year == date2.year
    }

    /**
     * Returns the number of months between two dates.
     * Result is positive if date2 is after date1.
     */
    fun monthsBetween(date1: EthiopicCalendar, date2: EthiopicCalendar): Int {
        val yearDiff = date2.year - date1.year
        val monthDiff = date2.month - date1.month
        return yearDiff * EthiopicCalendarConstants.MONTHS_PER_YEAR + monthDiff
    }

    /**
     * Returns the number of years between two dates.
     * Result is positive if date2 is after date1.
     */
    fun yearsBetween(date1: EthiopicCalendar, date2: EthiopicCalendar): Int {
        return date2.year - date1.year
    }

    /**
     * Returns all leap years in a given range.
     */
    fun leapYearsInRange(startYear: Int, endYear: Int): List<Int> {
        return (startYear..endYear).filter { year ->
            EthiopicCalendarRules.isLeapYear(year)
        }
    }

    /**
     * Returns the next leap year after the given year.
     */
    fun nextLeapYear(year: Int): Int {
        var nextYear = year + 1
        while (!EthiopicCalendarRules.isLeapYear(nextYear)) {
            nextYear++
        }
        return nextYear
    }

    /**
     * Returns the previous leap year before the given year.
     */
    fun previousLeapYear(year: Int): Int {
        var prevYear = year - 1
        while (!EthiopicCalendarRules.isLeapYear(prevYear)) {
            prevYear--
        }
        return prevYear
    }

    /**
     * Calculates the age in years given a birth date and a current date.
     * Returns the number of complete years.
     */
    fun calculateAge(birthDate: EthiopicCalendar, currentDate: EthiopicCalendar): Int {
        var age = currentDate.year - birthDate.year

        // Adjust if birthday hasn't occurred yet this year
        if (currentDate.month < birthDate.month ||
            (currentDate.month == birthDate.month && currentDate.day < birthDate.day)) {
            age--
        }

        return age
    }

    /**
     * Returns the next occurrence of a specific day of month after the given date.
     * For example, next occurrence of the 15th day of any month.
     */
    fun nextDayOfMonth(date: EthiopicCalendar, targetDay: Int): EthiopicCalendar {
        require(targetDay in 1..30) { "Target day must be between 1 and 30" }

        // Check if we can use the current month
        val daysInCurrentMonth = EthiopicCalendarRules.daysInMonth(date.year, date.month)
        if (date.day < targetDay && targetDay <= daysInCurrentMonth) {
            return EthiopicCalendar.of(date.year, date.month, targetDay)
        }

        // Try next month
        var checkDate = date.plusMonths(1)
        while (EthiopicCalendarRules.daysInMonth(checkDate.year, checkDate.month) < targetDay) {
            checkDate = checkDate.plusMonths(1)
        }

        return EthiopicCalendar.of(checkDate.year, checkDate.month, targetDay)
    }
}

/**
 * Extension functions for convenient date operations.
 */

/**
 * Returns the first day of this date's month.
 */
fun EthiopicCalendar.firstDayOfMonth(): EthiopicCalendar {
    return EthiopicDateUtils.firstDayOfMonth(this)
}

/**
 * Returns the last day of this date's month.
 */
fun EthiopicCalendar.lastDayOfMonth(): EthiopicCalendar {
    return EthiopicDateUtils.lastDayOfMonth(this)
}

/**
 * Returns the first day of this date's year.
 */
fun EthiopicCalendar.firstDayOfYear(): EthiopicCalendar {
    return EthiopicDateUtils.firstDayOfYear(this)
}

/**
 * Returns the last day of this date's year.
 */
fun EthiopicCalendar.lastDayOfYear(): EthiopicCalendar {
    return EthiopicDateUtils.lastDayOfYear(this)
}

/**
 * Checks if this date is in the same month as another date.
 */
fun EthiopicCalendar.isSameMonth(other: EthiopicCalendar): Boolean {
    return EthiopicDateUtils.isSameMonth(this, other)
}

/**
 * Checks if this date is in the same year as another date.
 */
fun EthiopicCalendar.isSameYear(other: EthiopicCalendar): Boolean {
    return EthiopicDateUtils.isSameYear(this, other)
}

/**
 * Calculates age in years from this birth date to the current date.
 */
fun EthiopicCalendar.ageAt(currentDate: EthiopicCalendar): Int {
    return EthiopicDateUtils.calculateAge(this, currentDate)
}

/**
 * Calculates current age in years from this birth date.
 */
fun EthiopicCalendar.currentAge(): Int {
    return ageAt(EthiopicCalendar.now())
}
