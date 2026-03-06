# Integration Map - Deutsch B1 Exam
## Version 2.1 — API Overhaul

---

## 🔌 API Tools — Current State & Fix

The app exposes 3 API-powered tools visible in the UI:
1. **Online Dictionary** — "Browse 5,000 German B1 words"
2. **Verb Conjugation** — "8,000 verbs · All tenses"
3. **Translation** — "Deutsch ↔ English · LibreTranslate"

All three had broken or unreliable endpoints. The section below documents the **verified, working, free replacement APIs** for each.

---

## ✅ API 1 — Online Dictionary

### Previous (Broken)
Was using Google Books API (`googleapis.com/books/v1/volumes?q=`) as a dictionary — incorrect use case, unreliable, and not a real dictionary API.

### ✅ Replacement: Two-Layer Dictionary Strategy

#### Layer 1 — Offline Browse (Bundled JSON Asset)
**Source**: `German-Words-Library` by Jonny-exe
**GitHub**: https://github.com/Jonny-exe/German-Words-Library
**Raw JSON (5,000 words)**:
```
https://raw.githubusercontent.com/Jonny-exe/German-Words-Library/master/German-Words-5000.json
```
**Format**: JSON array of German word strings, alphabetically sorted, full umlaut support.
**License**: GPL-3.0 (free for open-source projects).
**Integration**: Bundle as `assets/dictionary/words_5000.json`. Load into a searchable `LazyColumn`. Zero network required.

#### Layer 2 — Online Definition Lookup
**Source**: Wiktionary REST API (Wikimedia Foundation)
**Base URL**: `https://en.wiktionary.org/api/rest_v1/page/definition/{word}`
**Method**: GET — No API key, no rate limit for reasonable usage.

**Example**:
```
GET https://en.wiktionary.org/api/rest_v1/page/definition/arbeiten
```
**Response shape** (relevant part):
```json
{
  "de": [{
    "partOfSpeech": "verb",
    "definitions": [
      { "definition": "to work, to labor", "examples": ["Ich arbeite jeden Tag."] }
    ]
  }]
}
```
Extract: `de[].partOfSpeech`, `de[].definitions[].definition`, `de[].definitions[].examples`.
If `de` key absent → show "Kein Eintrag gefunden" empty state.

**Data Flow**:
```
DictionaryScreen
  ├── Browse tab → AssetLoader loads words_5000.json → searchable LazyColumn
  └── Lookup tab → user types word → 500ms debounce
                → GET wiktionary/rest_v1/page/definition/{word}
                → ApiResult<WiktionaryDefinition>
                → partOfSpeech badge + definition list + examples
                → 🔖 Bookmark → SavedWordDao.insert()
```

---

## ✅ API 2 — Verb Conjugation

### Previous (Broken)
No actual endpoint was wired. The Verb Conjugation card navigated to an empty or placeholder screen.

### ✅ Replacement: German Verb Conjugation API
**Project**: `JamesPartsafas/german-verbs-api`
**GitHub**: https://github.com/JamesPartsafas/german-verbs-api
**Live Endpoint**: `https://german-verbs-api.onrender.com/german-verbs-api`
**License**: MIT — free for all use including commercial.
**Coverage**: 8,000 most common German verbs. All tenses. Reflexive verbs. Auxiliary selection (haben/sein). All 6 grammatical persons.

**Request**:
```
GET https://german-verbs-api.onrender.com/german-verbs-api/{verb}
```
Umlaut rule: Replace ü→ue, ö→oe, ä→ae, ß→ss in the URL path before calling.
Example: `müssen` → `muessen`

**Example Response**:
```json
{
  "verb": "gehen",
  "auxiliary": "sein",
  "praesens":    { "ich": "gehe",  "du": "gehst",  "er/sie/es": "geht",  "wir": "gehen", "ihr": "geht",  "sie/Sie": "gehen" },
  "praeteritum": { "ich": "ging",  "du": "gingst", "er/sie/es": "ging",  "wir": "gingen","ihr": "gingt", "sie/Sie": "gingen" },
  "perfekt":     { "ich": "bin gegangen", ... },
  "konjunktiv2": { "ich": "ginge", ... },
  "imperativ":   { "du": "geh!", "ihr": "geht!" }
}
```

**Cold Start Warning**: This API runs on Render.com free tier. First request after inactivity takes 30–60 seconds. Show shimmer + text: *"Server starting up, please wait (~30s)…"* After first request, all subsequent calls are fast.

**Umlaut Sanitizer** (add to `ApiRepository`):
```kotlin
fun sanitizeVerbForApi(verb: String): String = verb
    .lowercase().trim()
    .replace("ü", "ue").replace("ö", "oe")
    .replace("ä", "ae").replace("ß", "ss")
```

