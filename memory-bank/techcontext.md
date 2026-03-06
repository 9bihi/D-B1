# Tech Context - Deutsch B1 Exam
## Version 2.0

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin 1.9.0+ |
| UI Framework | Jetpack Compose + Material3 |
| Architecture | Single Activity, Declarative UI, State Hoisting |
| Navigation | `androidx.navigation:navigation-compose` |
| Networking | Retrofit 2 + OkHttp 4 |
| Serialization | GSON (Retrofit converter + JSON asset loading) |
| Persistence | **Room DB** (new in v2.0) |
| Audio | **Media3 ExoPlayer** (new in v2.0) |
| Image Loading | Coil (`io.coil-kt:coil-compose`) |
| Settings | **DataStore Preferences** (new — theme toggle) |
| Animations | `AnimatedContent`, `animateFloatAsState`, `spring` |
| System UI | Accompanist `systemuicontroller` |
| Icons | Material Icons Extended |

---

## 📚 Key Dependencies (libs.versions.toml)

```toml
[versions]
compose-bom = "2024.02.00"
navigation-compose = "2.7.7"
retrofit = "2.9.0"
okhttp = "4.12.0"
gson = "2.10.1"
coil = "2.5.0"
room = "2.6.1"        # NEW
media3 = "1.3.0"      # NEW
datastore = "1.0.0"   # NEW

[libraries]
# Compose
androidx-compose-bom = { group = "androidx.compose", name = "compose-bom", version.ref = "compose-bom" }
androidx-compose-ui = { group = "androidx.compose.ui", name = "ui" }
androidx-compose-material3 = { group = "androidx.compose.material3", name = "material3" }
androidx-navigation-compose = { group = "androidx.navigation", name = "navigation-compose", version.ref = "navigation-compose" }

# Networking
retrofit = { group = "com.squareup.retrofit2", name = "retrofit", version.ref = "retrofit" }
retrofit-gson = { group = "com.squareup.retrofit2", name = "converter-gson", version.ref = "retrofit" }
okhttp = { group = "com.squareup.okhttp3", name = "okhttp", version.ref = "okhttp" }

# Room (NEW)
androidx-room-runtime = { group = "androidx.room", name = "room-runtime", version.ref = "room" }
androidx-room-ktx = { group = "androidx.room", name = "room-ktx", version.ref = "room" }
androidx-room-compiler = { group = "androidx.room", name = "room-compiler", version.ref = "room" }

# Media3 / ExoPlayer (NEW)
androidx-media3-exoplayer = { group = "androidx.media3", name = "media3-exoplayer", version.ref = "media3" }
androidx-media3-ui = { group = "androidx.media3", name = "media3-ui", version.ref = "media3" }

# DataStore (NEW)
androidx-datastore-preferences = { group = "androidx.datastore", name = "datastore-preferences", version.ref = "datastore" }

# Coil
coil-compose = { group = "io.coil-kt", name = "coil-compose", version.ref = "coil" }
```

---

## 🏗️ Build Setup
- **Gradle**: Kotlin DSL (`.gradle.kts`)
- **Min SDK**: 26 (Android 8.0 Oreo)
- **Target SDK**: 34 (Android 14)
- **Signing**: `release.jks` configured via `local.properties`

---

## 🌐 External API Integrations

| API | Purpose | Endpoint | Risk |
|---|---|---|---|
| MyMemory Translation | Free text translation | `https://api.mymemory.translated.net/get?q={text}&langpair={pair}` | 5,000 words/day free limit |
| Dictionary (Google Books / Web) | Word lookup | `https://www.googleapis.com/books/v1/volumes?q={query}` (inferred) | Rate limits; no API key = lower quota |

**Mitigation for API limits**: Implement response caching using OkHttp's built-in `Cache` (100 MB disk cache). Cache translation results keyed by `{text}+{langpair}` to avoid repeat API hits.

---

## 🗃️ Room DB Schema

```
UserExamResult
├── id: Int (PK autoGenerate)
├── examProvider: String    -- "GOETHE" | "OESD" | "TELC"
├── examNumber: Int         -- 1 | 2
├── skill: String           -- "LESEN" | "HOEREN" | etc.
├── score: Int
├── totalQuestions: Int
└── completedAt: Long       -- epoch millis

FlashcardProgress
├── id: Int (PK autoGenerate)
├── cardId: String          -- matches Flashcard.id from JSON
├── deckId: String
├── mastered: Boolean
└── lastReviewedAt: Long

SavedWord
├── id: Int (PK autoGenerate)
├── german: String
├── english: String
├── contextSentence: String
└── savedAt: Long

StudySession
├── id: Int (PK autoGenerate)
├── moduleType: String      -- "EXAM" | "FLASHCARD" | "GESCHICHTE" | "DRILL" | "GAME"
├── durationSeconds: Int
├── itemsCompleted: Int
└── completedAt: Long
```

---

## ⚡ Performance Constraints
- **Animations**: Heavy use of `spring` animations requires efficient recomposition. Ensure `remember` is used on all animation states and avoid triggering recomposition from within animated composables.
- **JSON Loading**: `AssetLoader` calls are synchronous by default. Wrap all `AssetLoader.load()` calls in `LaunchedEffect` or `withContext(Dispatchers.IO)` to avoid blocking the main thread.
- **Room Queries**: All DAO `@Query` methods returning `List<>` should be `suspend fun` called from `LaunchedEffect`. Avoid calling Room on the main thread.
- **ExoPlayer**: Must be released in `DisposableEffect.onDispose`. Failure to do so causes memory leaks and audio continuing after navigation.

---

## 🔒 Development Constraints
- **Offline-First**: All exam and learning content must be available without network. Only Translation and Dictionary tools require network; all other screens work fully offline.
- **API Rate Limits**: MyMemory is rate-limited. Cache responses aggressively. Do NOT fire an API request on every keystroke — debounce text input by 500ms before triggering.
- **No Authentication**: The app is local-only with no user accounts in the current scope. No PII is collected or transmitted.
