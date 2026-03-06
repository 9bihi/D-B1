# Implementation Plan - Deutsch B1 Exam
## Version 2.1 — API Overhaul + Integration Sprint

---

## 🔥 PHASE 0 — API Overhaul (DO THIS FIRST)

### Fix 0.1 — RetrofitClient with OkHttp Cache
**Goal**: All API calls go through a cached HTTP client, reducing quota consumption and improving offline resilience.
**File**: `network/RetrofitClient.kt`
**Steps**:
1. Add `Cache(File(context.cacheDir, "http_cache"), 100L * 1024 * 1024)` to `OkHttpClient.Builder()`.
2. Add `HttpLoggingInterceptor` at BASIC level for debug builds only (`if (BuildConfig.DEBUG)`).
3. Since the app calls 3 different base URLs, use a single Retrofit instance with `@Url` annotation on each endpoint for absolute URL calls (no base URL set, or set to `https://` as placeholder).
**Validation**: Make the same dictionary lookup twice → confirm second call is instant (served from cache).

---

### Fix 0.2 — Dictionary: German-Words-Library + Wiktionary
**Goal**: Replace the broken Google Books dictionary with a real, working German dictionary.
**Files**: `assets/dictionary/words_5000.json` (new asset), `data/ApiRepository.kt`, `ui/translation/DictionaryScreen.kt`

**Step 0.2.1 — Offline Word List**:
1. Download raw JSON from:
   `https://raw.githubusercontent.com/Jonny-exe/German-Words-Library/master/German-Words-5000.json`
2. Save as `app/src/main/assets/dictionary/words_5000.json`.
3. Create `AssetLoader.loadWordList(context): List<String>` — returns the JSON array as a sorted `List<String>`.

**Step 0.2.2 — Online Definition Lookup**:
1. Create `data class WiktEntry(partOfSpeech: String, definitions: List<String>, examples: List<String>)`.
2. Create `data class WiktionaryDefinition(word: String, entries: List<WiktEntry>)`.
3. In `ApiRepository`, add:
```kotlin
suspend fun lookupWord(word: String): ApiResult<WiktionaryDefinition> {
    return try {
        val url = "https://en.wiktionary.org/api/rest_v1/page/definition/${word.lowercase()}"
        val response = apiInterface.lookupWord(url)
        val deSection = response.body()?.get("de")
        if (deSection.isNullOrEmpty()) {
            ApiResult.Error("Kein Eintrag für \"$word\" gefunden.")
        } else {
            val entries = deSection.map { entry ->
                WiktEntry(
                    partOfSpeech = entry.partOfSpeech ?: "",
                    definitions = entry.definitions.map { it.definition.stripHtml() },
                    examples = entry.definitions.flatMap { it.examples ?: emptyList() }
                )
            }
            ApiResult.Success(WiktionaryDefinition(word, entries))
        }
    } catch (e: Exception) {
        ApiResult.Error("Verbindung fehlgeschlagen. Bitte erneut versuchen.")
    }
}
```
Note: Wiktionary returns HTML in definition strings — add a `String.stripHtml()` extension using `android.text.Html.fromHtml()`.

**Step 0.2.3 — Update DictionaryScreen**:
1. Add `TabRow` with two tabs: **📚 Wörterbuch** (Browse) | **🔍 Nachschlagen** (Lookup).
2. Browse tab: load `words_5000.json` on `LaunchedEffect(Unit)`. Display in `LazyColumn` with a search `TextField` at top that filters the list. On word tap → switch to Lookup tab and trigger definition fetch for that word.
3. Lookup tab: `TextField` for manual word input + "Suchen" button. Show `ApiResult` states:
   - Loading → `ShimmerEffect` (3 rows)
   - Success → list of `WiktEntry` cards (glass card per entry, `partOfSpeech` chip, definitions, examples)
   - Error → `ErrorStateCard` with retry button
4. Add 🔖 bookmark `IconButton` on each entry → `SavedWordDao.insert(german=word, english=definitions[0])`.
**Validation**: Search "arbeiten" → "verb" chip appears → definition in English shown → example sentence shown.

---

### Fix 0.3 — Verb Conjugation: German Verbs API
**Goal**: Build the Verb Conjugation screen from scratch with a real working free API.
**New File**: `ui/translation/VerbConjugationScreen.kt`
**Files**: `data/ApiRepository.kt`

