# Ethiopic Chrono

A lightweight Kotlin Multiplatform library providing full support for the Ethiopic (Ethiopian) calendar system.

## Overview

This library is a focused implementation of the Ethiopic calendar system for Kotlin Multiplatform, supporting Android, iOS, and other platforms. Unlike the bulky threeten-extra library that includes many calendar systems, this library contains **only** the Ethiopic calendar implementation with a clean, platform-agnostic API.

## Features

- **Kotlin Multiplatform**: Works on Android, iOS, JVM, and other KMP targets
- **EthiopicCalendar**: Clean, immutable API for Ethiopic dates
- **Platform-agnostic**: Core business logic shared across all platforms
- **Rich date operations**: Add/subtract days, months, years with proper overflow handling
- **Date ranges**: Iterate through date ranges with for loops and range operators
- **Utility functions**: Age calculation, leap year detection, date comparisons, and more
- **Localization**: Built-in support for English and Amharic month names
- **Type-safe**: Leverages Kotlin's type system for compile-time safety
- **Lightweight**: Focused library with only Ethiopic calendar support
- **Well-tested**: Comprehensive test suite covering all features
- **Legacy support**: Android-specific java.time API available for backward compatibility

## Calendar Details

The Ethiopic calendar system:
- **13 months per year**: 12 months of 30 days + 1 intercalary month (Pagumen) of 5-6 days
- **Leap years**: Every 4 years (when year % 4 == 3)
- **Epoch alignment**: `0001-01-01 (Ethiopic)` = `0008-08-27 (ISO)`
- **365/366 days**: Same total days as Gregorian, but distributed differently

### Month Names

| # | English | Amharic | Days |
|---|---------|---------|------|
| 1 | Meskerem | መስከረም | 30 |
| 2 | Tikimt | ጥቅምት | 30 |
| 3 | Hidar | ኅዳር | 30 |
| 4 | Tahsas | ታኅሣሥ | 30 |
| 5 | Tir | ጥር | 30 |
| 6 | Yekatit | የካቲት | 30 |
| 7 | Megabit | መጋቢት | 30 |
| 8 | Miazia | ሚያዝያ | 30 |
| 9 | Genbot | ግንቦት | 30 |
| 10 | Sene | ሰኔ | 30 |
| 11 | Hamle | ሐምሌ | 30 |
| 12 | Nehase | ነሐሴ | 30 |
| 13 | Pagumen | ጳጉሜን | 5-6 |

## Usage

### Creating Ethiopic Dates

```kotlin
import com.shalom.ethiopicchrono.EthiopicCalendar

// Current date
val today = EthiopicCalendar.now()

// Specific date (year, month, day)
val date = EthiopicCalendar.of(2016, 3, 15) // 15 Megabit 2016

// From ISO calendar date
val ethiopicDate = EthiopicCalendar.fromIso(2024, 7, 15)

// From epoch day (days since 1970-01-01 ISO)
val fromEpoch = EthiopicCalendar.fromEpochDay(19350)

// From year and day-of-year
val fromDayOfYear = EthiopicCalendar.ofYearDay(2016, 75) // Day 75 of 2016
```

### Date Operations

```kotlin
val date = EthiopicCalendar.of(2016, 3, 15)

// Add/subtract days
val nextWeek = date.plusDays(7)
val yesterday = date.minusDays(1)

// Add/subtract months
val nextMonth = date.plusMonths(1)
val lastYear = date.minusYears(1)

// Get components
println("Year: ${date.year}")        // 2016
println("Month: ${date.month}")      // 3
println("Day: ${date.day}")          // 15

// Calculate days between dates
val date1 = EthiopicCalendar.of(2016, 1, 1)
val date2 = EthiopicCalendar.of(2016, 13, 5)
val daysBetween = date1.daysUntil(date2) // Returns Long
```

### Formatting and Localization

```kotlin
import com.shalom.ethiopicchrono.domain.DateLocale

val date = EthiopicCalendar.of(2016, 3, 15)

// Format with English month names
val english = date.format(DateLocale.ENGLISH)  // "15 Megabit 2016"

// Format with Amharic month names
val amharic = date.format(DateLocale.AMHARIC)  // "15 መጋቢት 2016"

// ISO-like string representation
val isoFormat = date.toString()  // "2016-03-15"

// Get month names directly
val monthEn = date.getMonthNameEnglish()  // "Megabit"
val monthAm = date.getMonthNameAmharic()  // "መጋቢት"
```

### Conversion to ISO Calendar

