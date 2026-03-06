# Integration Map - Deutsch B1 Exam
## Version 2.0

---

## External GitHub Repositories — Full Integration Plan
https://github.com/greyels/deutsch-b1-prep
https://github.com/MohammedDrissi/Deutsche-Geschichten-zum-Lesen
https://github.com/MohammedDrissi/Grammar-mit-mir
https://github.com/MohammedDrissi/WordVault-Vocabulary-Builder
https://github.com/deutschimalltag22-hash/sprachspiel-b1
https://github.com/yunus-topal/Deutsch-Lernen
https://github.com/saqibroy/deutsch-b1-vokab
https://github.com/saqibroy/German-b1-learning-tracker
---

### Repo 1: `greyels/deutsch-b1-prep`
**Type**: Vocabulary + Grammar reference lists
**Integration Target**: `data/FlashcardData.kt` (JSON content source)
**What it provides**: B1-level thematic vocabulary lists organized by topic (Beruf, Reisen, Familie, Gesundheit, Freizeit, Umwelt, etc.) with German terms and English translations.
**How to integrate**:
1. Extract vocabulary entries from the repo's data files (CSV/JSON/Markdown).
2. Convert to `Flashcard` objects grouped into named decks.
3. Export as `assets/flashcards/deck_<topic>.json`.
4. `FlashcardData.kt` reads deck index from `assets/flashcards/index.json`.
**Data Flow**: `FlashcardDeckScreen` → `AssetLoader.load("flashcards/deck_beruf.json")` → `List<Flashcard>` → `FlashcardScreen`

---

### Repo 2: `MohammedDrissi/Deutsche-Geschichten-zum-Lesen`
**Type**: Graded German reading stories (A2–B1 prose)
**Integration Target**: New **Geschichten** module
**What it provides**: Short stories in German (200–400 words) at A2 and B1 levels, with vocabulary annotations and comprehension questions.
**How to integrate**:
1. Port stories to `assets/geschichten/stories.json` schema:
```json
{
  "id": "g1",
  "title": "Der Urlaub",
  "level": "B1",
  "body": "...",
  "vocabHints": [{"word": "Urlaub", "definition": "Vacation / holiday"}],
  "questions": [{"question": "...", "options": [...], "correctIndex": 0}]
}
```
2. `GeschichtenListScreen` loads the index, filters by level.
3. `GeschichteReaderScreen` renders `body` as `AnnotatedString`, detects word taps, shows `vocabHints` in a `BottomSheet`.
**Data Flow**: `GeschichtenListScreen` → tap story → `GeschichteReaderScreen` (tap word → VocabHint BottomSheet) → finish story → MCQuestion cards → score shown

---

### Repo 3: `MohammedDrissi/Grammar-mit-mir`
**Type**: Interactive grammar lessons with exercises
**Integration Target**: Learn module — **Grammar Drill** feature
**What it provides**: Grammar rule explanations (Konjunktiv II, Passiv, Relativsätze, Präpositionen, Wortstellung, etc.) with fill-in-the-blank and MC exercises per topic.
**How to integrate**:
1. Port content to `assets/grammar/drills.json`:
```json
{
  "topic": "Konjunktiv II",
  "explanation": "Used to express wishes, hypothetical situations...",
  "examples": ["Wenn ich Zeit hätte, würde ich reisen."],
  "drills": [
    { "type": "fill_blank", "template": "Wenn ich Geld ___, würde ich ein Haus kaufen.", "answer": "hätte", "distractors": ["habe", "hatte", "haben"] }
  ]
}
```
2. `GrammarDrillScreen` loads the topic, presents explanation, then cycles through drills.
3. Score tracked per session; ≥80% marks topic as "completed" in Room DB.
**Data Flow**: Learn > Grammar category → GrammarDrillScreen → drill cards → score → completion written to FlashcardProgressDao (reusing entity with `moduleType = "GRAMMAR"`)

---

### Repo 4: `MohammedDrissi/WordVault-Vocabulary-Builder`
**Type**: Vocabulary builder UX (save, review, stats)
**Integration Target**: **Word Vault** save-word feature across all screens
**What it provides**: The UX concept and data schema for a personal saved-words list with review mode, tag categories, and word frequency stats.
**How to integrate**:
1. Add `SavedWord` Room entity (german, english, contextSentence, savedAt).
2. Add bookmark `IconButton` (🔖) to:
   - `GeschichteReaderScreen` (on word tap bottom sheet)
   - `DictionaryScreen` result items
   - `LesenScreen` question text (long-press)
3. Build `WordVaultScreen`: personal saved words grouped by date, search bar at top.
4. "Review" button on WordVaultScreen → `FlashcardScreen` seeded with saved words as a custom deck.
**Data Flow**: Bookmark tap → `SavedWordDao.insert()` → `WordVaultScreen` → query all saved words → "Review" → `FlashcardScreen`

---

