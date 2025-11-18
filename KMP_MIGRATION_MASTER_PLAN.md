# KMP Migration Master Plan: Ethiopic Chrono

## Current State (Phase 1: COMPLETED ✅)
- ✅ Android library with Ethiopic calendar implementation
- ✅ Core classes: EthiopicDate, EthiopicChronology, EthiopicEra
- ✅ Full JSR-310 Temporal API support
- ✅ Kotlin-based implementation
- ✅ minSdk 26, targetSdk 36

## Migration Overview

This plan outlines the complete migration from Android-only to Kotlin Multiplatform (KMP), enabling iOS, JVM, JS, and Native platform support.

---

## Phase 2: KMP Foundation & iOS Implementation

### Objectives
- Migrate project structure to KMP
- Implement iOS target support
- Ensure commonMain code compiles on all platforms
- Set up proper source sets for Android and iOS

### Tasks

#### 2.1 Project Structure Migration
- [ ] Update root `build.gradle.kts` for KMP
- [ ] Convert `ethiopic-chrono/build.gradle.kts` to KMP module
- [ ] Add Kotlin Multiplatform plugin
- [ ] Configure source sets: commonMain, commonTest, androidMain, iosMain

#### 2.2 Source Code Organization
- [ ] Move current Kotlin files to `src/commonMain/kotlin/`
- [ ] Verify no Android-specific dependencies in common code
- [ ] Remove or abstract Android-specific annotations
- [ ] Create platform-specific implementations where needed

#### 2.3 iOS Target Setup
- [ ] Add iOS target configurations (iosArm64, iosSimulatorArm64, iosX64)
- [ ] Configure iOS framework export
- [ ] Set up CocoaPods or SPM integration for iOS
- [ ] Create iOS-specific implementations if needed

#### 2.4 Dependencies Migration
- [ ] Replace Android-specific dependencies with multiplatform equivalents
- [ ] Update androidx.annotation to common expect/actual pattern
- [ ] Verify all dependencies support KMP

#### 2.5 Testing Infrastructure
- [ ] Set up commonTest source set
- [ ] Migrate existing tests to commonTest
- [ ] Add iOS-specific tests in iosTest
- [ ] Configure test running for all platforms

### Success Criteria
- ✅ Project builds successfully for Android and iOS targets
- ✅ All tests pass on both platforms
- ✅ iOS framework can be exported and consumed
- ✅ No platform-specific code in commonMain

---

## Phase 3: Domain Layer Migration

### Objectives
- Ensure business logic is platform-agnostic
- Implement clean architecture patterns
- Add comprehensive validation and error handling

### Tasks

#### 3.1 Domain Model Enhancement
- [ ] Review EthiopicDate for domain logic purity
- [ ] Add validation layer for date operations
- [ ] Implement value objects and entities pattern
- [ ] Add domain-specific exceptions

#### 3.2 Use Cases/Interactors
- [ ] Create use cases for common date operations
- [ ] Implement calendar conversion use cases
- [ ] Add date calculation utilities
- [ ] Create formatting and parsing use cases

#### 3.3 Repository Pattern (if needed)
- [ ] Define repository interfaces in commonMain
- [ ] Implement platform-specific data sources
- [ ] Add caching mechanisms
- [ ] Implement data persistence layer

#### 3.4 Business Rules
- [ ] Implement Ethiopic calendar business rules
- [ ] Add leap year calculation validation
- [ ] Create era transition logic
- [ ] Add holiday calculation support (optional)

### Success Criteria
- ✅ Domain layer has no platform dependencies
- ✅ All business logic is testable in commonTest
- ✅ Clear separation of concerns
- ✅ Comprehensive unit test coverage (>80%)

---

## Phase 4: UI Layer Migration (Optional - if UI components exist)

### Note
Current analysis shows this is a library project with no UI components. This phase may be skipped or adapted for:
- Sample apps demonstrating library usage
- Calendar picker components (future enhancement)
- Demo applications for each platform

### Potential Tasks (if UI is added later)

#### 4.1 Compose Multiplatform Setup
- [ ] Add Compose Multiplatform dependencies
- [ ] Create shared UI components
- [ ] Implement calendar visualization
- [ ] Add date picker component

#### 4.2 Platform-Specific UI
- [ ] Android Compose UI implementation
- [ ] iOS SwiftUI bridge
- [ ] JS/Web UI implementation
- [ ] Desktop UI (if needed)

