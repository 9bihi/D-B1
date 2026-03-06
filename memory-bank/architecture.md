# Architecture - Deutsch B1 Exam
## Version 2.0

---

## System Overview
Single-activity Android app built entirely in Jetpack Compose. Uses flat component-based navigation, central static data providers for exam content, JSON asset loading for new content modules, Room DB for persistence, and ExoPlayer for audio.

---

## High-Level Diagram

```
[MainActivity]
      |
      +-- [DeutschB1App] (Scaffold)
              |
              +-- [AppNavGraph] (NavHost)
              |       |
              |       +-- [HomeScreen]
              |       |       +-- [ProgressDashboardScreen] (NEW)
              |       |
              |       +-- [ExamsHomeScreen]
              |       |       +-- [ProviderSkillSelector]
              |       |               +-- [ModelltestSelectorScreen] (shows Room completion badges)
              |       |                       +-- [LesenScreen]
              |       |                       +-- [HoerenScreen] (ExoPlayer audio)
              |       |                       +-- [SchreibenScreen]
              |       |                       +-- [SprechenScreen]
              |       |                       +-- [ResultSummaryScreen] (NEW)
              |       |
              |       +-- [LearnHomeScreen]
              |       |       +-- [CategoryDetailScreen]
              |       |       |       +-- [ThemePhraseListScreen]
              |       |       +-- [FlashcardDeckScreen] (NEW)
              |       |       |       +-- [FlashcardScreen] (NEW)
              |       |       +-- [GeschichtenListScreen] (NEW)
              |       |       |       +-- [GeschichteReaderScreen] (NEW)
              |       |       +-- [GrammarDrillScreen] (NEW)
              |       |       +-- [WordVaultScreen] (NEW)
              |       |       +-- [SpielMenuScreen] (NEW)
              |       |               +-- [WordMatchScreen] (NEW)
              |       |               +-- [FillBlankScreen] (NEW)
              |       |
              |       +-- [TranslationScreen] (UPDATED: error states + copy)
              |       +-- [DictionaryScreen] (UPDATED: error states + bookmark)
              |
              +-- [FloatingGlassNavBar] (Custom Bottom Bar, spring animations)
```

---

## Data Architecture

```
OFFLINE CONTENT LAYER
┌─────────────────────────────────────────────────────────┐
│  ExamData.kt (static Kotlin objects)                    │
│  LearnData.kt (static Kotlin objects)                   │
│  FlashcardData.kt ──► assets/flashcards/*.json          │
│  GeschichtenData.kt ──► assets/geschichten/stories.json │
│  GrammarDrillData.kt ──► assets/grammar/drills.json     │
│  SpielData.kt ──► assets/spiele/games.json              │
└─────────────────────────────────────────────────────────┘

USER PERSISTENCE LAYER (Room DB)
┌─────────────────────────────────────────────────────────┐
│  UserExamResultDao   (exam scores, completion)          │
│  FlashcardProgressDao (mastered cards)                  │
│  SavedWordDao         (bookmarked words)                │
│  StudySessionDao      (study time, streaks)             │
└─────────────────────────────────────────────────────────┘

NETWORK LAYER (ApiRepository → ApiResult<T>)
┌─────────────────────────────────────────────────────────┐
│  MyMemory Translation API                               │
│  Dictionary / Books API                                 │
│  OkHttp disk cache (100 MB)                             │
└─────────────────────────────────────────────────────────┘
```

---

## Key Data Flows

### 1. Exam Flow
User selects provider → ModelltestSelector queries Room for badge → starts exam → interacts with static ExamData content → ResultSummaryScreen shows score + writes to Room.

### 2. Flashcard Flow
FlashcardDeckScreen loads JSON deck via AssetLoader → FlashcardScreen tracks session → mastered cards written to Room FlashcardProgressDao → next session skips mastered cards.

### 3. Reading Story Flow
GeschichtenListScreen loads `stories.json` → user taps story → GeschichteReaderScreen renders annotated text → word tap shows VocabHint bottom sheet → bookmark writes to SavedWordDao → story end shows MCQs → StudySession logged to Room.

### 4. API Tool Flow
User types query → 500ms debounce → ApiRepository fires Retrofit call → returns `ApiResult.Loading` (shimmer) → `ApiResult.Success` (content) or `ApiResult.Error` (error card + retry). Response cached by OkHttp.

---

## Shared State / Global Managers

| Component | Scope | Notes |
|---|---|---|
| `NavController` | App-wide | Passed down through composable hierarchy |
| `ExamData` / `LearnData` | App-wide | Kotlin object singletons; immutable |
| `DatabaseProvider` | App-wide | Thread-safe Room singleton via `@Volatile` |
| `AssetLoader` | Screen-level | Called in `LaunchedEffect` on IO dispatcher |
| `Theme / Color.kt` | App-wide | MaterialTheme applied at root `DeutschB1App` |

---

## Technical Debt (Tracked)

| Issue | Severity | Fix Plan |
|---|---|---|
| No ViewModels — screens hold state directly | Medium | Introduce ViewModels in Phase 5 if screens grow complex |
| Large `ExamData.kt` compile time | Low | Already mitigated by JSON asset approach for new modules |
| No unit tests for data logic | Medium | Add Room DAO tests with `inMemoryDatabaseBuilder` in Phase 2 |
| Hardcoded UI strings | Low | Extract to `strings.xml` in Phase 5 |
| No OkHttp caching layer yet | High | Add in Phase 1.3 alongside ApiResult fix |
| TELC is enum stub only | Medium | Phase 5 content task |
