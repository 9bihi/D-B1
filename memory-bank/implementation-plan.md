# Implementation Plan - Deutsch B1 Exam
## Version 2.0 — Integration + Bug-Fix Sprint

---

## 🔥 PHASE 1 — Critical Bug Fixes (Do First, Nothing Else)

### Fix 1.1 — Populate Modelltest 2 Content
**Goal**: All 4 skill screens for Goethe 2 and ÖSD 2 must show real content.
**Files**: `data/ExamData.kt`
**Steps**:
1. Add `LesenPart` list to `GoetheExam2`: 3 parts with 5 MC questions each (mirror Goethe 1 structure).
2. Add `HoerenPart` list to `GoetheExam2`: 3 parts with audio transcript strings and MC questions.
3. Add `SchreibenTask` to `GoetheExam2`: formal letter prompt + informal message prompt.
4. Add `SprechenTask` to `GoetheExam2`: presentation card topics (Familie, Arbeit, Freizeit).
5. Repeat steps 1–4 for `OesdExam2`.
**Validation**: Navigate to both Modelltest 2 screens → all 4 skill buttons resolve to non-empty screens.

---

### Fix 1.2 — Result Summary Screen
**Goal**: After finishing any Lesen or Hoeren part, show a summary of score and answer review.
**New File**: `ui/exam/ResultSummaryScreen.kt`
**Steps**:
1. Create `ResultSummaryScreen` composable accepting `(score: Int, total: Int, questions: List<QuestionResult>)`.
2. Display a large score number (e.g. "7 / 10"), a progress arc, and a list of Q&A review rows.
3. Each row shows: question text, user's answer (colour-coded red/green), and the `explanation` field.
4. Add "Retry" and "Back to Exams" buttons.
5. Navigate to `ResultSummaryScreen` at the end of `LesenScreen` and `HoerenScreen` instead of popping the stack.
6. Register route `Screen.ResultSummary` in `AppNavGraph.kt`.
**Validation**: Complete a reading part → confirm summary screen appears with correct stats.

---

### Fix 1.3 — Silent API Error Handling
**Goal**: The Translation and Dictionary tools must surface errors to the user, not crash or show nothing.
**Files**: `data/ApiRepository.kt`, `ui/translation/TranslationScreen.kt`, `ui/translation/DictionaryScreen.kt`
**Steps**:
1. Wrap all Retrofit calls in `try/catch`. Return a `sealed class ApiResult<T> { data class Success<T>; data class Error(val message: String) }`.
2. In `TranslationScreen`: render a `Snackbar` or a red error card when `ApiResult.Error` is received.
3. In `DictionaryScreen`: same pattern.
4. Add a "Retry" button that re-fires the request.
5. Add a check for empty/null `body()` — currently crashes if the API returns an unexpected format.
**Validation**: Enable airplane mode → trigger a translation → confirm a graceful error message is shown.

---

### Fix 1.4 — Copy to Clipboard in Translator
**Goal**: Users can copy translation output with one tap.
**File**: `ui/translation/TranslationScreen.kt`
**Steps**:
1. Retrieve `LocalClipboardManager.current` in the composable.
2. Add an `IconButton` with `Icons.Default.ContentCopy` aligned to the top-right of the result card.
3. On click: `clipboardManager.setText(AnnotatedString(translatedText))` + show a short `Toast("Copied!")`.
**Validation**: Translate a phrase → tap copy icon → paste into another app → confirm correct text.

---

## 🟡 PHASE 2 — Persistence Layer (Room DB)

### Fix 2.1 — Room DB Setup
**Goal**: User exam scores and completed exams persist across app restarts.
**New Files**: `data/db/AppDatabase.kt`, `data/db/UserExamResultDao.kt`, `data/db/entities/UserExamResult.kt`
**Steps**:
1. Add Room dependency to `libs.versions.toml`: `androidx.room:room-runtime`, `room-ktx`, `room-compiler`.
2. Define `@Entity` `UserExamResult(id, examProvider, examNumber, skill, score, totalQuestions, completedAt: Long)`.
3. Define `UserExamResultDao` with `insert`, `getResultsForExam(provider, number)`, `getAllResults`.
4. Build `AppDatabase` singleton (companion object `getInstance`).
5. Inject via a simple `object DatabaseProvider` — no Hilt needed yet.
**Validation**: Complete an exam, kill the app, relaunch → score is still visible.

---

### Fix 2.2 — Show Completion Status on ModelltestSelector
**Goal**: Exams the user has completed show a score badge or checkmark.
**Files**: `ui/exams/ModelltestSelectorScreen.kt`
**Steps**:
1. At screen entry, query `UserExamResultDao.getResultsForExam(provider, examNumber)`.
2. If a result exists, overlay a green checkmark `Icons.Default.CheckCircle` + score text on the exam card.
3. If no result, show the normal "Start" state.
**Validation**: Complete Goethe 1 Lesen → back to selector → checkmark appears.

---

## 🟠 PHASE 3 — Audio / ExoPlayer

