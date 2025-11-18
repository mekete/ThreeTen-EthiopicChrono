package com.shalom.ethiopicchrono

/**
 * Represents a range of Ethiopic calendar dates.
 *
 * Usage:
 * ```kotlin
 * val start = EthiopicCalendar.of(2016, 1, 1)
 * val end = EthiopicCalendar.of(2016, 1, 10)
 * val range = start..end
 *
 * for (date in range) {
 *     println(date.format())
 * }
 * ```
 */
class EthiopicDateRange(
    override val start: EthiopicCalendar,
    override val endInclusive: EthiopicCalendar
) : ClosedRange<EthiopicCalendar>, Iterable<EthiopicCalendar> {

    /**
     * Returns an iterator over the dates in this range.
     * Iterates day by day from start to end (inclusive).
     */
    override fun iterator(): Iterator<EthiopicCalendar> {
        return EthiopicDateIterator(start, endInclusive)
    }

    /**
     * Returns the number of days in this range (inclusive).
     */
    fun lengthInDays(): Long {
        return start.daysUntil(endInclusive) + 1
    }

    /**
     * Checks if this range contains the given date.
     */
    override fun contains(value: EthiopicCalendar): Boolean {
        return value >= start && value <= endInclusive
    }

    /**
     * Checks if this range is empty (start > end).
     */
    override fun isEmpty(): Boolean {
        return start > endInclusive
    }

    /**
     * Returns a string representation of this range.
     */
    override fun toString(): String {
        return "$start..$endInclusive"
    }
}

/**
 * Iterator for Ethiopic dates in a range.
 */
private class EthiopicDateIterator(
    start: EthiopicCalendar,
    private val endInclusive: EthiopicCalendar
) : Iterator<EthiopicCalendar> {

    private var current: EthiopicCalendar = start

    override fun hasNext(): Boolean {
        return current <= endInclusive
    }

    override fun next(): EthiopicCalendar {
        if (!hasNext()) {
            throw NoSuchElementException()
        }
        val result = current
        current = current.plusDays(1)
        return result
    }
}

/**
 * Creates a range from this date to the specified end date.
 *
 * Usage:
 * ```kotlin
 * val start = EthiopicCalendar.of(2016, 1, 1)
 * val end = EthiopicCalendar.of(2016, 1, 10)
 * val range = start..end
 * ```
 */
operator fun EthiopicCalendar.rangeTo(other: EthiopicCalendar): EthiopicDateRange {
    return EthiopicDateRange(this, other)
}

/**
 * Creates a progression that steps through dates by the specified number of days.
 *
 * Usage:
 * ```kotlin
 * val start = EthiopicCalendar.of(2016, 1, 1)
 * val end = EthiopicCalendar.of(2016, 1, 30)
 *
 * // Every 5 days
 * for (date in start..end step 5) {
 *     println(date.format())
 * }
 * ```
 */
infix fun EthiopicDateRange.step(days: Int): Iterable<EthiopicCalendar> {
    require(days > 0) { "Step must be positive, was: $days" }
    return object : Iterable<EthiopicCalendar> {
        override fun iterator(): Iterator<EthiopicCalendar> {
            return object : Iterator<EthiopicCalendar> {
                private var current: EthiopicCalendar = start

                override fun hasNext(): Boolean {
                    return current <= endInclusive
                }

                override fun next(): EthiopicCalendar {
                    if (!hasNext()) {
                        throw NoSuchElementException()
                    }
                    val result = current
                    current = current.plusDays(days.toLong())
                    return result
                }
            }
        }
    }
}

/**
 * Extends Comparable to EthiopicCalendar for range operations.
 */
operator fun EthiopicCalendar.compareTo(other: EthiopicCalendar): Int {
    val epochDiff = this.toEpochDay() - other.toEpochDay()
    return when {
        epochDiff > 0 -> 1
        epochDiff < 0 -> -1
        else -> 0
    }
}
