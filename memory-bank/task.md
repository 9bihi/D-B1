# Task List - Deutsch B1 Exam
## Version 2.0 — Integration & Bug-Fix Sprint

---

## 🚨 ACTIVE TASK
> Executing Phase 1 Critical Bug Fixes — starting with content population and error handling.

---

## 🔴 Phase 1 — Critical Bug Fixes (Block all new features until done)

- [ ] **[FIX-1.1a]** Add `lesenParts` content to `GoetheExam2` in `ExamData.kt` (3 parts, 5 MC each)
- [ ] **[FIX-1.1b]** Add `hoerenParts` content to `GoetheExam2` (3 parts with transcripts + MC)
- [ ] **[FIX-1.1c]** Add `schreibenTasks` to `GoetheExam2` (formal letter + informal message)
- [ ] **[FIX-1.1d]** Add `sprechenTasks` to `GoetheExam2` (3 topic cards: Familie, Arbeit, Freizeit)
- [ ] **[FIX-1.1e]** Repeat all above for `OesdExam2`
- [ ] **[FIX-1.2a]** Create `ResultSummaryScreen.kt` composable
- [ ] **[FIX-1.2b]** Wire `ResultSummaryScreen` at end of `LesenScreen` and `HoerenScreen`
- [ ] **[FIX-1.2c]** Register `Screen.ResultSummary` route in `AppNavGraph.kt`
- [ ] **[FIX-1.3a]** Create `sealed class ApiResult<T>` in `data/ApiRepository.kt`
- [ ] **[FIX-1.3b]** Wrap all Retrofit calls in try/catch returning `ApiResult`
- [ ] **[FIX-1.3c]** Render error state in `TranslationScreen.kt` (Snackbar + Retry button)
- [ ] **[FIX-1.3d]** Render error state in `DictionaryScreen.kt` (Snackbar + Retry button)
- [ ] **[FIX-1.3e]** Handle null/empty `body()` response — no more crashes
- [ ] **[FIX-1.4a]** Add `ContentCopy` IconButton to Translation result card
- [ ] **[FIX-1.4b]** Wire `ClipboardManager` copy action + show Toast confirmation

---

## 🟡 Phase 2 — Persistence Layer

- [ ] **[DB-2.1a]** Add Room dependencies to `libs.versions.toml` and `build.gradle.kts`
- [ ] **[DB-2.1b]** Create `UserExamResult` `@Entity` data class
- [ ] **[DB-2.1c]** Create `UserExamResultDao` with insert + query methods
- [ ] **[DB-2.1d]** Build `AppDatabase` singleton
- [ ] **[DB-2.1e]** Create `DatabaseProvider` object for DI-free access
- [ ] **[DB-2.2a]** Query exam results in `ModelltestSelectorScreen` and display checkmark/score badge
- [ ] **[DB-2.2b]** Persist result to DB after `ResultSummaryScreen` is shown

---

## 🟠 Phase 3 — Audio / ExoPlayer

- [ ] **[AUDIO-3.1a]** Add `media3-exoplayer` + `media3-ui` to dependencies
- [ ] **[AUDIO-3.1b]** Add `audioAssetPath` field to `HoerenPart` data class
- [ ] **[AUDIO-3.1c]** Create `AudioPlayerBar` composable (play/pause, seek, time)
- [ ] **[AUDIO-3.1d]** Initialise ExoPlayer in `HoerenScreen` with `DisposableEffect` lifecycle
- [ ] **[AUDIO-3.1e]** Add placeholder `.mp3` files to `assets/audio/` for all existing Hoeren parts
- [ ] **[AUDIO-3.1f]** Link audio asset paths in `ExamData.kt` for existing Modelltest 1 entries

---

## 🟢 Phase 4 — External Repo Integrations

### WordVault Flashcards (saqibroy/deutsch-b1-vokab + greyels/deutsch-b1-prep)
- [ ] **[INT-4.1a]** Create `Flashcard` data class + 10 topic decks × 30 cards in `FlashcardData.kt`
- [ ] **[INT-4.1b]** Build `FlashcardDeckScreen` (deck list with study button)
- [ ] **[INT-4.1c]** Build `FlashcardScreen` (study mode: swipe/tap to flip, track score)
- [ ] **[INT-4.1d]** Add `FlashcardProgressDao` + Room entity for mastered cards
- [ ] **[INT-4.1e]** Add "Flashcards" entry to Learn home category list

