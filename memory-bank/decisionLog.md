# Decision Log - Deutsch B1 Exam

---

## 2024-03-05: Jetpack Compose over XML
- **Decision**: Use Jetpack Compose exclusively.
- **Rationale**: Compose allows complex Glassmorphism layer stacking and spring-physics animations far more easily than XML.
- **Alternatives Rejected**: XML Views — too much boilerplate for the required animation fidelity.

---

## 2024-03-05: Static Data Models for Initial Launch
- **Decision**: Start with static lists in `ExamData.kt` and `LearnData.kt`.
- **Rationale**: Faster initial development; all content available offline immediately.
- **Status**: ⚠️ To be superseded by Room DB in Phase 2 for user progress tracking. Static content (exam questions, vocab lists) will remain static for performance.

---

## 2024-03-05: Retrofit/GSON for Networking
- **Decision**: Industry-standard Retrofit 2 + OkHttp stack for external API calls.
- **Rationale**: Low boilerplate, high reliability for the small number of endpoints required.

---

## 2024-03-06: Memory Bank Implementation
- **Decision**: Implement a comprehensive `memory-bank/` directory.
- **Rationale**: Single source of truth for architectural patterns and design tokens across AI-assisted development sessions.

---

## 2024-03-06: Room DB over DataStore for Progress Persistence
- **Decision**: Use Room (SQLite) for all user progress, not DataStore/SharedPreferences.
- **Rationale**: User progress data is relational (exam → part → score → timestamp). Room's DAO pattern and structured queries are a much better fit than key-value stores. DataStore will only be used for simple boolean/string settings (e.g. theme preference).
- **Alternatives Rejected**: DataStore — not suited for multi-entity relational data. Firebase — overkill and adds network dependency; the app should remain offline-first.

---

## 2024-03-06: ExoPlayer (Media3) over MediaPlayer for Audio
- **Decision**: Use `androidx.media3:media3-exoplayer` for all audio playback in HoerenScreen.
- **Rationale**: MediaPlayer has a complex state machine and documented memory leak issues in Compose. Media3/ExoPlayer handles Compose lifecycle correctly with `DisposableEffect`, supports asset URIs natively, and the seek bar is far easier to implement.
- **Alternatives Rejected**: Android `MediaPlayer` — lifecycle management is brittle; not recommended for Compose apps as of 2024.

---

## 2024-03-06: Sealed `ApiResult<T>` for Network Error Handling
- **Decision**: Wrap all Retrofit calls in a `sealed class ApiResult<T> { Success, Error }`.
- **Rationale**: Current implementation crashes or goes blank on network errors. A sealed result type forces every call site to handle both success and failure states explicitly. This pattern is idiomatic in Kotlin and pairs cleanly with Compose UI state.
- **Alternatives Rejected**: Kotlin `Result<T>` — less expressive for attaching error messages. Raw try/catch at UI layer — mixes concerns.

---

## 2024-03-06: JSON Assets (Lazy Load) for New Content Modules
- **Decision**: New content modules (Geschichten stories, Grammar Drills, Spiele sets) will be stored as JSON files in `assets/` and parsed at runtime, NOT added to Kotlin static objects.
- **Rationale**: The existing `ExamData.kt` and `LearnData.kt` approach is already straining compile times and APK size. Adding 800+ vocab cards, 10 stories, 15 grammar topics, and 5 game sets as Kotlin objects would make this unsustainable. JSON assets are still offline-first but are loaded lazily on demand.
- **Alternatives Rejected**: More static Kotlin objects — scalability ceiling already hit. Cloud database — conflicts with offline-first requirement.

---

## 2024-03-06: No Hilt/DI for Room DB Access
- **Decision**: Use a simple `object DatabaseProvider` (singleton) for Room DB access instead of introducing Hilt dependency injection.
- **Rationale**: The app uses mostly screen-level state with state hoisting, not ViewModels. Introducing Hilt would require a large architectural refactor (ViewModelFactory, Hilt annotations on every screen) that is out of scope for this sprint. A `DatabaseProvider` singleton is sufficient for now.
- **Alternatives Rejected**: Hilt — large boilerplate cost for limited benefit at current scale. Koin — same concern; not worth adding a new DI framework mid-sprint.
- **Future Note**: If the app grows to 10+ screens with complex async state, migrate to Hilt + ViewModels.

---

## 2024-03-06: Unified Word Vault Integration
- **Decision**: Centralize all word-saving functionality (Stories, Dictionary, Exams) into a single `SavedWordDao`.
- **Rationale**: Previously, each feature thought about "saving" differently. A unified `SavedWord` entity allows the `WordVaultScreen` to serve as a holistic personal dictionary regardless of where the word was discovered.
- **Implementation**: The story reader and dictionary screens now share the exact same `SavedWord` persistence logic.

---

## 2024-03-06: Standardized Shimmer UI Patterns
- **Decision**: Adopt a centralized `ShimmerItem` and `shimmerBrush()` for all loading states.
- **Rationale**: To maintain the premium "wow factor," blank loading screens or standard progress bars were rejected. Shimmers maintain layout stability and visual continuity during async operations (Translation API, DB aggregations).

---

## 2024-03-06: Moved Performance-Critical Aggregations to SQL
- **Decision**: Shifted stats calculation logic (total hours, unique days) from Kotlin `map/reduce` to Room/SQL `@Query` methods.
- **Rationale**: As the `StudySession` table grows to hundreds of entries, calculating stats in-memory on the UI thread would cause frame drops. SQL's `SUM`, `COUNT`, and `DISTINCT` on the background dispatcher ensure the `ProgressDashboard` remains fluid.

---

## 2024-03-06: Comprehensive String Extraction to XML
- **Decision**: Perform a complete pass to migrate hardcoded UI labels to `res/values/strings.xml`.
- **Rationale**: Essential for long-term maintainability and potential localization. Also standardizes button labels (e.g., "Zurück", "Weiter") across disparate modules (Exams vs Stories).