### Fix 3.1 — ExoPlayer Integration for HoerenScreen
**Goal**: Enable actual audio playback on Listening screens.
**Files**: `ui/exam/HoerenScreen.kt`, `build.gradle.kts` (app module)
**Steps**:
1. Add `media3-exoplayer` and `media3-ui` to dependencies.
2. Create `AudioPlayerBar` composable: play/pause button, seek slider, time display. Style to match Glass theme.
3. In `HoerenScreen`, initialise `ExoPlayer` via `remember { ExoPlayer.Builder(context).build() }`.
4. Provide audio URI from `HoerenPart.audioAssetPath` (e.g. `"asset:///audio/goethe1_hoeren1.mp3"`).
5. Place audio files at `app/src/main/assets/audio/`.
6. Release player in `DisposableEffect { onDispose { player.release() } }`.
**Validation**: Open HoerenScreen → press Play → audio starts, seek bar moves in real-time.

---

## 🟢 PHASE 4 — External Repo Integrations (New Modules)

### Integration 4.1 — `saqibroy/deutsch-b1-vokab` + `greyels/deutsch-b1-prep` → WordVault Flashcards
**Goal**: Add a spaced-repetition flashcard feature to the Learn module.
**New Files**: `data/FlashcardData.kt`, `ui/learn/FlashcardScreen.kt`, `ui/learn/FlashcardDeckScreen.kt`
**Content Source**: The `deutsch-b1-vokab` repo contains 800+ B1 vocab entries in CSV/JSON format. The `deutsch-b1-prep` repo provides thematic vocabulary grouped by topic (Beruf, Familie, Gesundheit, etc.).
**Steps**:
1. Convert/port vocabulary lists into `data class Flashcard(id, germanWord, englishTranslation, exampleSentence, topic)`.
2. Populate `FlashcardData.kt` with at minimum 10 topic decks × 30 cards each (300 cards minimum).
3. Build `FlashcardDeckScreen`: shows deck name, card count, and a "Study" button.
4. Build `FlashcardScreen` (study mode): swipe left = "Don't know", swipe right = "Know". Track session score.
5. Show answer on tap (flip animation with `AnimatedContent`).
6. Persist "mastered" cards in Room DB: `FlashcardProgressDao` with `markMastered(cardId)`.
7. Add "Flashcards" entry to the Learn category list.
**Validation**: Open Learn → Flashcards → pick a deck → study 5 cards → exit and re-enter → mastered cards are marked.

---

### Integration 4.2 — `MohammedDrissi/Deutsche-Geschichten-zum-Lesen` → Geschichten Module
**Goal**: Add graded German reading stories (A2→B1) as a new content type in Learn.
**New Files**: `data/GeschichtenData.kt`, `ui/learn/GeschichtenListScreen.kt`, `ui/learn/GeschichteReaderScreen.kt`
**Content Source**: The repo contains short German prose stories with vocabulary annotations, comprehension questions, and level tags (A2/B1).
**Steps**:
1. Model: `data class Geschichte(id, title, level, bodyText, vocabHints: List<VocabHint>, questions: List<MCQuestion>)`.
2. Port at least 10 stories from the repo into `GeschichtenData.kt`, tagged A2 or B1.
3. `GeschichtenListScreen`: filterable by level. Glass cards showing title + level badge + estimated read time.
4. `GeschichteReaderScreen`: full-screen text reader. Long-press on a word → bottom sheet shows `VocabHint` definition. (Uses `AnnotatedString` + `PointerInput` to detect word taps.)
5. Comprehension questions appear after the user reaches the end of the story (same MC component as Lesen).
6. Register routes in `AppNavGraph.kt`.
7. Add "Geschichten" section to Learn home.
**Validation**: Open Learn → Geschichten → pick a story → tap a word → vocab hint appears → complete the story → comprehension questions shown.

---

### Integration 4.3 — `MohammedDrissi/Grammar-mit-mir` → Grammar Drill Feature
**Goal**: Replace static grammar phrase lists with interactive grammar drills (fill-in-the-blank, multiple choice).
**New Files**: `data/GrammarDrillData.kt`, `ui/learn/GrammarDrillScreen.kt`
**Content Source**: The `Grammar-mit-mir` repo structures grammar rules into card-based lessons with mini-exercises (Konjunktiv II, Passiv, Relativsätze, etc.).
**Steps**:
1. Model: `data class GrammarRule(id, topic, explanation, examples: List<String>, drills: List<GrammarDrill>)` where `GrammarDrill` is a fill-in-the-blank or MC exercise.
2. Port at least 15 grammar topics from the repo.
3. `GrammarDrillScreen`: displays rule explanation at top, then drills one-by-one in a card swipe format.
4. Mark topics "completed" when user scores ≥ 80% on the drills.
5. Integrate with the existing Learn > Grammar category entry point.
**Validation**: Open Learn → Grammar → pick "Konjunktiv II" → complete 5 drills → topic marked completed.

---