```kotlin
val ethiopicDate = EthiopicCalendar.of(2016, 3, 15)

// Convert to ISO date components
val isoDate = ethiopicDate.toIsoDate()
println("ISO: ${isoDate.year}-${isoDate.month}-${isoDate.day}")

// Convert to epoch day
val epochDay = ethiopicDate.toEpochDay()
```

### Leap Years and Calendar Rules

```kotlin
// Check if a year is a leap year
val isLeap = EthiopicCalendar.isLeapYear(2015)  // true (2015 % 4 == 3)

// Check if current date is in a leap year
val date = EthiopicCalendar.of(2015, 13, 6)
println(date.isInLeapYear())  // true

// Get day of year
val dayOfYear = date.getDayOfYear()  // 1-365 or 1-366
```

### Advanced Features

#### Date Ranges

Iterate through date ranges with ease:

```kotlin
val start = EthiopicCalendar.of(2016, 1, 1)
val end = EthiopicCalendar.of(2016, 1, 10)

// Iterate day by day
for (date in start..end) {
    println(date.format())
}

// Iterate with a step
for (date in (start..end step 3)) {
    println(date.format()) // Days 1, 4, 7, 10
}

// Check if date is in range
val someDate = EthiopicCalendar.of(2016, 1, 5)
if (someDate in start..end) {
    println("Date is in range!")
}

// Get range length
val range = start..end
println("Range has ${range.lengthInDays()} days")
```

#### Date Utilities

Convenient utility functions for common operations:

```kotlin
val date = EthiopicCalendar.of(2016, 3, 15)

// First and last days
val firstDay = date.firstDayOfMonth()  // 2016-03-01
val lastDay = date.lastDayOfMonth()   // 2016-03-30
val yearStart = date.firstDayOfYear() // 2016-01-01
val yearEnd = date.lastDayOfYear()    // 2016-13-05

// All dates in a period
val datesInMonth = EthiopicDateUtils.datesInMonth(2016, 3) // List of 30 dates
val datesInYear = EthiopicDateUtils.datesInYear(2016)     // List of 365 dates

// Date comparisons
val date1 = EthiopicCalendar.of(2016, 1, 15)
val date2 = EthiopicCalendar.of(2016, 3, 20)
println(date1.isSameMonth(date2))  // false
println(date1.isSameYear(date2))   // true

// Calculate intervals
val monthsBetween = EthiopicDateUtils.monthsBetween(date1, date2)  // 2
val yearsBetween = EthiopicDateUtils.yearsBetween(date1, date2)    // 0

// Age calculations
val birthDate = EthiopicCalendar.of(2000, 1, 15)
val currentDate = EthiopicCalendar.of(2016, 5, 20)
val age = birthDate.ageAt(currentDate)  // 16

// Or get current age
val currentAge = birthDate.currentAge()

// Find leap years
val leapYears = EthiopicDateUtils.leapYearsInRange(2010, 2020)
val nextLeap = EthiopicDateUtils.nextLeapYear(2016)     // 2019
val prevLeap = EthiopicDateUtils.previousLeapYear(2016) // 2015

// Next occurrence of a specific day
val next15th = EthiopicDateUtils.nextDayOfMonth(date, 15)
```

### Platform-Specific Usage

The library automatically uses the appropriate date/time APIs for each platform:
- **Android**: Uses `java.time` APIs (requires minSdk 26)
- **iOS**: Uses `Foundation.NSDate` and `NSCalendar` APIs
- **Other platforms**: Uses shared Kotlin implementation

All APIs work identically across platforms!

## Installation

### Kotlin Multiplatform

```kotlin
kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation("com.shalom:ethiopic-chrono:2.0.0")
            }
        }
    }
}
```

### Android-only Project

```gradle
dependencies {
    implementation("com.shalom:ethiopic-chrono:2.0.0")
}
```

### iOS (CocoaPods)

```ruby
pod 'EthiopicChrono', '~> 2.0.0'
```

## Requirements

### Android
- Minimum SDK: 26 (Android 8.0 Oreo) - required for java.time APIs
- Target SDK: 36
- Kotlin: 2.0.21+

### iOS
- iOS Deployment Target: 14.0+
- Xcode: 14.0+

### Common
- Kotlin: 2.0.21+
- Gradle: 8.0+

## Comparison with ThreeTen-Extra

This library provides the same Ethiopic calendar functionality as ThreeTen-Extra but with significant improvements:

