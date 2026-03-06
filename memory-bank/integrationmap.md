# Integration Map - Deutsch B1 Exam
## Version 2.2

---

## 🔌 API Tools — Status After v2.2 Overhaul

| Tool | v2.0 (Broken) | v2.1 (Still Failing) | ✅ v2.2 (Working) |
|---|---|---|---|
| Online Dictionary | Google Books API (wrong use case) | Wiktionary REST (HTML parse crash + compound word 404s) | German-Words-Library JSON (offline browse) + DictionaryAPI.dev (online lookup, simple JSON) |
| Verb Conjugation | No endpoint wired | Render.com free tier (server sleeps, times out) | **Fully offline** — `assets/verbs/conjugations.json` with 200 B1 verbs |
| Translation | LibreTranslate (public instance, broken) | MyMemory via Retrofit `@Url` (construction bug) | **Google Translate gtx** via raw OkHttp (no key, highly reliable) |

---

## ✅ API 1 — Online Dictionary

### Why Previous Attempts Failed
- **v2.0**: Google Books returns books, not word definitions.
- **v2.1**: Wiktionary REST API returns HTML inside definition strings which crashed Gson deserialization. Also, compound German nouns (e.g. "Abbaumaschinen") have no `de` section in the English Wiktionary, returning an error even though the word is valid.

### ✅ v2.2 Solution: Two-Layer

#### Layer 1 — Offline Browse (zero network, instant)
**Source**: `Jonny-exe/German-Words-Library` 5,000-word JSON
**File**: `assets/dictionary/words_5000.json`
**Usage**: Loaded into memory once on first tab open. Searchable in real time (filter in memory). Tapping a word triggers an online definition lookup.

#### Layer 2 — Online Lookup: DictionaryAPI.dev
**URL**: `https://api.dictionaryapi.dev/api/v2/entries/de/{word}`
**Key**: None required.
**Response**: Standard flat JSON array — no HTML, no nested complexity.
**404 handling**: Show "Kein Eintrag gefunden" — do NOT crash.
**Reliability**: Very high. This API is maintained, documented, and widely used.

---

## ✅ API 2 — Verb Conjugation

### Why Previous Attempt Failed
- **v2.1**: `german-verbs-api.onrender.com` is hosted on Render.com **free tier**. Free tier servers sleep after 15 minutes of inactivity. Cold start = 30–60 seconds, which exceeds OkHttp's default timeout. Result: the app always shows a timeout error unless someone happened to use the API in the last 15 minutes.

### ✅ v2.2 Solution: Fully Offline JSON Asset
**File**: `assets/verbs/conjugations.json`
**Coverage**: 200 most common B1-level German verbs — all verbs that appear in Goethe, ÖSD, and TELC B1 exams.
**Tenses**: Präsens, Präteritum, Perfekt, Konjunktiv II, Imperativ.
**Features**: Autocomplete suggestions while typing (prefix match from loaded list).
**Network required**: None. Works completely offline.
**Fallback for unknown verbs**: Show a helpful "not found" message with examples of supported verbs.

---

## ✅ API 3 — Translation

### Why Previous Attempt Failed
- **v2.1**: MyMemory was being called via Retrofit with a `@Url` annotation. When Retrofit is built with a fixed base URL (e.g. `https://api.mymemory.translated.net/`), using `@Url` with a completely different domain (as used for the verb and dictionary APIs) causes the base URL to be prepended incorrectly in some Retrofit versions, resulting in malformed URLs that return connection errors.

### ✅ v2.2 Solution: Google Translate gtx via Raw OkHttp

**Endpoint**:
```
GET https://translate.googleapis.com/translate_a/single?client=gtx&sl={source}&tl={target}&dt=t&q={urlEncoded}
```

**Key**: None. The `client=gtx` parameter grants guest access.
**Rate limits**: Generous; practically no limit for normal app usage.
**Response**: Raw JSON array (not a JSON object). Must be parsed with `JSONArray`, not Gson object mapping.
**Reliability**: Extremely high. This is backed by Google's infrastructure. The same endpoint powers dozens of open-source translation tools.

