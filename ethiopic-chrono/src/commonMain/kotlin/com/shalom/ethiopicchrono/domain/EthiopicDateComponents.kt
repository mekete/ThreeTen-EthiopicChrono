package com.shalom.ethiopicchrono.domain

/**
 * Represents the basic components of an Ethiopic date.
 * This is a platform-agnostic value object.
 */
data class EthiopicDateComponents(
    val year: Int,
    val month: Int,
    val day: Int
) {
    init {
        require(month in 1..EthiopicCalendarConstants.MONTHS_PER_YEAR) {
            "Month must be between 1 and ${EthiopicCalendarConstants.MONTHS_PER_YEAR}, got $month"
        }
        require(day >= 1) {
            "Day must be at least 1, got $day"
        }

        // Validate day of month
        val maxDay = when {
            month < 13 -> EthiopicCalendarConstants.DAYS_PER_REGULAR_MONTH
            EthiopicCalendarRules.isLeapYear(year) -> EthiopicCalendarConstants.PAGUMEN_DAYS_LEAP
            else -> EthiopicCalendarConstants.PAGUMEN_DAYS_NORMAL
        }

        require(day <= maxDay) {
            "Day $day is invalid for month $month in year $year (max: $maxDay)"
        }
    }

    /**
     * Returns the month name in English
     */
    fun getMonthNameEnglish(): String {
        return EthiopicCalendarConstants.MONTH_NAMES_ENGLISH[month - 1]
    }

    /**
     * Returns the month name in Amharic
     */
    fun getMonthNameAmharic(): String {
        return EthiopicCalendarConstants.MONTH_NAMES_AMHARIC[month - 1]
    }

    /**
     * Returns whether this date is in a leap year
     */
    fun isInLeapYear(): Boolean {
        return EthiopicCalendarRules.isLeapYear(year)
    }

    /**
     * Returns the day of year (1-365 or 1-366)
     */
    fun getDayOfYear(): Int {
        return (month - 1) * EthiopicCalendarConstants.DAYS_PER_REGULAR_MONTH + day
    }

    /**
     * Returns a string representation in ISO-like format
     */
    override fun toString(): String {
        return String.format("%04d-%02d-%02d", year, month, day)
    }

    /**
     * Returns a formatted string representation
     */
    fun toFormattedString(locale: DateLocale = DateLocale.ENGLISH): String {
        val monthName = when (locale) {
            DateLocale.ENGLISH -> getMonthNameEnglish()
            DateLocale.AMHARIC -> getMonthNameAmharic()
        }
        return "$day $monthName $year"
    }
}

/**
 * Locale for date formatting
 */
enum class DateLocale {
    ENGLISH,
    AMHARIC
}
