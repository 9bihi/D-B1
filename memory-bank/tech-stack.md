# Tech Stack - Deutsch B1 Exam

## Core Platform
- **OS**: Android (minSdk 26, targetSdk 34)
- **Language**: Kotlin 1.9.0+
- **Framework**: Jetpack Compose (Modern Declartive UI)

## UI & Styling
- **Design System**: Material Design 3 (Material3)
- **Glassmorphism**: Custom implementation using translucent colors, shadows, and borders.
- **Animations**:
    - `AnimatedContent`, `animateFloatAsState`, `spring` for smooth transitions.
    - `systemuicontroller` (Accompanist) for edge-to-edge transparency.
- **Icons**: Android Material Icons (Extended)
- **Image Loading**: Coil (`io.coil-kt:coil-compose`)

## Networking & Data
- **HTTP Client**: Retrofit 2 + OkHttp
- **Serialization**: GSON (`retrofit2:converter-gson`)
- **APIs Used**:
    - **Google Books API**: (Inferred from code) Used for book searches/dictionary?
    - **MyMemory Translation API**: (Inferred from code) Used for free translations.
- **State Management**:
    - `mutableStateOf`, `mutableStateListOf` for UI state.
    - ViewModels (though many screens currently pass state directly - possible technical debt).

## Build & Dev Tools
- **Build System**: Gradle (Kotlin DSL - `.gradle.kts`)
- **Version Management**: Version Catalog (`libs.versions.toml`)
- **Linting/Testing**: AndroidX Test, JUnit (Standard Android templates)

## Choice Justifications
1. **Jetpack Compose**: Chosen for speed of UI iteration and complex animations (especially for the custom Glass NavBar).
2. **Retrofit/GSON**: Industry-standard for robust networking.
3. **Coil**: Lightweight image loading optimized for Compose.
4. **Dark Mode by Default**: Emphasize "modern" and "premium" vibe, reducing eye strain for long study sessions.