### Repo 5: `deutschimalltag22-hash/sprachspiel-b1`
**Type**: B1 vocabulary games (matching, fill-blank, word ordering)
**Integration Target**: New **Spiele (Games)** section in Learn module
**What it provides**: Game data sets — word-pair matching (German ↔ English), fill-in-the-blank sentences, jumbled word ordering exercises. All at B1 level.
**How to integrate**:
1. Port game data to `assets/spiele/games.json`:
```json
{
  "sets": [
    {
      "id": "match_1", "type": "word_match", "title": "Alltag Wörter",
      "pairs": [{"german": "Bahnhof", "english": "train station"}]
    },
    {
      "id": "fill_1", "type": "fill_blank", "title": "Sätze ergänzen",
      "sentences": [{"template": "Ich ___ gestern ins Kino gegangen.", "answer": "bin", "distractors": ["habe", "war", "wäre"]}]
    }
  ]
}
```
2. `SpielMenuScreen`: grid of game set cards, each showing type icon, title, and best score.
3. `WordMatchScreen`: two-column layout, tap pairs to match. Spring animation on correct match.
4. `FillBlankScreen`: card-swipe through sentences; 4 MC options per card.
**Data Flow**: Learn > Spiele > SpielMenuScreen → pick game → WordMatchScreen or FillBlankScreen → score screen

---

### Repo 6: `yunus-topal/Deutsch-Lernen`
**Type**: Structured German learning lessons by daily-life topic
**Integration Target**: `data/LearnData.kt` — enrichment of thematic phrase categories
**What it provides**: Vocabulary lists organized by daily-life topics (Reisen, Kochen, Gesundheit, Wohnen, Arbeit, Familie) with German phrases, English translations, and context sentences.
**How to integrate**:
1. Add 5 new `LearnCategory` entries to `LearnData.kt`: Reisen, Kochen, Gesundheit, Wohnen, Beziehungen.
2. Expand existing Arbeit and Familie categories by 15+ phrases each.
3. Add `exampleSentence: String` field to the `ThemePhrase` data class.
4. Update `ThemePhraseListScreen` to optionally show the example sentence below each phrase (collapsible).
**Data Flow**: Same as existing Learn flow — no new screens needed.

---

### Repo 7: `saqibroy/deutsch-b1-vokab`
**Type**: Curated 800+ B1 vocabulary list with translations
**Integration Target**: `data/FlashcardData.kt` (primary vocabulary source for flashcard decks)
**What it provides**: A comprehensive, curated list of B1 vocabulary items (800+) grouped by frequency and topic, with German word, English translation, gender (for nouns), and a short example.
**How to integrate**:
1. This is the primary data source for Flashcard Integration (Repo 1 above).
2. Convert the 800+ entries into topic-grouped JSON decks (min 10 decks × 30 cards).
3. Priority topics: Beruf, Bildung, Familie, Freizeit, Gesundheit, Natur, Reisen, Technik, Wohnen, Gesellschaft.
4. Each `Flashcard` object: `{id, german, article, plural, english, exampleSentence, topic}`.
**Data Flow**: Same as Repo 1 (FlashcardData).

---

### Repo 8: `saqibroy/German-b1-learning-tracker`
**Type**: Learning progress tracking schema + logic
**Integration Target**: Room DB schema + **Progress Dashboard** screen
**What it provides**: Data schema for tracking study sessions (module, duration, items completed), streak calculation logic, and a summary dashboard concept.
**How to integrate**:
1. The `StudySession` Room entity schema is directly inspired by this repo's tracking structure.
2. Streak calculation: query `StudySessionDao.getDistinctDates(limit = 60)`, check for consecutive calendar days.
3. `ProgressDashboardScreen` shows:
   - 🔥 Current streak (days)
   - ⏱ Total study time (formatted)
   - 📚 Exams completed (X of 8 skills × 3 providers)
   - 🃏 Flashcards mastered (X of total)
   - 📖 Stories read (X of total)
4. A small "Activity Ring" (drawn with Canvas arc) for each module category.
**Data Flow**: Every completed activity (exam part, flashcard session, story, drill, game) → `StudySessionDao.insert()` → `ProgressDashboardScreen` queries all DAOs for aggregated stats.

---

## Internal Module Dependency Graph (v2.0)

```
[UI Modules]
    ├── ExamScreens         → ExamData.kt (static)
    │                       → UserExamResultDao (Room) [write on complete]
    ├── FlashcardScreens    → FlashcardData.kt (JSON asset)
    │                       → FlashcardProgressDao (Room)
    ├── GeschichtenScreens  → GeschichtenData.kt (JSON asset)
    │                       → SavedWordDao (Room) [bookmark]
    ├── GrammarDrillScreen  → GrammarDrillData.kt (JSON asset)
    ├── SpielScreens        → SpielData.kt (JSON asset)
    ├── WordVaultScreen     → SavedWordDao (Room)
    │                       → FlashcardScreen (seeded with saved words)
    ├── TranslationScreen   → ApiRepository → MyMemory API
    ├── DictionaryScreen    → ApiRepository → Dictionary API
    │                       → SavedWordDao (Room) [bookmark]
    └── ProgressDashboard   → All DAOs (aggregate read-only)

[AppNavGraph]               → manages all routes
[DatabaseProvider]          → single Room instance for all DAOs
[AssetLoader]               → parses JSON assets for all content modules
[ApiRepository]             → returns ApiResult<T> for all network calls
```

---

## Data Sync Strategy
- **Exam + Learn content**: Static Kotlin objects (`ExamData`, `LearnData`) → offline-first, fast.
- **New content modules**: JSON assets (`assets/*.json`) → offline-first, lazy-loaded.
- **User progress**: Room DB → persisted locally, no cloud sync.
- **API responses**: OkHttp cache (100 MB disk) → reduce repeat calls, work partially offline.
- **Future**: If cloud sync is added, a `SyncRepository` would bridge Room ↔ Firebase, but this is out of scope.