### Geschichten Module (MohammedDrissi/Deutsche-Geschichten-zum-Lesen)
- [ ] **[INT-4.2a]** Create `Geschichte` data class + `GeschichtenData.kt` with ≥10 stories
- [ ] **[INT-4.2b]** Build `GeschichtenListScreen` with level filter (A2 / B1)
- [ ] **[INT-4.2c]** Build `GeschichteReaderScreen` with tap-word vocab hints
- [ ] **[INT-4.2d]** Add comprehension MC questions after story end
- [ ] **[INT-4.2e]** Register Geschichten routes in `AppNavGraph.kt`
- [ ] **[INT-4.2f]** Add "Geschichten 📖" to Learn home

### Grammar Drill (MohammedDrissi/Grammar-mit-mir)
- [ ] **[INT-4.3a]** Create `GrammarRule` + `GrammarDrill` data classes
- [ ] **[INT-4.3b]** Port ≥15 grammar topics into `GrammarDrillData.kt`
- [ ] **[INT-4.3c]** Build `GrammarDrillScreen` (explanation card → drill exercises)
- [ ] **[INT-4.3d]** Track completion state per topic in Room DB
- [ ] **[INT-4.3e]** Hook Grammar Drill into existing Learn > Grammar entry point

### Word Vault Bookmarks (MohammedDrissi/WordVault-Vocabulary-Builder)
- [ ] **[INT-4.4a]** Create `SavedWord` Room entity + `SavedWordDao`
- [ ] **[INT-4.4b]** Add bookmark icon to `GeschichteReaderScreen`
- [ ] **[INT-4.4c]** Add bookmark icon to exam question rows
- [ ] **[INT-4.4d]** Add bookmark icon to `DictionaryScreen` results
- [ ] **[INT-4.4e]** Build `WordVaultScreen` (personal saved words list)
- [ ] **[INT-4.4f]** Add "Review Saved Words" → opens FlashcardScreen with personal deck

### Spiele / Games (deutschimalltag22-hash/sprachspiel-b1)
- [ ] **[INT-4.5a]** Create `WordPair` + `FillSentence` data classes and `SpielData.kt` with ≥5 sets
- [ ] **[INT-4.5b]** Build `SpielMenuScreen`
- [ ] **[INT-4.5c]** Build `WordMatchScreen` (tap-to-match game)
- [ ] **[INT-4.5d]** Build `FillBlankScreen` (fill-in-the-blank MC game)
- [ ] **[INT-4.5e]** Add "Spiele 🎮" to Learn home

### Thematic Phrase Enrichment (yunus-topal/Deutsch-Lernen)
- [ ] **[INT-4.6a]** Add 5 new topic categories to `LearnData.kt` (Reisen, Kochen, Gesundheit, Wohnen, Beziehungen)
- [ ] **[INT-4.6b]** Expand existing Arbeit and Familie themes with 15+ new phrases each
- [ ] **[INT-4.6c]** Add `exampleSentence` field to `ThemePhrase` data class

### Progress Dashboard (saqibroy/German-b1-learning-tracker)
- [ ] **[INT-4.7a]** Create `StudySession` Room entity + DAO
- [ ] **[INT-4.7b]** Log session after each completed activity (exams, flashcards, stories, drills)
- [ ] **[INT-4.7c]** Build `ProgressDashboardScreen` with streak, total time, completion %
- [ ] **[INT-4.7d]** Add Stats icon to Home screen top app bar

---

## 🔵 Phase 5 — Polish

- [ ] **[POLISH-5.1]** Create `ShimmerEffect` composable and apply to Translation + Dictionary screens
- [ ] **[POLISH-5.2]** Add TELC Modelltest 1 content (Lesen + Schreiben minimum) to `ExamData.kt`
- [ ] **[POLISH-5.3]** Extract all hardcoded UI strings to `res/values/strings.xml`
- [ ] **[POLISH-5.4]** Add Dark Theme variant toggle ("Full Black" vs "Deep Grey") via DataStore

---

## ✅ Completed Tasks
- [x] Create project structure and navigation (`AppNavGraph`, `Screen` sealed class)
- [x] Implement Glassmorphism theme and custom `FloatingGlassNavBar`
- [x] Create `ApiRepository` for external API calls (Retrofit + OkHttp)
- [x] Populate Goethe Modelltest 1 — all 4 skills (Lesen, Hoeren, Schreiben, Sprechen)
- [x] Populate ÖSD Modelltest 1 — all 4 skills
- [x] Set up Learn module with Grammar + Vocabulary categories + thematic phrase lists
- [x] Document project in `memory-bank/` directory
- [x] Map 8 external GitHub repos to integration targets (this sprint)