**New Screen**: `ui/translation/VerbConjugationScreen.kt`
**New Data Model**: `data class VerbConjugation(verb, auxiliary, praesens, praeteritum, perfekt, konjunktiv2, imperativ)` where each tense is `Map<String, String>`.

**Data Flow**:
```
VerbConjugationScreen
  ├── TextField (verb input, e.g. "gehen")
  ├── "Konjugieren" Button → sanitize → ApiRepository.conjugateVerb()
  ├── ApiResult states:
  │     ├── Loading  → shimmer + cold-start warning card
  │     ├── Success  → TenseTabRow + conjugation table
  │     └── Error    → "Verb nicht gefunden" card + Retry button
  └── TenseTabRow tabs: Präsens | Präteritum | Perfekt | Konjunktiv II | Imperativ
        └── Each tab: 6-row table (Person | Konjugation)
```

---

## ✅ API 3 — Translation

### Previous (Broken)
Was referencing a LibreTranslate instance that required self-hosting or a paid API key. Public community LibreTranslate instances are unstable, frequently rate-limited, and often offline.

### ✅ Replacement: MyMemory (Primary) + LibreTranslate (Fallback)

#### Primary: MyMemory Translation API
**URL**: `https://api.mymemory.translated.net/get`
**Method**: GET — No API key required.
**Free Quota**: 5,000 words/day per IP — more than enough for a study app.
**Supported pairs**: de↔en, de↔ar, de↔fr, de↔tr, de↔es, and 30+ others.

**Request**:
```
GET https://api.mymemory.translated.net/get?q={encoded_text}&langpair=de|en
```

**Response**:
```json
{
  "responseStatus": 200,
  "responseData": { "translatedText": "I learn German", "match": 1.0 }
}
```

**Status Handling**:
```kotlin
when (body.responseStatus) {
    200  -> ApiResult.Success(body.responseData.translatedText)
    403  -> ApiResult.Error("Daily limit reached. Retrying with backup...", 403)
    else -> ApiResult.Error("Translation failed. Check your connection.", body.responseStatus)
}
```

#### Fallback: LibreTranslate (Public Instance)
Triggered automatically if MyMemory returns 403.
```
POST https://libretranslate.com/translate
Content-Type: application/json
Body: { "q": "text", "source": "de", "target": "en", "format": "text", "api_key": "" }
```
`api_key: ""` enables limited free usage on the public instance. For higher reliability, prompt the user to optionally enter a free LibreTranslate API key in app Settings.

**Language Pair Selector UI**:
The Translation screen must allow switching the direction and target language. Supported pairs to show in the dropdown:

| Display | langpair |
|---|---|
| 🇩🇪 → 🇬🇧 Deutsch → English | `de\|en` |
| 🇬🇧 → 🇩🇪 English → Deutsch | `en\|de` |
| 🇩🇪 → 🇸🇦 Deutsch → العربية | `de\|ar` |
| 🇩🇪 → 🇫🇷 Deutsch → Français | `de\|fr` |
| 🇩🇪 → 🇹🇷 Deutsch → Türkçe | `de\|tr` |

**Data Flow**:
```
TranslationScreen
  ├── LanguagePairSelector (de↔en default)
  ├── TextField (source text) → multiline, 500ms debounce
  ├── "Übersetzen" Button → ApiRepository.translate(text, langpair)
  │     ├── PRIMARY: MyMemory GET
  │     │     ├── 200 → ApiResult.Success
  │     │     └── 403 → FALLBACK: LibreTranslate POST
  │     └── Network error → ApiResult.Error → error card + retry
  ├── Result card:
  │     ├── Translated text (large, readable font)
  │     ├── 📋 ContentCopy IconButton → ClipboardManager.setText() + Toast "Kopiert!"
  │     └── Character count indicator
  └── Loading → shimmer skeleton
```

---

## 📊 API Summary Table

| Tool | API | Endpoint | Key Required | Free Quota | Reliability |
|---|---|---|---|---|---|
| Dictionary Browse | German-Words-Library (bundled) | Local `assets/` JSON | ❌ None | ♾️ Unlimited (offline) | ✅ Perfect |
| Dictionary Lookup | Wiktionary REST API | `en.wiktionary.org/api/rest_v1/page/definition/{word}` | ❌ None | No hard limit | ✅ Very High |
| Verb Conjugation | German Verbs API | `german-verbs-api.onrender.com/german-verbs-api/{verb}` | ❌ None | No hard limit | 🟡 Good (cold start ~30s) |
| Translation (primary) | MyMemory | `api.mymemory.translated.net/get` | ❌ None | 5,000 words/day | ✅ Very High |
| Translation (fallback) | LibreTranslate | `libretranslate.com/translate` | Optional (free tier) | Limited w/o key | 🟡 Good |

---

## 🔧 Implementation Checklist — API Overhaul