**Parse logic**:
```kotlin
val jsonArray = JSONArray(responseBody)
val sb = StringBuilder()
val translations = jsonArray.getJSONArray(0)
for (i in 0 until translations.length()) {
    val segment = translations.getJSONArray(i)
    if (!segment.isNull(0)) sb.append(segment.getString(0))
}
val result = sb.toString().trim()
```

**HTTP call**: Done via raw `OkHttpClient` (NOT Retrofit interface) to avoid base URL conflicts.

**Why not MyMemory anymore**: MyMemory is a perfectly fine API but the Retrofit setup in this project is causing URL construction issues. Using raw OkHttp for all three API tools (Dictionary, Translation) is cleaner and avoids the multi-base-URL problem entirely.

---

## 🔧 Icon Bug — Provider Skill Selector Screen

### What's broken (confirmed in screenshots)
The `ProviderSkillSelectorScreen` (skill selection: Lesen/Hören/Schreiben/Sprechen) shows a **white square** icon in the provider header card. The `ExamProviderListScreen` correctly shows real logos. They use different icon rendering paths.

### Fix: Consistent icon mapping
```kotlin
// Add to ExamProvider enum or companion object:
@DrawableRes
fun ExamProvider.toIconRes(): Int = when (this) {
    ExamProvider.GOETHE -> R.drawable.ic_goethe
    ExamProvider.OESD   -> R.drawable.ic_oesd
    ExamProvider.TELC   -> R.drawable.ic_telc
}

fun ExamProvider.toBrandColor(): Color = when (this) {
    ExamProvider.GOETHE -> Color(0xFF00A550)
    ExamProvider.OESD   -> Color(0xFF0070C0)
    ExamProvider.TELC   -> Color(0xFFE2001A)
}
```

Use `painterResource(provider.toIconRes())` in the header card on `ProviderSkillSelectorScreen` — same as `ExamProviderListScreen`.

---

## External GitHub Repositories — Integration Plan (unchanged)

| Repo | Target |
|---|---|
| `greyels/deutsch-b1-prep` | FlashcardData vocab JSON |
| `MohammedDrissi/Deutsche-Geschichten-zum-Lesen` | Geschichten module |
| `MohammedDrissi/Grammar-mit-mir` | Grammar Drill feature |
| `MohammedDrissi/WordVault-Vocabulary-Builder` | Bookmark/save-word UX |
| `deutschimalltag22-hash/sprachspiel-b1` | Spiele section |
| `yunus-topal/Deutsch-Lernen` | LearnData thematic phrase enrichment |
| `saqibroy/deutsch-b1-vokab` | FlashcardData 800+ vocab source |
| `saqibroy/German-b1-learning-tracker` | Progress Dashboard schema |

---

## Module Dependency Graph (v2.2)

```
[DictionaryScreen]
    ├── Browse tab → AssetLoader("dictionary/words_5000.json") [OFFLINE]
    └── Lookup tab → raw OkHttp → api.dictionaryapi.dev [ONLINE]
                   → SavedWordDao.insert() [Room]

[VerbConjugationScreen]
    └── VerbRepository.searchVerb(context, verb) [OFFLINE ONLY]
        └── reads assets/verbs/conjugations.json once, caches in memory

[TranslationScreen]
    └── raw OkHttp → translate.googleapis.com/translate_a/single?client=gtx [ONLINE]
        ├── ClipboardManager (copy button)
        └── LanguagePairSelector (de↔en, en↔de, de↔ar, de↔fr, de↔tr)

[ExamScreens]
    └── ExamData.kt (static) + UserExamResultDao (Room)

[AppNavGraph]          → all routes
[DatabaseProvider]     → single Room instance
[AssetLoader]          → JSON asset parser
[RetrofitClient]       → NOT used for API tools in v2.2 (raw OkHttp used instead)
```