**Step 0.3.1 — Data Model**:
```kotlin
data class VerbConjugation(
    val verb: String,
    val auxiliary: String,
    val praesens: Map<String, String>,
    val praeteritum: Map<String, String>,
    val perfekt: Map<String, String>,
    val konjunktiv2: Map<String, String>,
    val imperativ: Map<String, String>
)
```

**Step 0.3.2 — ApiRepository**:
```kotlin
fun sanitizeVerbForApi(verb: String): String = verb
    .lowercase().trim()
    .replace("ü", "ue").replace("ö", "oe")
    .replace("ä", "ae").replace("ß", "ss")

suspend fun conjugateVerb(verb: String): ApiResult<VerbConjugation> {
    return try {
        val sanitized = sanitizeVerbForApi(verb)
        val url = "https://german-verbs-api.onrender.com/german-verbs-api/$sanitized"
        val response = apiInterface.conjugateVerb(url)
        if (response.isSuccessful && response.body() != null) {
            ApiResult.Success(response.body()!!)
        } else {
            ApiResult.Error("Verb \"$verb\" nicht gefunden.")
        }
    } catch (e: Exception) {
        ApiResult.Error("Verbindung fehlgeschlagen. Server lädt möglicherweise noch (~30s).")
    }
}
```

**Step 0.3.3 — VerbConjugationScreen**:
1. `TextField` with hint "Verb eingeben (z.B. gehen)" + "Konjugieren" `Button`.
2. State: `var apiResult by remember { mutableStateOf<ApiResult<VerbConjugation>>(ApiResult.Loading) }` — initially show prompt card "Enter a verb above to start".
3. On button click:
   - Set state to `ApiResult.Loading` → show shimmer + glass info card: *"🔄 Server startet… bitte ~30 Sekunden warten"* (only on first load; detect with `var isFirstLoad by remember { mutableStateOf(true) }`).
   - Call `coroutineScope.launch { apiResult = repo.conjugateVerb(verb) }`.
4. Success state:
   - Show verb title (capitalized) + auxiliary badge ("sein" or "haben") in a glass header card.
   - `ScrollableTabRow` with 5 tabs: Präsens | Präteritum | Perfekt | Konjunktiv II | Imperativ.
   - Each tab body: a glass card with a 2-column table (Pronomen | Konjugation). Persons: `ich, du, er/sie/es, wir, ihr, sie/Sie`.
5. Error state: `ErrorStateCard("Verb nicht gefunden", "Retry")`.
6. Register `Screen.VerbConjugation` in `AppNavGraph.kt`.
7. On API Tools screen, wire "Verb Conjugation" card → `navController.navigate(Screen.VerbConjugation)`.
**Validation**: Type "schlafen" → tap Konjugieren → Präsens tab shows "ich schlafe, du schläfst..." → Perfekt tab shows "ich habe geschlafen".

---

### Fix 0.4 — Translation: MyMemory + Fallback + Language Selector
**Goal**: Replace broken LibreTranslate-only implementation with a reliable two-API chain and add language pair selection and copy functionality.
**File**: `ui/translation/TranslationScreen.kt`, `data/ApiRepository.kt`

