package com.shalom.ethiopicchrono.domain

/**
 * Constants for the Ethiopic calendar system.
 * These are platform-agnostic and can be used across all targets.
 */
object EthiopicCalendarConstants {
    /** Number of months in a year (12 regular + 1 intercalary) */
    const val MONTHS_PER_YEAR = 13

    /** Number of days in a regular month */
    const val DAYS_PER_REGULAR_MONTH = 30

    /** Number of days in the 13th month (Pagumen) in a non-leap year */
    const val PAGUMEN_DAYS_NORMAL = 5

    /** Number of days in the 13th month (Pagumen) in a leap year */
    const val PAGUMEN_DAYS_LEAP = 6

    /** Total days in a non-leap year */
    const val DAYS_PER_YEAR_NORMAL = 365

    /** Total days in a leap year */
    const val DAYS_PER_YEAR_LEAP = 366

    /** Leap year cycle */
    const val LEAP_YEAR_CYCLE = 4

    /** Epoch day difference between Ethiopic and ISO calendars */
    const val EPOCH_DAY_DIFFERENCE = 716367L

    /** Calendar system identifier */
    const val CALENDAR_ID = "Ethiopic"

    /** Calendar type */
    const val CALENDAR_TYPE = "ethiopic"

    /** Month names in English */
    val MONTH_NAMES_ENGLISH = listOf(
        "Meskerem",
        "Tikimt",
        "Hidar",
        "Tahsas",
        "Tir",
        "Yekatit",
        "Megabit",
        "Miazia",
        "Genbot",
        "Sene",
        "Hamle",
        "Nehase",
        "Pagumen"
    )

    /** Month names in Amharic (transliterated) */
    val MONTH_NAMES_AMHARIC = listOf(
        "መስከረም",
        "ጥቅምት",
        "ኅዳር",
        "ታኅሣሥ",
        "ጥር",
        "የካቲት",
        "መጋቢት",
        "ሚያዝያ",
        "ግንቦት",
        "ሰኔ",
        "ሐምሌ",
        "ነሐሴ",
        "ጳጉሜን"
    )
}
