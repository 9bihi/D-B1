# Task List - Deutsch B1 Exam
## Version 2.2

---

## 🚨 ACTIVE TASK
> **Fix 2 confirmed live bugs**: (1) Provider icon broken on skill selector screen. (2) All 3 API tools failing. Do these before any other work.

---

## 🔴 Phase 0 — CURRENT: Icon Fix + API Rewrite

### A. Provider Icon Fix (Quick win — do this first, ~30 min)
- [ ] **[ICON-A1]** Open `ExamProviderListScreen.kt` — identify exact drawable names and method used to render provider icons (`painterResource(R.drawable.ic_goethe)` etc.)
- [ ] **[ICON-A2]** Add `fun ExamProvider.toIconRes(): Int` extension mapping each provider to its drawable
- [ ] **[ICON-A3]** Add `fun ExamProvider.toBrandColor(): Color` extension (Goethe=`#00A550`, ÖSD=`#0070C0`, TELC=`#E2001A`)
- [ ] **[ICON-A4]** In `ProviderSkillSelectorScreen.kt`, find the provider header card and replace broken icon with `Image(painter = painterResource(provider.toIconRes()), ...)`
- [ ] **[ICON-A5]** Wrap icon in `Box` with `background(provider.toBrandColor())` and `clip(RoundedCornerShape(14.dp))`
- [ ] **[ICON-A6]** Test: tap all 3 providers — all show correct logo and brand color

### B. Translation Fix — Google Translate gtx via raw OkHttp
- [ ] **[TRANS-B1]** Add `private val httpClient = OkHttpClient.Builder().connectTimeout(15s).readTimeout(15s).build()` to `ApiRepository`
- [ ] **[TRANS-B2]** Add `suspend fun translate(text, sourceLang, targetLang): ApiResult<String>` using raw OkHttp
  - URL: `https://translate.googleapis.com/translate_a/single?client=gtx&sl={src}&tl={tgt}&dt=t&q={encoded}`
  - Parse: `JSONArray(body)[0]` → iterate segments → concatenate `[i][0]`
- [ ] **[TRANS-B3]** Add `data class LangPair(label, source, target, emoji)` and list of 5 pairs
- [ ] **[TRANS-B4]** Update `TranslationScreen.kt`:
  - [ ] Add `LazyRow` of `FilterChip`s for language pair selection
  - [ ] Input `TextField` multiline + `"${text.length}/300"` counter
  - [ ] "Übersetzen" `Button` (disabled when blank)
  - [ ] `ApiResult` states: Loading shimmer | Success card with copy button | Error card with retry
  - [ ] `ContentCopy` `IconButton` → `ClipboardManager.setText()` + Toast "Kopiert!"
- [ ] **[TRANS-B5]** Test de→en: "Guten Morgen" → "Good Morning". Test de→ar. Test airplane mode → error card.

### C. Verb Conjugation Fix — Fully Offline JSON
- [ ] **[VERB-C1]** Create directory `app/src/main/assets/verbs/`
- [ ] **[VERB-C2]** Create `conjugations.json` with **all 200 B1 verbs** (schema defined in implementation-plan.md). Must include at minimum: sein, haben, werden, können, müssen, wollen, sollen, dürfen, mögen, gehen, kommen, machen, sagen, wissen, sehen, sprechen, arbeiten, lernen, fahren, wohnen, kaufen, lesen, schreiben, hören, verstehen, denken, glauben, finden, zeigen, brauchen, helfen, geben, nehmen, bringen, stellen, legen, setzen, fragen, antworten, erklären, besuchen, reisen, warten, bleiben, heißen, leben, kennen, laufen, schlafen, essen, trinken, kochen, spielen, studieren, bezahlen, öffnen, schließen, anfangen, aufhören, einladen, empfehlen, vergessen, erinnern, passieren, gefallen, interessieren, entscheiden, versuchen, schaffen, erreichen + 130 more
- [ ] **[VERB-C3]** Create `data/VerbRepository.kt` with `loadVerbs(context)`, `searchVerb(context, query)`, `getSuggestions(context, prefix)`
- [ ] **[VERB-C4]** Create/update `VerbConjugationScreen.kt`:
  - [ ] TextField + "Konjugieren" button
  - [ ] Autocomplete suggestions dropdown (prefix match, 8 results max)
  - [ ] Initial empty state card with usage hint
  - [ ] Success: verb title + auxiliary badge + `ScrollableTabRow` (5 tenses) + conjugation table (6 rows)
  - [ ] Not-found state: helpful error with example verbs