| Feature | ThreeTen-Extra | Ethiopic Chrono |
|---------|----------------|-----------------|
| **Platform Support** | JVM only | Android, iOS, JVM, Native |
| **API Style** | Java-centric | Kotlin-first |
| **Calendar Systems** | 30+ calendars | Ethiopic only |
| **Size** | Large (all calendars) | Small (focused) |
| **Localization** | Limited | English + Amharic built-in |
| **Type Safety** | Good | Excellent (Kotlin) |
| **Null Safety** | Annotations | Kotlin null safety |
| **Immutability** | Via conventions | Data classes |

## Migration Guide (v1.x to v2.x)

If you're upgrading from the Android-only version (v1.x) to the KMP version (v2.x):

### Old API (v1.x - Android only)
```kotlin
import com.shalom.ethiopicchrono.EthiopicDate

val date = EthiopicDate.of(2016, 3, 15)
val year = date.getLong(ChronoField.YEAR)
val isoDate = LocalDate.ofEpochDay(date.toEpochDay())
```

### New API (v2.x - Multiplatform)
```kotlin
import com.shalom.ethiopicchrono.EthiopicCalendar

val date = EthiopicCalendar.of(2016, 3, 15)
val year = date.year  // Direct property access
val isoDate = date.toIsoDate()  // Returns IsoDateComponents
```

### Legacy Support

The old Java Time API is still available in Android builds under the `javatime` package:

```kotlin
// Android only - for backward compatibility
import com.shalom.ethiopicchrono.javatime.EthiopicDate
import com.shalom.ethiopicchrono.javatime.EthiopicChronology

val date = EthiopicDate.of(2016, 3, 15)
// Works exactly like v1.x
```

## Architecture

The library is structured in layers for optimal code sharing:

```
ethiopic-chrono/
├── commonMain/           # Shared code (works on all platforms)
│   ├── domain/          # Business logic & calendar rules
│   │   ├── EthiopicCalendarConstants.kt
│   │   ├── EthiopicCalendarRules.kt
│   │   └── EthiopicDateComponents.kt
│   ├── platform/        # Platform abstractions (expect declarations)
│   │   └── PlatformDate.kt
│   └── EthiopicCalendar.kt  # Main API
│
├── androidMain/         # Android-specific implementations
│   ├── platform/        # Android actual implementations (java.time)
│   │   └── PlatformDate.android.kt
│   └── javatime/        # Legacy API for backward compatibility
│       ├── EthiopicDate.kt
│       ├── EthiopicChronology.kt
│       └── EthiopicEra.kt
│
└── iosMain/             # iOS-specific implementations
    └── platform/        # iOS actual implementations (Foundation)
        └── PlatformDate.ios.kt
```

### Design Principles

1. **Shared Business Logic**: All calendar calculations in `commonMain`
2. **Platform Abstractions**: `expect/actual` for platform-specific date/time APIs
3. **Immutable Data**: All date objects are immutable value types
4. **Type Safety**: Leverages Kotlin's type system
5. **Zero Dependencies**: No external dependencies (except platform stdlib)

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request. For major changes, please open an issue first to discuss what you would like to change.

### Development Setup

1. Clone the repository
2. Open in Android Studio or IntelliJ IDEA
3. Sync Gradle
4. Run tests: `./gradlew test`

### Running Tests

```bash
# Run all tests
./gradlew test

# Android tests
./gradlew testDebugUnitTest

# iOS tests (requires macOS)
./gradlew iosX64Test
```

## Roadmap

- [x] Phase 1: Core Ethiopic calendar implementation (Android)
- [x] Phase 2: Kotlin Multiplatform migration with iOS support
- [x] Phase 3: Domain layer with platform abstractions
- [x] Phase 5: Advanced features
  - [x] Date range operations with iteration
  - [x] Comprehensive utility functions
  - [x] Age calculations
  - [x] Leap year detection and queries
- [ ] Future enhancements
  - [ ] Ethiopic holidays calculation
  - [ ] More locales (Tigrinya, Oromo, etc.)
  - [ ] Additional platforms (JS, Wasm, Desktop)
  - [ ] Compose Multiplatform date picker component
  - [ ] SwiftUI date picker component
  - [ ] kotlinx-datetime integration

## Credits

Based on the excellent ThreeTen-Extra library by Stephen Colebourne and contributors.
- Original Java implementation: https://github.com/ThreeTen/threeten-extra
- Calendar algorithms: From JSR-310 (java.time) chronology system
- License: BSD 3-Clause (maintained from original)

## License

BSD 3-Clause License - See LICENSE file for details.

---

**Made with ❤️ for the Ethiopian developer community**
