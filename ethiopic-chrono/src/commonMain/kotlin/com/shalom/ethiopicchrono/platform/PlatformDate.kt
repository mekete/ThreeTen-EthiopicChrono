package com.shalom.ethiopicchrono.platform

import com.shalom.ethiopicchrono.domain.EthiopicDateComponents

/**
 * Platform-specific date operations.
 * Each platform implements this with its native date/time APIs.
 */
expect class PlatformDate {
    /**
     * Gets the current date in the system timezone as Ethiopic date components.
     */
    fun now(): EthiopicDateComponents

    /**
     * Converts ISO year, month, day to Ethiopic date components.
     */
    fun fromIsoDate(year: Int, month: Int, day: Int): EthiopicDateComponents

    /**
     * Converts Ethiopic date components to ISO year, month, day.
     * Returns a Triple of (year, month, day).
     */
    fun toIsoDate(components: EthiopicDateComponents): IsoDateComponents

    /**
     * Converts epoch day to Ethiopic date components.
     */
    fun fromEpochDay(epochDay: Long): EthiopicDateComponents

    /**
     * Converts Ethiopic date components to epoch day.
     */
    fun toEpochDay(components: EthiopicDateComponents): Long
}

/**
 * Represents ISO date components (year, month, day).
 */
data class IsoDateComponents(
    val year: Int,
    val month: Int,  // 1-12
    val day: Int     // 1-31
)

/**
 * Factory function to create a PlatformDate instance.
 */
expect fun createPlatformDate(): PlatformDate
