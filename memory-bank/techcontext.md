# Tech Context - Deutsch B1 Exam
## Version 2.1

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin 1.9.0+ |
| UI Framework | Jetpack Compose + Material3 |
| Architecture | Single Activity, Declarative UI, State Hoisting |
| Navigation | `androidx.navigation:navigation-compose` |
| Networking | Retrofit 2 + OkHttp 4 (with disk cache) |
| Serialization | GSON (Retrofit converter + JSON asset loading) |
| Persistence | Room DB |
| Audio | Media3 ExoPlayer |
| Image Loading | Coil (`io.coil-kt:coil-compose`) |
| Settings | DataStore Preferences (theme toggle) |
| Animations | `AnimatedContent`, `animateFloatAsState`, `spring` |
| System UI | Accompanist `systemuicontroller` |
| Icons | Material Icons Extended |

---

## 📚 Key Dependencies (`libs.versions.toml`)

```toml
[versions]
compose-bom = "2024.02.00"
navigation-compose = "2.7.7"
retrofit = "2.9.0"
okhttp = "4.12.0"
gson = "2.10.1"
coil = "2.5.0"
room = "2.6.1"
media3 = "1.3.0"
datastore = "1.0.0"

[libraries]
# Compose
androidx-compose-bom      = { group = "androidx.compose",    name = "compose-bom",               version.ref = "compose-bom" }
androidx-compose-ui       = { group = "androidx.compose.ui", name = "ui" }
androidx-compose-material3= { group = "androidx.compose.material3", name = "material3" }
androidx-navigation       = { group = "androidx.navigation",  name = "navigation-compose",        version.ref = "navigation-compose" }

# Networking
retrofit                  = { group = "com.squareup.retrofit2", name = "retrofit",                version.ref = "retrofit" }
retrofit-gson             = { group = "com.squareup.retrofit2", name = "converter-gson",          version.ref = "retrofit" }
okhttp                    = { group = "com.squareup.okhttp3",   name = "okhttp",                  version.ref = "okhttp" }
okhttp-logging            = { group = "com.squareup.okhttp3",   name = "logging-interceptor",     version.ref = "okhttp" }

# Room
androidx-room-runtime     = { group = "androidx.room", name = "room-runtime",   version.ref = "room" }
androidx-room-ktx         = { group = "androidx.room", name = "room-ktx",       version.ref = "room" }
androidx-room-compiler    = { group = "androidx.room", name = "room-compiler",  version.ref = "room" }

# Media3 / ExoPlayer
androidx-media3-exoplayer = { group = "androidx.media3", name = "media3-exoplayer", version.ref = "media3" }
androidx-media3-ui        = { group = "androidx.media3", name = "media3-ui",        version.ref = "media3" }

# DataStore
androidx-datastore        = { group = "androidx.datastore", name = "datastore-preferences", version.ref = "datastore" }

# Coil
coil-compose              = { group = "io.coil-kt", name = "coil-compose", version.ref = "coil" }
```

---

## 🏗️ Build Setup
- **Gradle**: Kotlin DSL (`.gradle.kts`)
- **Min SDK**: 26 (Android 8.0 Oreo)
- **Target SDK**: 34 (Android 14)
- **Signing**: `release.jks` via `local.properties`

---

## 🌐 External API Integrations (v2.1 — Verified & Working)

| Tool | API | Endpoint | Key | Free Quota | Notes |
|---|---|---|---|---|---|
| Dictionary Browse | German-Words-Library (bundled) | `assets/dictionary/words_5000.json` | None | ♾️ Offline | GPL-3.0; bundle once |
| Dictionary Lookup | Wiktionary REST API | `en.wiktionary.org/api/rest_v1/page/definition/{word}` | None | No hard limit | Returns `de` section |
| Verb Conjugation | German Verbs API | `german-verbs-api.onrender.com/german-verbs-api/{verb}` | None | No hard limit | Cold start ~30s on Render free tier |
| Translation (primary) | MyMemory | `api.mymemory.translated.net/get?q={text}&langpair={pair}` | None | 5,000 words/day | No key needed |
| Translation (fallback) | LibreTranslate | `libretranslate.com/translate` (POST) | Optional free key | Limited w/o key | Auto-triggered on MyMemory 403 |

### Retrofit Client Setup (with Cache)
```kotlin
object RetrofitClient {
    fun build(context: Context): Retrofit {
        val cache = Cache(File(context.cacheDir, "http_cache"), 100L * 1024 * 1024)
        val client = OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(HttpLoggingInterceptor().apply { level = Level.BASIC })
            .build()
        return Retrofit.Builder()
            .baseUrl("https://api.mymemory.translated.net/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
```
Note: Since the app calls 3 different base URLs, use `@Url` annotation in `ApiInterface` for absolute URLs, or create separate Retrofit instances per API.

### API Interface (Key Endpoints)
```kotlin
interface ApiInterface {
    // Translation — MyMemory
    @GET
    suspend fun translate(
        @Url url: String = "https://api.mymemory.translated.net/get",
        @Query("q") text: String,
        @Query("langpair") langpair: String
    ): Response<MyMemoryResponse>

    // Translation — LibreTranslate fallback
    @POST("https://libretranslate.com/translate")
    suspend fun translateLibre(@Body body: LibreTranslateRequest): Response<LibreTranslateResponse>

    // Dictionary — Wiktionary
    @GET
    suspend fun lookupWord(@Url url: String): Response<Map<String, List<WiktEntry>>>

    // Verb Conjugation
    @GET
    suspend fun conjugateVerb(@Url url: String): Response<VerbConjugation>
}
```

---

## 🗃️ Room DB Schema

```
UserExamResult
  id: Int (PK), examProvider: String, examNumber: Int,
  skill: String, score: Int, totalQuestions: Int, completedAt: Long

FlashcardProgress
  id: Int (PK), cardId: String, deckId: String,
  mastered: Boolean, lastReviewedAt: Long

SavedWord
  id: Int (PK), german: String, english: String,
  contextSentence: String, savedAt: Long

StudySession
  id: Int (PK), moduleType: String, durationSeconds: Int,
  itemsCompleted: Int, completedAt: Long
```

---

## ⚡ Performance Constraints
- **Animations**: Use `remember` on all `animateFloatAsState` / `animateDpAsState` calls. No recomposition triggers inside animation lambdas.
- **JSON Loading**: All `AssetLoader.load()` calls must run on `Dispatchers.IO` inside `LaunchedEffect` — never on Main thread.
- **Room Queries**: All DAO methods must be `suspend fun`. Call from `LaunchedEffect` or coroutine scope only.
- **ExoPlayer**: Always release in `DisposableEffect.onDispose { player.release() }`.
- **API Debouncing**: Minimum 500ms debounce on all text fields that trigger API calls (Dictionary, Translation). Never fire on every keystroke.
- **Verb API Cold Start**: Show a patient loading state with explicit "server starting up" message. Do not let the user think the app is broken.

---

## 🔒 Development Constraints
- **Offline-First**: All exam, learn, and dictionary browse content works without network. Only Wiktionary lookup, Verb Conjugation, and Translation require network.
- **No Authentication**: Local-only app. No user accounts, no PII transmitted or stored.
- **API Rate Limits**: Cache all API responses with OkHttp. MyMemory is 5,000 words/day — more than sufficient, but cache prevents hitting limits from repeated identical queries.