package com.shalom.ethiopicchrono.domain

/**
 * Business rules and calculations for the Ethiopic calendar system.
 * All functions are platform-agnostic.
 */
object EthiopicCalendarRules {

    /**
     * Determines if a given year is a leap year in the Ethiopic calendar.
     *
     * In the Ethiopic calendar, a year is a leap year if (year % 4 == 3).
     * This is different from the Gregorian calendar.
     *
     * @param year The proleptic year
     * @return true if the year is a leap year, false otherwise
     */
    fun isLeapYear(year: Int): Boolean {
        return year % EthiopicCalendarConstants.LEAP_YEAR_CYCLE == 3
    }

    /**
     * Calculates the number of days in a given month.
     *
     * @param year The year
     * @param month The month (1-13)
     * @return The number of days in the month
     */
    fun daysInMonth(year: Int, month: Int): Int {
        require(month in 1..EthiopicCalendarConstants.MONTHS_PER_YEAR) {
            "Month must be between 1 and ${EthiopicCalendarConstants.MONTHS_PER_YEAR}"
        }

        return when {
            month < 13 -> EthiopicCalendarConstants.DAYS_PER_REGULAR_MONTH
            isLeapYear(year) -> EthiopicCalendarConstants.PAGUMEN_DAYS_LEAP
            else -> EthiopicCalendarConstants.PAGUMEN_DAYS_NORMAL
        }
    }

    /**
     * Calculates the number of days in a given year.
     *
     * @param year The year
     * @return 365 for regular years, 366 for leap years
     */
    fun daysInYear(year: Int): Int {
        return if (isLeapYear(year)) {
            EthiopicCalendarConstants.DAYS_PER_YEAR_LEAP
        } else {
            EthiopicCalendarConstants.DAYS_PER_YEAR_NORMAL
        }
    }

    /**
     * Validates if a date is valid.
     *
     * @param year The year
     * @param month The month (1-13)
     * @param day The day of month
     * @return true if valid, false otherwise
     */
    fun isValidDate(year: Int, month: Int, day: Int): Boolean {
        if (month < 1 || month > EthiopicCalendarConstants.MONTHS_PER_YEAR) {
            return false
        }
        if (day < 1) {
            return false
        }
        val maxDay = daysInMonth(year, month)
        return day <= maxDay
    }

    /**
     * Converts year, month, day to day-of-year.
     *
     * @param month The month (1-13)
     * @param day The day of month
     * @return The day of year (1-365 or 1-366)
     */
    fun toDayOfYear(month: Int, day: Int): Int {
        return (month - 1) * EthiopicCalendarConstants.DAYS_PER_REGULAR_MONTH + day
    }

    /**
     * Converts day-of-year to month.
     *
     * @param dayOfYear The day of year (1-365 or 1-366)
     * @return The month (1-13)
     */
    fun toMonth(dayOfYear: Int): Int {
        return (dayOfYear - 1) / EthiopicCalendarConstants.DAYS_PER_REGULAR_MONTH + 1
    }

    /**
     * Converts day-of-year to day-of-month.
     *
     * @param dayOfYear The day of year (1-365 or 1-366)
     * @return The day of month
     */
    fun toDayOfMonth(dayOfYear: Int): Int {
        return (dayOfYear - 1) % EthiopicCalendarConstants.DAYS_PER_REGULAR_MONTH + 1
    }

    /**
     * Calculates the epoch day from Ethiopic date components.
     * Epoch day is the number of days since the epoch (1970-01-01 ISO).
     *
     * @param year The Ethiopic year
     * @param month The Ethiopic month (1-13)
     * @param day The day of month
     * @return The epoch day
     */
    fun toEpochDay(year: Int, month: Int, day: Int): Long {
        val ethiopicEpochDay = calculateEthiopicEpochDay(year, month, day)
        return ethiopicEpochDay - EthiopicCalendarConstants.EPOCH_DAY_DIFFERENCE
    }

    /**
     * Calculates the Ethiopic epoch day (days since Ethiopic epoch).
     *
     * @param year The Ethiopic year
     * @param month The Ethiopic month
     * @param day The day of month
     * @return Days since Ethiopic epoch
     */
    private fun calculateEthiopicEpochDay(year: Int, month: Int, day: Int): Long {
        val yearDay = (year - 1).toLong() * 365L + (year - 1).toLong() / 4L
        val monthDay = (month - 1).toLong() * EthiopicCalendarConstants.DAYS_PER_REGULAR_MONTH.toLong()
        return yearDay + monthDay + day.toLong()
    }

    /**
     * Converts ISO epoch day to Ethiopic date components.
     *
     * @param epochDay The ISO epoch day
     * @return The Ethiopic date components
     */
    fun fromEpochDay(epochDay: Long): EthiopicDateComponents {
        var ethiopicED = epochDay + EthiopicCalendarConstants.EPOCH_DAY_DIFFERENCE
        var adjustment = 0

        if (ethiopicED < 0) {
            ethiopicED += (1461L * (1_000_000L / 4))
            adjustment = -1_000_000
        }

        val prolepticYear = (((ethiopicED * 4) + 1463) / 1461).toInt()
        val startYearEpochDay = (prolepticYear - 1) * 365 + (prolepticYear / 4)
        val doy0 = (ethiopicED - startYearEpochDay).toInt()
        val month = doy0 / 30 + 1
        val day = doy0 % 30 + 1

        return EthiopicDateComponents(prolepticYear + adjustment, month, day)
    }

    /**
     * Adds days to an Ethiopic date.
     *
     * @param components The starting date
     * @param daysToAdd The number of days to add (can be negative)
     * @return The resulting date
     */
    fun plusDays(components: EthiopicDateComponents, daysToAdd: Long): EthiopicDateComponents {
        val currentEpochDay = toEpochDay(components.year, components.month, components.day)
        val newEpochDay = currentEpochDay + daysToAdd
        return fromEpochDay(newEpochDay)
    }

    /**
     * Calculates the number of days between two Ethiopic dates.
     *
     * @param start The start date
     * @param end The end date
     * @return The number of days between the dates (positive if end is after start)
     */
    fun daysBetween(start: EthiopicDateComponents, end: EthiopicDateComponents): Long {
        val startEpochDay = toEpochDay(start.year, start.month, start.day)
        val endEpochDay = toEpochDay(end.year, end.month, end.day)
        return endEpochDay - startEpochDay
    }
}
