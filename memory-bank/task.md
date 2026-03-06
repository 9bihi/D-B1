# Task List - Deutsch B1 Exam
## Version 2.1 — API Overhaul + Integration Sprint

---

## 🚨 ACTIVE TASK
> **API Overhaul** — Fix all 3 broken API tools (Dictionary, Verb Conjugation, Translation) before any other new feature work.

---

## 🔴 Phase 0 — API Overhaul (CURRENT — Do Before Everything Else)

### RetrofitClient.kt
- [ ] **[API-0.0a]** Add OkHttp `Cache(100 MB)` to `RetrofitClient`
- [ ] **[API-0.0b]** Add `HttpLoggingInterceptor` (BASIC level) for debugging
- [ ] **[API-0.0c]** Configure Retrofit to support absolute `@Url` annotations for multi-base-URL calls

### New Data Models
- [ ] **[API-0.1a]** Create `WiktionaryDefinition` + `WiktEntry` data classes
- [ ] **[API-0.1b]** Create `VerbConjugation` data class (verb, auxiliary, tense maps)
- [ ] **[API-0.1c]** Create `MyMemoryResponse` + `LibreTranslateRequest/Response` data classes
- [ ] **[API-0.1d]** Create `TranslationLangPair` data class for the UI selector

### ApiRepository.kt — Replace All Broken Calls
- [ ] **[API-0.2a]** Remove Google Books Dictionary call entirely
- [ ] **[API-0.2b]** Add `suspend fun lookupWord(word: String): ApiResult<WiktionaryDefinition>`
  - `GET https://en.wiktionary.org/api/rest_v1/page/definition/{word}`
  - Parse `de[]` key from response
  - Return `ApiResult.Error("Kein Eintrag gefunden")` if `de` key absent
- [ ] **[API-0.2c]** Add `fun sanitizeVerbForApi(verb: String): String` (umlaut → ascii)
- [ ] **[API-0.2d]** Add `suspend fun conjugateVerb(verb: String): ApiResult<VerbConjugation>`
  - Sanitize verb first
  - `GET https://german-verbs-api.onrender.com/german-verbs-api/{verb}`
  - Handle 404 (verb not found) and timeout (cold start) gracefully
- [ ] **[API-0.2e]** Update `suspend fun translate(text, langpair): ApiResult<String>`
  - PRIMARY: `GET https://api.mymemory.translated.net/get?q={text}&langpair={pair}`
  - Check `responseStatus`: 200=success, 403=quota exceeded → trigger fallback
  - FALLBACK: `POST https://libretranslate.com/translate` with empty `api_key`
  - Expose `langpair` parameter (de|en, en|de, de|ar, de|fr, de|tr)

### Dictionary Screen (`ui/translation/DictionaryScreen.kt`)
- [ ] **[API-0.3a]** Download `German-Words-5000.json` → commit to `assets/dictionary/words_5000.json`
- [ ] **[API-0.3b]** Add **Browse tab**: load JSON via `AssetLoader`, searchable `LazyColumn` A–Z
- [ ] **[API-0.3c]** Add **Lookup tab**: text field → Wiktionary API → render `WiktEntry` list
- [ ] **[API-0.3d]** Show `partOfSpeech` chip (Verb / Nomen / Adjektiv) on each entry
- [ ] **[API-0.3e]** Show example sentences below each definition (collapsible)
- [ ] **[API-0.3f]** Add 🔖 Bookmark IconButton → `SavedWordDao.insert()`
- [ ] **[API-0.3g]** Add shimmer loading state + "Kein Eintrag gefunden" empty state

### Verb Conjugation Screen (`ui/translation/VerbConjugationScreen.kt`) — NEW
- [ ] **[API-0.4a]** Create `VerbConjugationScreen.kt` composable
- [ ] **[API-0.4b]** TextField for verb input + "Konjugieren" button
- [ ] **[API-0.4c]** Show cold-start warning card: *"Server startet… (~30 Sekunden)"* during first load
- [ ] **[API-0.4d]** Shimmer loading skeleton while API is responding
- [ ] **[API-0.4e]** Tense `TabRow`: Präsens | Präteritum | Perfekt | Konjunktiv II | Imperativ
- [ ] **[API-0.4f]** Each tab: 6-row conjugation table (Person | Form), Glass card styled
- [ ] **[API-0.4g]** Show auxiliary info badge ("sein" or "haben") next to verb title
- [ ] **[API-0.4h]** Error card for verb not found + Retry button
- [ ] **[API-0.4i]** Register `Screen.VerbConjugation` in `AppNavGraph.kt`
- [ ] **[API-0.4j]** Wire "Verb Conjugation" card on API Tools screen → `Screen.VerbConjugation`

