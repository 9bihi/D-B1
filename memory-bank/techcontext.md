# Tech Context - Deutsch B1 Exam
## Version 2.2

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin 1.9.0+ |
| UI | Jetpack Compose + Material3 |
| Architecture | Single Activity, State Hoisting |
| Navigation | `androidx.navigation:navigation-compose` |
| HTTP (API tools) | Raw `OkHttpClient` (NOT Retrofit interface) |
| HTTP (other) | Retrofit 2 + OkHttp 4 (for any remaining Retrofit calls) |
| JSON parsing | GSON + `org.json.JSONArray` (for Google gtx response) |
| Offline data | JSON assets via `AssetLoader` |
| Persistence | Room DB |
| Audio | Media3 ExoPlayer |
| Images | Coil |
| Settings | DataStore Preferences |
| Animations | `AnimatedContent`, `animateFloatAsState`, `spring` |
| System UI | Accompanist `systemuicontroller` |

---

## 🌐 External APIs (v2.2 — All Verified Working)

### Translation
| Property | Value |
|---|---|
| API | Google Translate (gtx guest client) |
| Endpoint | `https://translate.googleapis.com/translate_a/single?client=gtx&sl={src}&tl={tgt}&dt=t&q={encoded}` |
| Method | GET |
| Key required | ❌ None |
| Response type | Raw JSON array — use `org.json.JSONArray`, not Gson |
| Parse path | `response[0][i][0]` for each segment i |
| Rate limit | None for normal usage |
| HTTP client | Raw `OkHttpClient` |

### Dictionary Lookup
| Property | Value |
|---|---|
| API | Free Dictionary API |
| Endpoint | `https://api.dictionaryapi.dev/api/v2/entries/de/{word}` |
| Method | GET |
| Key required | ❌ None |
| Response type | `List<DictApiEntry>` — standard Gson |
| 404 handling | `ApiResult.Error("Kein Eintrag gefunden")` |
| Rate limit | None documented |
| HTTP client | Raw `OkHttpClient` |

### Dictionary Browse
| Property | Value |
|---|---|
| Source | German-Words-Library (Jonny-exe) |
| File | `assets/dictionary/words_5000.json` |
| Format | `List<String>` JSON array |
| Network | ❌ None (fully offline) |

### Verb Conjugation
| Property | Value |
|---|---|
| Source | Custom bundled JSON |
| File | `assets/verbs/conjugations.json` |
| Coverage | 200 B1-level German verbs, 5 tenses each |
| Network | ❌ None (fully offline) |
| Access | `VerbRepository.searchVerb(context, verb)` |

---

## ⚠️ Critical: Why Raw OkHttp (Not Retrofit) for API Tools

```kotlin
// ✅ CORRECT — use this pattern for Dictionary and Translation calls:
private val httpClient = OkHttpClient.Builder()
    .connectTimeout(15, TimeUnit.SECONDS)
    .readTimeout(15, TimeUnit.SECONDS)
    .build()

suspend fun translate(text: String, src: String, tgt: String): ApiResult<String> {
    return withContext(Dispatchers.IO) {
        try {
            val encoded = URLEncoder.encode(text, "UTF-8")
            val url = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=$src&tl=$tgt&dt=t&q=$encoded"
            val response = httpClient.newCall(Request.Builder().url(url).build()).execute()
            // ... parse JSONArray response
        } catch (e: Exception) { ApiResult.Error("...") }
    }
}

// ❌ DO NOT do this — @Url with different base URL domain causes malformed requests:
// @GET suspend fun translate(@Url url: String): Response<MyMemoryResponse>
```

---

## 🗃️ Room DB Schema

```
UserExamResult    (id, examProvider, examNumber, skill, score, totalQuestions, completedAt)
FlashcardProgress (id, cardId, deckId, mastered, lastReviewedAt)
SavedWord         (id, german, english, contextSentence, savedAt)
StudySession      (id, moduleType, durationSeconds, itemsCompleted, completedAt)
```

---

## 📁 Asset Structure

```
app/src/main/assets/
├── dictionary/
│   └── words_5000.json          ← German-Words-Library 5k list
├── verbs/
│   └── conjugations.json        ← 200 B1 verbs, all tenses (offline)
├── flashcards/
│   └── deck_<topic>.json        ← Phase 4
├── geschichten/
│   └── stories.json             ← Phase 4
├── grammar/
│   └── drills.json              ← Phase 4
└── spiele/
    └── games.json               ← Phase 4
```

---

## ⚡ Performance Constraints
- `AssetLoader` calls must run on `Dispatchers.IO` in `LaunchedEffect` — never on Main thread.
- `VerbRepository` caches the verb list in memory after first load — don't reload on every search.
- Room DAO queries must be `suspend fun` — never call on Main thread.
- ExoPlayer must be released in `DisposableEffect.onDispose { player.release() }`.
- API text input: 500ms debounce before firing requests — never fire on every keystroke.

---

## 🔒 Constraints
- **Offline-first**: Dictionary browse + verb conjugation work with zero network. Only translation and dictionary lookup require network.
- **No authentication**: No user accounts, no PII transmitted.
- **No Retrofit for API tools**: Use raw OkHttp to avoid base URL conflicts.