### Integration 4.4 — `MohammedDrissi/WordVault-Vocabulary-Builder` → Enhanced Vocab Builder
**Goal**: Add a save-to-wordlist feature, allowing users to bookmark words encountered anywhere in the app.
**New Files**: `data/db/entities/SavedWord.kt`, `data/db/SavedWordDao.kt`, `ui/learn/WordVaultScreen.kt`
**Content Source**: The `WordVault` repo provides the UX concept (saved words list, review mode, word stats).
**Steps**:
1. Add `SavedWord` entity to Room DB: `(id, german, english, context, savedAt)`.
2. Add a bookmark `IconButton` (🔖) to: `GeschichteReaderScreen`, `ExamScreen` question rows, `DictionaryScreen` result items.
3. `WordVaultScreen`: shows the user's personal saved word list, grouped by date. Each card has the word, translation, and the original context sentence.
4. Add a "Review Saved Words" button that launches `FlashcardScreen` with the user's personal deck.
5. Add "Word Vault" entry to Learn home or as a floating action in Dictionary.
**Validation**: Long-press a word in a story → bookmark it → open Word Vault → word appears with context.

---

### Integration 4.5 — `deutschimalltag22-hash/sprachspiel-b1` → Spiele (Games) Section
**Goal**: Add gamified B1 vocabulary exercises to make practice more engaging.
**New Files**: `data/SpielData.kt`, `ui/learn/SpielMenuScreen.kt`, `ui/learn/games/WordMatchScreen.kt`, `ui/learn/games/FillBlankScreen.kt`
**Content Source**: The repo provides word-matching pairs, fill-in-the-blank sentence structures, and jumbled word ordering exercises.
**Steps**:
1. Model: `data class WordPair(german, english)` for matching game; `data class FillSentence(template, answer, distractors)` for fill-blank.
2. Port at least 5 game sets (30 pairs/sentences each) from the repo.
3. `WordMatchScreen`: two columns — German words left, shuffled English right — tap to match. Animate matched pairs with a green flash and spring effect.
4. `FillBlankScreen`: sentence with a `___` gap, 4 MC options below. Running score at top.
5. Add "Spiele 🎮" section to Learn home.
**Validation**: Open Learn → Spiele → Word Match → match 10 pairs correctly → score displayed.

---

### Integration 4.6 — `yunus-topal/Deutsch-Lernen` → Thematic Phrase Enrichment
**Goal**: Enrich existing `LearnData.kt` thematic categories with more vocabulary and structured lessons.
**Content Source**: The repo provides structured German vocabulary organized by daily-life topics (travel, food, health, work) with German sentences and English translations.
**Steps**:
1. Review existing `LearnData.kt` topic categories.
2. Add at least 5 new topics pulled from the `Deutsch-Lernen` repo (e.g. Reisen, Kochen, Gesundheit, Wohnen, Beziehungen).
3. Expand existing topics (e.g. Arbeit, Familie) with 15+ additional phrases each.
4. For each new phrase entry, add an `exampleSentence` field alongside `term` and `translation`.
**Validation**: Open Learn → any enriched category → confirm phrase count significantly increased vs. before.

---

### Integration 4.7 — `saqibroy/German-b1-learning-tracker` → Progress Dashboard
**Goal**: Build a Progress/Stats screen showing overall learning progress across all modules.
**Content Source**: The `learning-tracker` repo defines a schema for tracking study sessions, streaks, and module completion states.
**New Files**: `ui/home/ProgressDashboardScreen.kt`, `data/db/entities/StudySession.kt`
**Steps**:
1. Add `StudySession` entity to Room: `(id, moduleType, durationSeconds, itemsCompleted, completedAt)`.
2. Log a `StudySession` after any completed exam part, flashcard session, story, or grammar drill.
3. `ProgressDashboardScreen`: displays streak counter (days in a row), total study time, exams completed %, and flashcard mastery %.
4. Accessible via a "Stats" icon on the Home screen top bar.
**Validation**: Complete 2 activities → open Stats → counters reflect correct totals.

---

## 🔵 PHASE 5 — Polish & Production Readiness

### Fix 5.1 — Shimmer Loading States
Add `ShimmerEffect` composable (animated gradient sweep) to replace blank screens during API calls in Translation and Dictionary. Use `Animatable` for the shimmer gradient offset.

### Fix 5.2 — TELC Provider Stub → Full Content
Add `telcExam1` in `ExamData.kt` for TELC Modelltest 1, starting with at least Lesen and Schreiben parts. Register in provider selector.

### Fix 5.3 — Strings Extraction
Move all hardcoded UI text from composables into `res/values/strings.xml`. This is required before any localization pass.

### Fix 5.4 — Night Mode Toggle
Add "Full Black" vs "Deep Grey" theme variant selector in Settings (accessible from Home top bar). Persist choice in `DataStore`.

---

## 🧪 Testing Strategy (Per Phase)
- **Phase 1 (Bugs)**: Manual smoke tests + screenshot comparison per fixed screen.
- **Phase 2 (Room)**: Unit tests for DAO methods using `Room.inMemoryDatabaseBuilder`.
- **Phase 3 (Audio)**: Device/emulator manual test with real `.mp3` assets.
- **Phase 4 (New Modules)**: Manual navigation tests + verify data appears on each new screen.
- **Phase 5 (Polish)**: Full regression pass on all 4 exam providers and all Learn screens.