### Translation Screen (`ui/translation/TranslationScreen.kt`)
- [ ] **[API-0.5a]** Add `LanguagePairSelector` dropdown with 5 pairs (de↔en, en↔de, de↔ar, de↔fr, de↔tr)
- [ ] **[API-0.5b]** Replace old LibreTranslate-only call with MyMemory primary + LibreTranslate fallback
- [ ] **[API-0.5c]** Show "Daily limit reached, using backup..." message on 403 fallback
- [ ] **[API-0.5d]** Add 📋 `ContentCopy` IconButton → `ClipboardManager` + Toast *"Kopiert!"*
- [ ] **[API-0.5e]** Add shimmer loading state
- [ ] **[API-0.5f]** Add character count indicator below input field
- [ ] **[API-0.5g]** Add `ErrorStateCard` with Retry button on network failure

---

## 🔴 Phase 1 — Critical Bug Fixes

- [ ] **[FIX-1.1a–e]** Populate all 4 skills for GoetheExam2 and OesdExam2
- [ ] **[FIX-1.2a–c]** Create `ResultSummaryScreen.kt` + wire at end of Lesen/Hoeren + register route
- [ ] **[FIX-1.3a–e]** `ApiResult<T>` sealed class across all API calls (partially done in Phase 0)
- [ ] **[FIX-1.4a–b]** Copy-to-clipboard in Translator (done in API-0.5d above)

---

## 🟡 Phase 2 — Persistence Layer (Room DB)

- [ ] **[DB-2.1a–e]** Room setup: entities, DAOs, AppDatabase, DatabaseProvider
- [ ] **[DB-2.2a–b]** Completion badges on ModelltestSelectorScreen

---

## 🟠 Phase 3 — Audio / ExoPlayer

- [ ] **[AUDIO-3.1a–f]** ExoPlayer in HoerenScreen + AudioPlayerBar composable + mp3 assets

---

## 🟢 Phase 4 — External Repo Integrations (New Modules)

- [ ] **[INT-4.1a–e]** WordVault Flashcards (saqibroy/deutsch-b1-vokab + greyels/deutsch-b1-prep)
- [ ] **[INT-4.2a–f]** Geschichten Module (MohammedDrissi/Deutsche-Geschichten-zum-Lesen)
- [ ] **[INT-4.3a–e]** Grammar Drill (MohammedDrissi/Grammar-mit-mir)
- [ ] **[INT-4.4a–f]** Word Vault Bookmarks (MohammedDrissi/WordVault-Vocabulary-Builder)
- [ ] **[INT-4.5a–e]** Spiele / Games (deutschimalltag22-hash/sprachspiel-b1)
- [ ] **[INT-4.6a–c]** Thematic phrase enrichment (yunus-topal/Deutsch-Lernen)
- [ ] **[INT-4.7a–d]** Progress Dashboard (saqibroy/German-b1-learning-tracker)

---

## 🔵 Phase 5 — Polish

- [ ] **[POLISH-5.1]** ShimmerEffect composable for all API screens (partially done in Phase 0)
- [ ] **[POLISH-5.2]** TELC Modelltest 1 content (Lesen + Schreiben)
- [ ] **[POLISH-5.3]** Extract all hardcoded strings to `strings.xml`
- [ ] **[POLISH-5.4]** Dark theme variant toggle (Full Black / Deep Grey) via DataStore

---

## ✅ Completed Tasks
- [x] Project structure, navigation (`AppNavGraph`, `Screen` sealed class)
- [x] Glassmorphism theme + custom `FloatingGlassNavBar`
- [x] `ApiRepository` initial setup (Retrofit + OkHttp)
- [x] Goethe Modelltest 1 — all 4 skills
- [x] ÖSD Modelltest 1 — all 4 skills
- [x] Learn module — Grammar + Vocabulary categories
- [x] Memory Bank v2.1 documentation
- [x] Map 8 external GitHub repos to integration targets
- [x] Identify and document 3 verified free replacement APIs