package com.shalom.ethiopicchrono.platform

import com.shalom.ethiopicchrono.domain.EthiopicCalendarRules
import com.shalom.ethiopicchrono.domain.EthiopicDateComponents
import java.time.LocalDate

/**
 * Android implementation of PlatformDate using java.time APIs.
 */
actual class PlatformDate {
    actual fun now(): EthiopicDateComponents {
        val today = LocalDate.now()
        return fromEpochDay(today.toEpochDay())
    }

    actual fun fromIsoDate(year: Int, month: Int, day: Int): EthiopicDateComponents {
        val localDate = LocalDate.of(year, month, day)
        return fromEpochDay(localDate.toEpochDay())
    }

    actual fun toIsoDate(components: EthiopicDateComponents): IsoDateComponents {
        val epochDay = toEpochDay(components)
        val localDate = LocalDate.ofEpochDay(epochDay)
        return IsoDateComponents(localDate.year, localDate.monthValue, localDate.dayOfMonth)
    }

    actual fun fromEpochDay(epochDay: Long): EthiopicDateComponents {
        return EthiopicCalendarRules.fromEpochDay(epochDay)
    }

    actual fun toEpochDay(components: EthiopicDateComponents): Long {
        return EthiopicCalendarRules.toEpochDay(components.year, components.month, components.day)
    }
}

/**
 * Creates a new PlatformDate instance for Android.
 */
actual fun createPlatformDate(): PlatformDate = PlatformDate()