#### 4.3 ViewModel/Presentation Layer
- [ ] Create shared ViewModels
- [ ] Implement state management
- [ ] Add navigation logic
- [ ] Implement UI event handling

### Success Criteria (if applicable)
- ✅ Shared UI components work on all platforms
- ✅ Platform-specific UI integrations are clean
- ✅ Consistent user experience across platforms

---

## Phase 5: Platform-Specific Features & Optimizations

### Objectives
- Leverage platform-specific capabilities
- Optimize performance for each platform
- Add platform integrations
- Implement native date/time formatting

### Tasks

#### 5.1 Android Platform Features
- [ ] Implement Android-specific date formatters
- [ ] Add locale support for Android
- [ ] Integrate with Android DateFormat system
- [ ] Add Parcelize support for EthiopicDate
- [ ] Optimize for Android runtime

#### 5.2 iOS Platform Features
- [ ] Create Swift-friendly API layer
- [ ] Implement iOS-specific date formatters
- [ ] Add Foundation Date conversions
- [ ] Integrate with iOS locale system
- [ ] Add Codable support for EthiopicDate

#### 5.3 Performance Optimizations
- [ ] Benchmark date calculations on all platforms
- [ ] Optimize epoch day conversions
- [ ] Add caching for frequently used calculations
- [ ] Minimize object allocations
- [ ] Profile and optimize hot paths

#### 5.4 Platform Integrations
- [ ] Android: Calendar provider integration
- [ ] iOS: EventKit integration (optional)
- [ ] Web: JavaScript Date interop
- [ ] JVM: java.time.LocalDate integration

#### 5.5 Advanced Features
- [ ] Add localized month names (Amharic/English)
- [ ] Implement date formatting patterns
- [ ] Add Ethiopic holidays calculation
- [ ] Create timezone support
- [ ] Implement period and duration calculations

#### 5.6 Documentation & Publishing
- [ ] Update README for KMP usage
- [ ] Create platform-specific usage guides
- [ ] Add API documentation
- [ ] Prepare Maven Central publishing
- [ ] Create CocoaPods spec for iOS
- [ ] Set up npm package for JS (optional)

### Success Criteria
- ✅ Platform-specific features work correctly
- ✅ Performance is optimized for each platform
- ✅ API is ergonomic for each platform's conventions
- ✅ Comprehensive documentation exists
- ✅ Library is published to relevant package managers

---

## Timeline Estimates

- **Phase 2**: 2-3 days (KMP structure + iOS)
- **Phase 3**: 2-3 days (Domain layer)
- **Phase 4**: 3-5 days (UI - if needed, otherwise skip)
- **Phase 5**: 3-4 days (Platform features + optimizations)

**Total**: 7-10 days (excluding UI phase if not needed)

---

## Risk Assessment

### High Risk
- **iOS API compatibility**: Ensure API works well with Swift/Obj-C
- **Platform date/time differences**: Handle platform-specific calendar quirks

### Medium Risk
- **Performance on iOS**: Native date calculations may differ from JVM
- **Dependency compatibility**: Some dependencies may not support all platforms

### Low Risk
- **Core logic migration**: Pure Kotlin code should migrate cleanly
- **Testing**: Existing tests should port to commonTest easily

---

## Rollback Strategy

Each phase should:
1. Be developed on a feature branch
2. Have passing tests before merge
3. Maintain backward compatibility with Android
4. Allow reverting to previous phase if issues arise

---

## Success Metrics

### Technical Metrics
- All platforms build successfully
- Test coverage >80% in commonTest
- No platform-specific code in commonMain (except expect/actual)
- API surface is consistent across platforms

### Quality Metrics
- Zero known bugs after migration
- Performance within 10% of Android-only version
- API is ergonomic for each platform
- Documentation is comprehensive

---

## Next Steps

1. **Immediate**: Begin Phase 2 - KMP structure migration
2. **Create branch**: `claude/kmp-phase2-foundation`
3. **Deliverables**: Working KMP project with Android + iOS support
4. **Review**: Ensure all Phase 2 criteria met before proceeding to Phase 3

---

## Notes

- This is a library project, so UI migration (Phase 4) is optional
- Focus on creating a solid, well-tested multiplatform library
- Maintain API compatibility where possible
- Prioritize iOS support as the primary new platform
- Consider adding JS/Web support in future phases
