package com.shalom.ethiopicchrono.platform

import com.shalom.ethiopicchrono.domain.EthiopicCalendarRules
import com.shalom.ethiopicchrono.domain.EthiopicDateComponents
import platform.Foundation.*
import kotlinx.cinterop.*

/**
 * iOS implementation of PlatformDate using Foundation Date APIs.
 */
actual class PlatformDate {
    private val calendar = NSCalendar.currentCalendar
    private val referenceDate = NSDate.dateWithTimeIntervalSince1970(0.0)

    actual fun now(): EthiopicDateComponents {
        val today = NSDate()
        val epochDay = calculateEpochDayFromNSDate(today)
        return fromEpochDay(epochDay)
    }

    actual fun fromIsoDate(year: Int, month: Int, day: Int): EthiopicDateComponents {
        val components = NSDateComponents()
        components.year = year.toLong()
        components.month = month.toLong()
        components.day = day.toLong()
        components.calendar = calendar

        val date = calendar.dateFromComponents(components)
            ?: throw IllegalArgumentException("Invalid ISO date: $year-$month-$day")

        val epochDay = calculateEpochDayFromNSDate(date)
        return fromEpochDay(epochDay)
    }

    actual fun toIsoDate(components: EthiopicDateComponents): IsoDateComponents {
        val epochDay = toEpochDay(components)
        val nsDate = createNSDateFromEpochDay(epochDay)

        val dateComponents = calendar.components(
            NSCalendarUnitYear or NSCalendarUnitMonth or NSCalendarUnitDay,
            fromDate = nsDate
        )

        return IsoDateComponents(
            year = dateComponents.year.toInt(),
            month = dateComponents.month.toInt(),
            day = dateComponents.day.toInt()
        )
    }

    actual fun fromEpochDay(epochDay: Long): EthiopicDateComponents {
        return EthiopicCalendarRules.fromEpochDay(epochDay)
    }

    actual fun toEpochDay(components: EthiopicDateComponents): Long {
        return EthiopicCalendarRules.toEpochDay(components.year, components.month, components.day)
    }

    private fun calculateEpochDayFromNSDate(date: NSDate): Long {
        // NSDate.timeIntervalSince1970 gives seconds since Unix epoch
        val secondsSinceEpoch = date.timeIntervalSince1970
        return (secondsSinceEpoch / 86400.0).toLong() // 86400 seconds in a day
    }

    private fun createNSDateFromEpochDay(epochDay: Long): NSDate {
        val secondsSinceEpoch = epochDay * 86400.0 // Convert days to seconds
        return NSDate.dateWithTimeIntervalSince1970(secondsSinceEpoch)
    }
}

/**
 * Creates a new PlatformDate instance for iOS.
 */
actual fun createPlatformDate(): PlatformDate = PlatformDate()