**Step 0.4.1 — ApiRepository**:
```kotlin
data class TranslationLangPair(val displayName: String, val code: String, val emoji: String)

val supportedPairs = listOf(
    TranslationLangPair("Deutsch → English", "de|en", "🇩🇪→🇬🇧"),
    TranslationLangPair("English → Deutsch", "en|de", "🇬🇧→🇩🇪"),
    TranslationLangPair("Deutsch → العربية", "de|ar", "🇩🇪→🇸🇦"),
    TranslationLangPair("Deutsch → Français",  "de|fr", "🇩🇪→🇫🇷"),
    TranslationLangPair("Deutsch → Türkçe",  "de|tr", "🇩🇪→🇹🇷")
)

suspend fun translate(text: String, langpair: String): ApiResult<String> {
    return try {
        // PRIMARY: MyMemory
        val url = "https://api.mymemory.translated.net/get?q=${URLEncoder.encode(text, "UTF-8")}&langpair=$langpair"
        val resp = apiInterface.translate(url)
        when (resp.body()?.responseStatus) {
            200  -> ApiResult.Success(resp.body()!!.responseData.translatedText)
            403  -> translateWithLibre(text, langpair) // Fallback
            else -> ApiResult.Error("Übersetzung fehlgeschlagen (${resp.body()?.responseStatus}).")
        }
    } catch (e: Exception) {
        ApiResult.Error("Keine Verbindung. Bitte erneut versuchen.")
    }
}

private suspend fun translateWithLibre(text: String, langpair: String): ApiResult<String> {
    return try {
        val parts = langpair.split("|")
        val body = LibreTranslateRequest(q=text, source=parts[0], target=parts[1], format="text", api_key="")
        val resp = apiInterface.translateLibre(body)
        if (resp.isSuccessful) ApiResult.Success(resp.body()!!.translatedText)
        else ApiResult.Error("Tages-Limit erreicht und Backup fehlgeschlagen.")
    } catch (e: Exception) {
        ApiResult.Error("Alle Übersetzungsdienste nicht erreichbar.")
    }
}
```

**Step 0.4.2 — TranslationScreen**:
1. `LanguagePairSelector`: A horizontal scrollable `Row` of pill-shaped `FilterChip`s, one per `supportedPairs`. Glass styled, selected chip has accent color fill.
2. `TextField` for source text (multiline, max 300 chars), character counter `"${text.length}/300"`.
3. "Übersetzen" `Button` — disabled when text is blank.
4. 500ms debounce: use `LaunchedEffect(text) { delay(500); if (text.isNotBlank()) translate() }`.
5. Result card (glass card):
   - Translated text in large readable font.
   - Top-right: 📋 `IconButton(Icons.Default.ContentCopy)` → `clipboardManager.setText(AnnotatedString(result))` + `Toast.makeText(ctx, "Kopiert!", Toast.LENGTH_SHORT).show()`.
   - If fallback was used, show small info chip: *"Backup-Dienst verwendet"*.
6. Loading → `ShimmerEffect` over result card area.
7. Error → `ErrorStateCard` with "Erneut versuchen" button.
**Validation**:
- Switch to de|ar → translate "Danke" → Arabic text appears.
- Tap copy → paste into another app → correct translated text.
- Airplane mode → translate → graceful error card shown.

---

## 🔥 PHASE 1 — Critical Bug Fixes

### Fix 1.1 — Populate Modelltest 2 Content
*(same as v2.0 — unchanged)*

### Fix 1.2 — Result Summary Screen
*(same as v2.0 — unchanged)*

### Fix 1.3 — Sealed ApiResult (already established in Phase 0)
All `ApiResult<T>` patterns are now established via Phase 0. Apply the same `ErrorStateCard` + `ShimmerEffect` pattern to any remaining screens.

### Fix 1.4 — Copy to Clipboard
Done in Fix 0.4 (TranslationScreen).

---

## 🟡 PHASE 2 — Persistence Layer (Room DB)
*(same as v2.0 — unchanged)*

---

## 🟠 PHASE 3 — Audio / ExoPlayer
*(same as v2.0 — unchanged)*

---

## 🟢 PHASE 4 — External Repo Integrations
*(same as v2.0 — unchanged: Flashcards, Geschichten, Grammar Drill, WordVault, Spiele, LearnData enrichment, Progress Dashboard)*

---

## 🔵 PHASE 5 — Polish
*(same as v2.0 — unchanged)*

---

## 🧪 Testing Strategy
- **Phase 0 (APIs)**: Test each API with real device (not emulator for network tests).
  - Dictionary: search 5 words → definitions load correctly; airplane mode → Browse tab still works.
  - VerbConjugation: test `gehen`, `müssen`, `sein` → all tenses correct; test unknown word → error card.
  - Translation: test de→en, de→ar; test airplane mode → error card; test 403 scenario (mock or wait for quota).
- **Phase 1 (Bugs)**: Manual smoke tests per fixed screen.
- **Phase 2 (Room)**: `Room.inMemoryDatabaseBuilder` unit tests for all DAOs.
- **Phase 3 (Audio)**: Real device test with `.mp3` assets.
- **Phase 4 (Modules)**: Navigation + data appearance tests per new screen.
- **Phase 5 (Polish)**: Full regression pass on all screens.