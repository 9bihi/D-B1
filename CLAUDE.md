# CLAUDE.md - Deutsch B1 Exam

## Project Context
The **Deutsch B1 Exam** app is a premium Android application built with Jetpack Compose (Kotlin). It provides comprehensive B1-level exam preparation (Goethe, ÖSD) and features a modern "glassmorphism" design with a custom bottom navigation bar.

## "Always" Rules
1. **Always** read `memory-bank/architecture.md` before writing any code to maintain the existing single-activity, component-based structure.
2. **Always** read `memory-bank/product-requirements.md` to ensure any new feature fits the premium, educational goal of the project.
3. **Always** update `memory-bank/architecture.md` and `memory-bank/progress.md` after implementing a major feature (e.g., adding a new exam part or integration).

## Coding Style
- **Jetpack Compose**: Use purely declarative UI. Avoid legacy XML or Fragments.
- **Glassmorphism**: When creating new UI components, follow the "glass" pattern: dark translucent backgrounds (e.g., `Color(0xFF1C1C1E).copy(alpha = 0.88f)`), subtle borders, and `shadow` with low alpha.
- **Modularity**:
    - Keep screens in `ui/`, data models and repositories in `data/`, and central navigation in `navigation/`.
    - Extract reusable UI components into a dedicated `ui/components/` package (WIP).
- **Naming**: Use clear, German/English hybrid naming that matches the exam vocabulary (e.g., `LesenScreen`, `HoerenPart`, `ModelltestSelector`).

## Best Practices
- **Recomposition**: Be mindful of recomposition; hoist state appropriately and use `remember` for calculation-heavy UI logic.
- **Animations**: Use Compose's `animatedContent` or `animateFloatAsState` with `spring` transitions for the "premium" feel.
- **Data Layers**: Prefer static `ExamData` objects for mock tests unless a local database (Room) is strictly required for persistent user scores.
- **Resource Linking**: String resources should eventually be moved to `strings.xml` for potential localization, though currently hardcoded for development speed.

## Decision Making
- **Ask** the user if you're adding new external dependencies or significantly altering the `AppNavGraph`.
- **Proceed** autonomously for bug fixes, content population (`ExamData.kt`), and UI polishing (CSS/Color/Padding adjustments).

## Build Commands
- **Debug Build**: `./gradlew assembleDebug` (Outputs to `app/build/outputs/apk/debug/app-debug.apk`)
- **Release Build**: `./gradlew assembleRelease` (Outputs to `app/build/outputs/apk/release/app-release.apk`)
- **Clean Build**: `./gradlew clean assembleRelease`
