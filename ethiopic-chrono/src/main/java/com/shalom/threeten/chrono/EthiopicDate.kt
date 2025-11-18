package com.shalom.threeten.chrono

 
/**
 * Convert Gregorian date to Julian Day Number
 * Uses Fliegel & Van Flandern algorithm
 * Valid for all dates in the proleptic Gregorian calendar
 */
private fun gregorianToJulianDay(
    year: Int,
    month: Int,
    day: Int
): Long {
    val a = (14 - month) / 12           // 0 for Jan-Dec, 1 for Nov-Dec
    val y = year + 4800 - a              // Adjusted year
    val m = month + 12 * a - 3           // Adjusted month (0-11)
    
    return (
        day +                                // Day of month
        (153 * m + 2) / 5 +                 // Days in previous months
        365 * y +                           // Days in complete years
        y / 4 -                             // Leap day every 4 years
        y / 100 +                           // Except every 100 years
        y / 400 -                           // Except every 400 years
        32045                               // Offset for epoch
    )
}

/**
 * Convert Julian Day Number back to Gregorian date
 */
private fun julianDayToGregorian(jdn: Long): Triple<Int, Int, Int> {
    val a = jdn + 32044
    val b = (4 * a + 3) / 146097
    val c = a - (146097 * b) / 4
    val d = (4 * c + 3) / 1461
    val e = c - (1461 * d) / 4
    val m = (5 * e + 2) / 153
    
    val day = e - (153 * m + 2) / 5 + 1
    val month = m + 3 - 12 * (m / 10)
    val year = 100 * b + d - 4800 + m / 10
    
    return Triple(year.toInt(), month.toInt(), day.toInt())
}



