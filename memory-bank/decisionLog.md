# Decision Log - Deutsch B1 Exam
## Version 2.2

---

## 2024-03-05: Jetpack Compose over XML
- **Decision**: Jetpack Compose exclusively.
- **Rationale**: Required for Glassmorphism + spring animations. XML is too verbose.

## 2024-03-05: Static Data Models for Initial Launch
- **Decision**: Static Kotlin objects in `ExamData.kt` / `LearnData.kt`.
- **Status**: ⚠️ Superseded by Room DB for progress tracking. Content stays static for speed.

## 2024-03-05: Retrofit/GSON for Networking
- **Decision**: Retrofit 2 + OkHttp 4.
- **Status**: ⚠️ API tools now use raw OkHttp directly (see 2024-03-06 entry below).

## 2024-03-06: Memory Bank Implementation
- **Decision**: `memory-bank/` directory for all project docs.
- **Rationale**: Single source of truth across AI-assisted development sessions.

## 2024-03-06: Room DB over DataStore
- **Decision**: Room (SQLite) for all user progress.
- **Rationale**: Relational data (exam→score→timestamp) doesn't fit key-value store.
- **Alternatives Rejected**: DataStore (wrong model), Firebase (breaks offline-first).

## 2024-03-06: ExoPlayer (Media3) over MediaPlayer
- **Decision**: `androidx.media3:media3-exoplayer`.
- **Rationale**: MediaPlayer has documented memory leak pattern in Compose. Media3 handles lifecycle via `DisposableEffect`.

## 2024-03-06: Sealed `ApiResult<T>`
- **Decision**: All network calls return `sealed class ApiResult<T> { Success, Error, Loading }`.
- **Rationale**: Forces UI to handle all states. No silent crashes or blank screens.

## 2024-03-06: JSON Assets for New Content Modules
- **Decision**: New modules stored as `assets/*.json`, loaded via `AssetLoader`.
- **Rationale**: Static Kotlin objects cause APK bloat and slow compile times at scale.

## 2024-03-06: No Hilt/DI — `DatabaseProvider` singleton
- **Decision**: Simple `object DatabaseProvider` for Room access.
- **Rationale**: App uses screen-level state hoisting. Hilt refactor is out of scope. Revisit in Phase 5.

---

## 2024-03-06: v2.2 — Raw OkHttp for API Tools (replaces Retrofit @Url approach)

- **Decision**: API tools (Translation, Dictionary lookup) call their endpoints via a raw `OkHttpClient`, not the Retrofit interface.
- **Rationale**: The app's Retrofit instance was built with a fixed base URL. Using `@Url` in the Retrofit interface with completely different domains (e.g. `translate.googleapis.com` when the base URL is `api.mymemory.translated.net`) prepends the base URL in certain Retrofit versions, producing malformed request URLs. This was the root cause of the "Keine Verbindung" error. Using raw OkHttp for these calls completely avoids this issue.
- **Alternatives Rejected**:
  - Multiple Retrofit instances (one per base URL) — excessive boilerplate for 3 simple GET/POST calls.
  - Fixing `@Url` handling — fragile; Retrofit's behavior with `@Url` and base URLs varies by version.
  - Keeping MyMemory — MyMemory itself is fine but the Retrofit integration was broken; easier to switch to Google gtx which is more reliable anyway.

---

## 2024-03-06: v2.2 — Google Translate gtx endpoint for Translation

- **Decision**: Translation uses `https://translate.googleapis.com/translate_a/single?client=gtx&sl={src}&tl={tgt}&dt=t&q={encoded}`.
- **Rationale**: No API key required. `client=gtx` grants guest access. This endpoint is used by dozens of well-known open-source Android translation apps. It is backed by Google's infrastructure and has no practical rate limit for normal app usage. The response is a raw JSON array (not a JSON object), which requires `JSONArray` parsing — trivial with Android's built-in `org.json` package.
- **Alternatives Rejected**:
  - MyMemory — good API, but the Retrofit integration was broken and it's easier to use gtx.
  - LibreTranslate public instance — unreliable; public community instances go offline frequently.
  - DeepL free tier — requires account registration; adds friction for app distribution.

---

## 2024-03-06: v2.2 — Fully Offline Verb Conjugation

- **Decision**: Drop the German Verbs API (Render.com) entirely. Replace with a bundled `assets/verbs/conjugations.json` covering 200 B1-relevant German verbs.
- **Rationale**: The Render.com free tier sleeps after 15 minutes of inactivity. Cold start time (30–60 seconds) reliably exceeds OkHttp's timeout. The app appeared broken to every user who opened the Verb Conjugation screen, because the error appeared before any visible loading state could render. For a study app, verb conjugation must work offline — students study on planes, in low-signal areas, etc.
- **Data size**: 200 verbs × 5 tenses × 6 persons × ~10 chars avg ≈ ~80 KB JSON. Acceptable.
- **Autocomplete**: Since all verbs are in memory, instant prefix-match autocomplete is trivially cheap.
- **Alternatives Rejected**:
  - German Verbs API on a paid hosting tier — adds ongoing cost and network dependency.
  - Generating conjugations algorithmically — German has too many irregular verbs at B1 level to rely on rules alone. A curated dataset is more reliable.

---

## 2024-03-06: v2.2 — DictionaryAPI.dev replaces Wiktionary REST

- **Decision**: Use `https://api.dictionaryapi.dev/api/v2/entries/de/{word}` for online dictionary lookup.
- **Rationale**: Wiktionary REST API failed for two reasons: (1) its response includes HTML markup within definition strings, causing Gson to produce garbled or failed parses; (2) compound German nouns (like "Abbaumaschinen") do not have entries in the English Wiktionary's `de` section, always returning an error for such words. DictionaryAPI.dev returns clean, flat JSON with no HTML, handles 404 correctly, and is free with no key required.
- **Trade-off acknowledged**: DictionaryAPI.dev provides English-language definitions for German words, not German-language definitions. For a B1 exam prep app with likely non-native German speakers, this is entirely appropriate — users need English translations of definitions.
- **Alternatives Rejected**: Wiktionary REST — HTML in definitions + compound word gaps. OpenThesaurus — synonyms only, no definitions.