- [ ] **[VERB-C5]** Ensure `Screen.VerbConjugation` is registered in `AppNavGraph.kt`
- [ ] **[VERB-C6]** Ensure "Verb Conjugation" card on API Tools screen navigates to `Screen.VerbConjugation`
- [ ] **[VERB-C7]** Test: "gehen" → all tenses correct. "müssen" → works (umlaut in infinitiv). "xyz" → not-found message. App offline → still works.

### D. Dictionary Fix — Offline Browse + DictionaryAPI.dev
- [ ] **[DICT-D1]** Download `German-Words-5000.json` from GitHub raw URL, save to `assets/dictionary/words_5000.json`
- [ ] **[DICT-D2]** Add `suspend fun lookupWord(word: String): ApiResult<List<DictApiEntry>>` to `ApiRepository` using raw OkHttp
  - URL: `https://api.dictionaryapi.dev/api/v2/entries/de/{word}`
  - Handle 404 gracefully: return `ApiResult.Error("Kein Eintrag gefunden")`
- [ ] **[DICT-D3]** Create `DictApiEntry`, `DictMeaning`, `DictDefinition` data classes
- [ ] **[DICT-D4]** Update `DictionaryScreen.kt`:
  - [ ] Tab 0 (Wörterbuch): load words_5000.json on `LaunchedEffect(Unit)`, show in `LazyColumn`, search TextField filters in memory
  - [ ] Tapping a word in Browse tab → switch to Tab 1 and auto-search that word
  - [ ] Tab 1 (Nachschlagen): TextField + Suchen button + `ApiResult` states (shimmer/entries/error)
  - [ ] Each entry: `partOfSpeech` chip + definition text + example sentence (italic, if available)
  - [ ] 🔖 bookmark icon on each entry card
- [ ] **[DICT-D5]** Test: Browse tab loads with no network. Search "Buch" in browse → filters. Lookup "machen" → definitions shown. Lookup "Abbaumaschinen" → graceful 404 message.

---

## 🔴 Phase 1 — Critical Content Bugs

- [ ] **[FIX-1.1]** Populate all 4 skills for GoetheExam2 and OesdExam2 in `ExamData.kt`
- [ ] **[FIX-1.2]** Create `ResultSummaryScreen.kt` and wire at end of Lesen/Hoeren screens
- [ ] **[FIX-1.3]** Ensure `ApiResult<T>` sealed class is applied to all remaining network calls

---

## 🟡 Phase 2 — Room DB Persistence
- [ ] Room setup: entities, DAOs, AppDatabase, DatabaseProvider
- [ ] Completion badges on ModelltestSelectorScreen

## 🟠 Phase 3 — Audio / ExoPlayer
- [ ] ExoPlayer in HoerenScreen + AudioPlayerBar + mp3 assets

## 🟢 Phase 4 — External Repo Integrations
- [ ] Flashcards, Geschichten, Grammar Drill, WordVault, Spiele, LearnData enrichment, Progress Dashboard

## 🔵 Phase 5 — Polish
- [ ] Shimmer refinement, TELC content, strings.xml, theme toggle

---

## ✅ Completed
- [x] Project structure, NavGraph, Screen sealed class
- [x] Glassmorphism theme + FloatingGlassNavBar
- [x] Goethe + ÖSD Modelltest 1 — all 4 skills
- [x] Learn module base (Grammar + Vocab categories)
- [x] ApiResult<T> sealed class established
- [x] Error cards + shimmer UI components built
- [x] TranslationScreen UI structure (langpair selector, copy button, char counter)
- [x] VerbConjugationScreen UI structure (tense tabs, conjugation table)
- [x] DictionaryScreen UI structure (browse + lookup tabs)
- [x] Memory Bank v2.2 updated
