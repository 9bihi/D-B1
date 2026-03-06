# Decision Log - Deutsch B1 Exam
## Version 2.1

---

## 2024-03-05: Jetpack Compose over XML
- **Decision**: Use Jetpack Compose exclusively.
- **Rationale**: Compose enables Glassmorphism layer stacking and spring-physics animations far more easily than XML.
- **Alternatives Rejected**: XML Views — too much boilerplate for required animation fidelity.

---

## 2024-03-05: Static Data Models for Initial Launch
- **Decision**: Start with static lists in `ExamData.kt` and `LearnData.kt`.
- **Rationale**: Faster initial dev; all content offline immediately.
- **Status**: ⚠️ Superseded by Room DB for user progress. Static content stays for performance.

---

## 2024-03-05: Retrofit/GSON for Networking
- **Decision**: Retrofit 2 + OkHttp stack.
- **Rationale**: Low boilerplate, high reliability, industry-standard.

---

## 2024-03-06: Memory Bank Implementation
- **Decision**: Maintain `memory-bank/` directory for all project docs.
- **Rationale**: Single source of truth for AI-assisted development sessions.

---

## 2024-03-06: Room DB over DataStore for Progress Persistence
- **Decision**: Room (SQLite) for all user progress data.
- **Rationale**: Progress is relational (exam → part → score → timestamp). DataStore is key-value only; not suited for multi-entity relational data.
- **Alternatives Rejected**: DataStore — wrong data model. Firebase — adds network dependency, breaks offline-first.

---

## 2024-03-06: ExoPlayer (Media3) over MediaPlayer for Audio
- **Decision**: `androidx.media3:media3-exoplayer` for HoerenScreen audio.
- **Rationale**: MediaPlayer has a complex state machine with documented memory leaks in Compose. Media3 handles Compose lifecycle correctly via `DisposableEffect`, natively supports asset URIs.
- **Alternatives Rejected**: Android `MediaPlayer` — brittle lifecycle, not recommended for Compose.

---

## 2024-03-06: Sealed `ApiResult<T>` for Network Error Handling
- **Decision**: All Retrofit calls return `sealed class ApiResult<T> { Success, Error, Loading }`.
- **Rationale**: Forces every call site to handle all states. Eliminates silent crashes and blank screens.
- **Alternatives Rejected**: Kotlin `Result<T>` — less expressive. Raw try/catch at UI layer — mixes concerns.

---

## 2024-03-06: JSON Assets (Lazy Load) for New Content Modules
- **Decision**: New modules (Geschichten, Grammar, Spiele, Flashcards) stored as `assets/*.json`.
- **Rationale**: Existing static Kotlin objects are already straining compile times. JSON assets are offline-first, loaded lazily, and keep the code clean.
- **Alternatives Rejected**: More static Kotlin objects — not scalable. Cloud DB — breaks offline-first.

---

## 2024-03-06: No Hilt/DI for Room DB Access
- **Decision**: `object DatabaseProvider` singleton — no Hilt.
- **Rationale**: The app uses screen-level state hoisting, not ViewModels. Hilt would require a large architectural refactor that is out of scope.
- **Future Note**: Introduce Hilt + ViewModels in Phase 5 if complexity demands it.

---

## 2024-03-06: Dictionary API — Wiktionary REST over Google Books
- **Decision**: Replace Google Books API with a two-layer approach:
  1. Bundled offline `German-Words-5000.json` (German-Words-Library) for browsing.
  2. Wiktionary REST API (`en.wiktionary.org/api/rest_v1/page/definition/{word}`) for live definitions.
- **Rationale**: Google Books is a book search engine, not a dictionary. It returned irrelevant results. Wiktionary is the largest free structured dictionary in the world, has a documented REST API, requires no API key, and has no hard rate limits. The bundled 5,000 word list provides instant offline browsing with no network dependency.
- **Alternatives Rejected**:
  - WiktAPI (wiktapi.dev) — requires self-hosting; not viable for a consumer Android app.
  - Free Dictionary API (dictionaryapi.dev) — English only; does not support German.
  - Google Books — incorrect use case (not a dictionary).

---

## 2024-03-06: Verb Conjugation API — German Verbs API (MIT)
- **Decision**: Use `https://german-verbs-api.onrender.com/german-verbs-api/{verb}` by JamesPartsafas.
- **Rationale**: Only free, open-source German verb conjugation API available. Covers 8,000 verbs, all tenses, reflexive verbs, auxiliary selection. MIT license. No API key required.
- **Known Limitation**: Hosted on Render.com free tier — 30–60 second cold start if server hasn't been accessed recently. Mitigation: Show an explicit "server starting up" message in the UI. After first request, subsequent calls are fast.
- **Alternatives Rejected**:
  - Morphisto — academic tool, no public REST API.
  - GermaNet — requires license from University of Tübingen; not a conjugation tool.
  - Manual conjugation rule engine — too much development effort; accuracy not guaranteed for irregular verbs.

---

## 2024-03-06: Translation API — MyMemory Primary + LibreTranslate Fallback
- **Decision**: Translation uses MyMemory as primary and LibreTranslate as automatic fallback.
- **Rationale**:
  - **MyMemory** (`api.mymemory.translated.net`): No API key, 5,000 words/day free, very stable, 30+ language pairs. This fully replaces the broken LibreTranslate-only implementation.
  - **LibreTranslate** as fallback: Triggered automatically only when MyMemory returns a 403 (quota exceeded). Provides resilience without forcing the user to self-host.
- **Alternatives Rejected**:
  - LibreTranslate public instance only — too unstable; public community instances frequently go offline or require keys.
  - DeepL API — free tier limited to 500,000 characters/month with required registration; adds friction.
  - Self-hosted LibreTranslate — impractical for a consumer Android app distributed via APK.
- **Multi-language Support**: The selector now exposes de↔en, en↔de, de↔ar, de↔fr, de↔tr. This reflects the primary language backgrounds of users preparing for the German B1 exam.

---

## 2024-03-06: OkHttp Disk Cache for API Responses
- **Decision**: Add 100 MB OkHttp disk cache to the Retrofit client.
- **Rationale**: Without caching, every repeated dictionary lookup, translation, or conjugation request fires a new network call, rapidly consuming the MyMemory daily quota and degrading performance on slow connections. The cache keyed by URL will serve identical queries instantly without hitting the network.
- **Implementation**: `Cache(File(context.cacheDir, "http_cache"), 100L * 1024 * 1024)` added to `OkHttpClient.Builder()`.