### ApiRepository.kt Changes
- [ ] Remove Google Books Dictionary call
- [ ] Add `suspend fun lookupWord(word: String): ApiResult<WiktionaryDefinition>`
  - Endpoint: `GET https://en.wiktionary.org/api/rest_v1/page/definition/{word}`
  - Parse `de` array from response
- [ ] Add `suspend fun conjugateVerb(verb: String): ApiResult<VerbConjugation>`
  - Sanitize verb via `sanitizeVerbForApi()` before calling
  - Endpoint: `GET https://german-verbs-api.onrender.com/german-verbs-api/{sanitized}`
- [ ] Update `suspend fun translate(text: String, langpair: String): ApiResult<String>`
  - Primary: MyMemory GET
  - Fallback on 403: LibreTranslate POST
  - Expose `langpair` parameter (currently hardcoded to de|en)
- [ ] Add OkHttp `Cache(File(context.cacheDir, "http_cache"), 100L * 1024 * 1024)` to Retrofit client

### New Data Models
- [ ] `data class WiktionaryDefinition(word: String, entries: List<WiktEntry>)`
- [ ] `data class WiktEntry(partOfSpeech: String, definitions: List<String>, examples: List<String>)`
- [ ] `data class VerbConjugation(verb: String, auxiliary: String, praesens: Map<String, String>, praeteritum: Map<String, String>, perfekt: Map<String, String>, konjunktiv2: Map<String, String>, imperativ: Map<String, String>)`
- [ ] `data class TranslationRequest(langpair: String, displayName: String, flagEmoji: String)`

### New/Updated Screens
- [ ] `DictionaryScreen.kt` — Add Browse tab (offline JSON) + Lookup tab (Wiktionary)
- [ ] `VerbConjugationScreen.kt` — Build from scratch (search + tense tabs + conjugation table)
- [ ] `TranslationScreen.kt` — Add language pair selector + copy button + fallback logic

### Assets
- [ ] Download and commit `assets/dictionary/words_5000.json` from German-Words-Library

### Navigation
- [ ] Ensure `Screen.VerbConjugation` route exists in `AppNavGraph.kt`
- [ ] Wire "Verb Conjugation" card on API Tools screen to `Screen.VerbConjugation`
- [ ] Ensure "Translation" card routes to updated `TranslationScreen`
- [ ] Ensure "Online Dictionary" card routes to updated `DictionaryScreen`

---

## External GitHub Repositories — Content Integration Plan

### Repo 1: `greyels/deutsch-b1-prep`
**Integration**: `data/FlashcardData.kt` — B1 thematic vocab lists → flashcard decks

### Repo 2: `MohammedDrissi/Deutsche-Geschichten-zum-Lesen`
**Integration**: New Geschichten module — graded reading stories

### Repo 3: `MohammedDrissi/Grammar-mit-mir`
**Integration**: Grammar Drill feature — interactive grammar exercises

### Repo 4: `MohammedDrissi/WordVault-Vocabulary-Builder`
**Integration**: Word Vault — save-word bookmark system

### Repo 5: `deutschimalltag22-hash/sprachspiel-b1`
**Integration**: Spiele section — Word Match + Fill-Blank games

### Repo 6: `yunus-topal/Deutsch-Lernen`
**Integration**: `LearnData.kt` thematic phrase enrichment

### Repo 7: `saqibroy/deutsch-b1-vokab`
**Integration**: 800+ B1 vocab → FlashcardData JSON decks

### Repo 8: `saqibroy/German-b1-learning-tracker`
**Integration**: Room DB schema + Progress Dashboard screen

---

## Internal Module Dependency Graph (v2.1)

```
[UI Modules]
    ├── ExamScreens           → ExamData.kt (static) + UserExamResultDao (Room)
    ├── FlashcardScreens      → FlashcardData.kt (JSON asset) + FlashcardProgressDao
    ├── GeschichtenScreens    → GeschichtenData.kt (JSON asset) + SavedWordDao
    ├── GrammarDrillScreen    → GrammarDrillData.kt (JSON asset)
    ├── SpielScreens          → SpielData.kt (JSON asset)
    ├── WordVaultScreen       → SavedWordDao (Room) → FlashcardScreen
    ├── DictionaryScreen      → assets/dictionary/words_5000.json (browse)
    │                         → ApiRepository → Wiktionary REST (lookup)
    │                         → SavedWordDao (Room)
    ├── VerbConjugationScreen → ApiRepository → German Verbs API
    ├── TranslationScreen     → ApiRepository → MyMemory → LibreTranslate fallback
    │                         → ClipboardManager (copy)
    └── ProgressDashboard     → All DAOs (aggregate read-only)

[AppNavGraph]    → all routes
[DatabaseProvider] → single Room instance
[AssetLoader]    → JSON assets
[ApiRepository]  → all network calls → ApiResult<T>
[RetrofitClient] → OkHttp with 100MB disk cache
```