# Ethiopic Chrono

A lightweight Kotlin Android library providing full support for the Ethiopic (Ethiopian) calendar system, based on ThreeTen-Extra's implementation.

## Overview

This library is a slim, focused implementation of the Ethiopic chronology from ThreeTen-Extra, specifically designed for Android applications. Unlike the bulky threeten-extra library that includes many calendar systems, this library contains **only** the Ethiopic calendar implementation.

## Features

- **EthiopicDate**: Full date representation in the Ethiopic calendar system
- **EthiopicChronology**: Complete chronology implementation following JSR-310 standards
- **EthiopicEra**: Support for BEFORE_INCARNATION and INCARNATION eras
- **Full Temporal API**: Complete support for Java Time API operations
- **Lightweight**: Slim library with only Ethiopic calendar support
- **Android Optimized**: Minimum SDK 26 (Android 8.0 Oreo)
- **Kotlin-first**: Written in idiomatic Kotlin

## Calendar Details

The Ethiopic calendar system:
- **13 months per year**: 12 months of 30 days + 1 month (Pagumen) of 5-6 days
- **Leap years**: Every 4 years (when year % 4 == 3)
- **Epoch alignment**: `0001-01-01 (Ethiopic)` = `0008-08-27 (ISO)`
- **Two eras**: BEFORE_INCARNATION (≤ 0) and INCARNATION (≥ 1)

## Usage

### Creating Ethiopic Dates

```kotlin
// Current date
val today = EthiopicDate.now()

// Specific date
val date = EthiopicDate.of(2016, 3, 15) // Year 2016, Month 3, Day 15

// From ISO date
val isoDate = LocalDate.of(2023, 11, 25)
val ethiopicDate = EthiopicDate.from(isoDate)

// From epoch day
val dateFromEpoch = EthiopicDate.ofEpochDay(19350)

// From year and day of year
val dateFromDayOfYear = EthiopicDate.ofYearDay(2016, 75)
```

### Date Operations

```kotlin
val date = EthiopicDate.of(2016, 3, 15)

// Add/subtract time
val nextWeek = date.plusWeeks(1)
val lastMonth = date.minusMonths(1)
val nextYear = date.plusYears(1)

// Get date components
val year = date.getLong(ChronoField.YEAR)
val month = date.getLong(ChronoField.MONTH_OF_YEAR)
val day = date.getLong(ChronoField.DAY_OF_MONTH)

// Calculate periods
val date1 = EthiopicDate.of(2016, 1, 1)
val date2 = EthiopicDate.of(2016, 13, 5)
val period = date1.until(date2) // Returns ChronoPeriod

// Calculate durations
val daysBetween = date1.until(date2, ChronoUnit.DAYS)
val monthsBetween = date1.until(date2, ChronoUnit.MONTHS)
```

### Conversion to ISO

```kotlin
val ethiopicDate = EthiopicDate.of(2016, 3, 15)
val isoDate = LocalDate.ofEpochDay(ethiopicDate.toEpochDay())
```

### Working with Chronology

```kotlin
val chrono = EthiopicChronology.INSTANCE

// Create dates through chronology
val date = chrono.date(2016, 3, 15)
val dateFromEra = chrono.date(EthiopicEra.INCARNATION, 2016, 3, 15)

// Check leap year
val isLeap = chrono.isLeapYear(2016) // Returns true (2016 % 4 == 0 in Ethiopic)
```

## Installation

Add the library to your Android project:

```gradle
dependencies {
    implementation("com.shalom:ethiopic-chrono:1.0.0")
}
```

## Requirements

- Minimum SDK: 26 (Android 8.0 Oreo)
- Target SDK: 36
- Kotlin: 2.0.21
- Java: 21

## Comparison with ThreeTen-Extra

This library provides the same Ethiopic calendar functionality as ThreeTen-Extra but:
- ✅ **Smaller size**: Only Ethiopic calendar (no Coptic, Julian, etc.)
- ✅ **Kotlin-first**: Written in Kotlin for better Android integration
- ✅ **Focused**: Single purpose, easier to maintain
- ✅ **Android optimized**: No Java 8+ desktop dependencies

## Credits

Based on the excellent ThreeTen-Extra library by Stephen Colebourne and contributors.
- Original Java implementation: https://github.com/ThreeTen/threeten-extra
- License: BSD 3-Clause (maintained from original)

## License

BSD 3-Clause License - See LICENSE file for details.
