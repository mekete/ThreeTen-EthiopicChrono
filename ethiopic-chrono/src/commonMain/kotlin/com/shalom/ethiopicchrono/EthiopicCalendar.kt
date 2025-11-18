package com.shalom.ethiopicchrono

import com.shalom.ethiopicchrono.domain.*
import com.shalom.ethiopicchrono.platform.IsoDateComponents
import com.shalom.ethiopicchrono.platform.createPlatformDate

/**
 * Main API for working with Ethiopic calendar dates.
 * This provides a platform-agnostic interface that works on Android, iOS, and other KMP targets.
 *
 * Usage:
 * ```kotlin
 * // Create a date
 * val date = EthiopicCalendar.of(2016, 3, 15)
 *
 * // Get current date
 * val today = EthiopicCalendar.now()
 *
 * // Convert from ISO
 * val ethiopicDate = EthiopicCalendar.fromIso(2023, 11, 25)
 *
 * // Date operations
 * val nextWeek = date.plusDays(7)
 * val formatted = date.format(DateLocale.ENGLISH) // "15 Megabit 2016"
 * ```
 */
data class EthiopicCalendar(val components: EthiopicDateComponents) {

    val year: Int get() = components.year
    val month: Int get() = components.month
    val day: Int get() = components.day

    /**
     * Returns whether this date is in a leap year.
     */
    fun isInLeapYear(): Boolean = components.isInLeapYear()

    /**
     * Returns the day of year (1-365 or 1-366).
     */
    fun getDayOfYear(): Int = components.getDayOfYear()

    /**
     * Returns the month name in English.
     */
    fun getMonthNameEnglish(): String = components.getMonthNameEnglish()

    /**
     * Returns the month name in Amharic.
     */
    fun getMonthNameAmharic(): String = components.getMonthNameAmharic()

    /**
     * Adds the specified number of days to this date.
     */
    fun plusDays(days: Long): EthiopicCalendar {
        val newComponents = EthiopicCalendarRules.plusDays(components, days)
        return EthiopicCalendar(newComponents)
    }

    /**
     * Subtracts the specified number of days from this date.
     */
    fun minusDays(days: Long): EthiopicCalendar = plusDays(-days)

    /**
     * Adds the specified number of months to this date.
     * Note: If the resulting day is invalid for the target month, it will be adjusted.
     */
    fun plusMonths(months: Int): EthiopicCalendar {
        var newYear = year
        var newMonth = month + months

        while (newMonth > EthiopicCalendarConstants.MONTHS_PER_YEAR) {
            newMonth -= EthiopicCalendarConstants.MONTHS_PER_YEAR
            newYear++
        }
        while (newMonth < 1) {
            newMonth += EthiopicCalendarConstants.MONTHS_PER_YEAR
            newYear--
        }

        // Adjust day if necessary
        val maxDay = EthiopicCalendarRules.daysInMonth(newYear, newMonth)
        val newDay = minOf(day, maxDay)

        return EthiopicCalendar(EthiopicDateComponents(newYear, newMonth, newDay))
    }

    /**
     * Subtracts the specified number of months from this date.
     */
    fun minusMonths(months: Int): EthiopicCalendar = plusMonths(-months)

    /**
     * Adds the specified number of years to this date.
     */
    fun plusYears(years: Int): EthiopicCalendar {
        val newYear = year + years
        val maxDay = EthiopicCalendarRules.daysInMonth(newYear, month)
        val newDay = minOf(day, maxDay)
        return EthiopicCalendar(EthiopicDateComponents(newYear, month, newDay))
    }

    /**
     * Subtracts the specified number of years from this date.
     */
    fun minusYears(years: Int): EthiopicCalendar = plusYears(-years)

    /**
     * Calculates the number of days between this date and another date.
     */
    fun daysUntil(other: EthiopicCalendar): Long {
        return EthiopicCalendarRules.daysBetween(this.components, other.components)
    }

    /**
     * Converts this Ethiopic date to ISO calendar date.
     */
    fun toIsoDate(): IsoDateComponents {
        val platformDate = createPlatformDate()
        return platformDate.toIsoDate(components)
    }

    /**
     * Converts to epoch day (days since 1970-01-01 ISO).
     */
    fun toEpochDay(): Long {
        return EthiopicCalendarRules.toEpochDay(year, month, day)
    }

    /**
     * Formats the date as a string.
     * Format: "DD MonthName YYYY" (e.g., "15 Megabit 2016")
     */
    fun format(locale: DateLocale = DateLocale.ENGLISH): String {
        return components.toFormattedString(locale)
    }

    /**
     * Returns ISO-like string representation: "YYYY-MM-DD"
     */
    override fun toString(): String = components.toString()

    companion object {
        /**
         * Creates an Ethiopic date from year, month, and day.
         * @throws IllegalArgumentException if the date is invalid
         */
        fun of(year: Int, month: Int, day: Int): EthiopicCalendar {
            return EthiopicCalendar(EthiopicDateComponents(year, month, day))
        }

        /**
         * Gets the current date in the Ethiopic calendar.
         */
        fun now(): EthiopicCalendar {
            val platformDate = createPlatformDate()
            return EthiopicCalendar(platformDate.now())
        }

        /**
         * Creates an Ethiopic date from an ISO calendar date.
         */
        fun fromIso(year: Int, month: Int, day: Int): EthiopicCalendar {
            val platformDate = createPlatformDate()
            return EthiopicCalendar(platformDate.fromIsoDate(year, month, day))
        }

        /**
         * Creates an Ethiopic date from epoch day (days since 1970-01-01 ISO).
         */
        fun fromEpochDay(epochDay: Long): EthiopicCalendar {
            return EthiopicCalendar(EthiopicCalendarRules.fromEpochDay(epochDay))
        }

        /**
         * Creates an Ethiopic date from year and day-of-year.
         */
        fun ofYearDay(year: Int, dayOfYear: Int): EthiopicCalendar {
            require(dayOfYear in 1..EthiopicCalendarRules.daysInYear(year)) {
                "Day of year $dayOfYear is invalid for year $year"
            }
            val month = EthiopicCalendarRules.toMonth(dayOfYear)
            val dayOfMonth = EthiopicCalendarRules.toDayOfMonth(dayOfYear)
            return EthiopicCalendar(EthiopicDateComponents(year, month, dayOfMonth))
        }

        /**
         * Checks if a year is a leap year in the Ethiopic calendar.
         */
        fun isLeapYear(year: Int): Boolean {
            return EthiopicCalendarRules.isLeapYear(year)
        }
    }
}
