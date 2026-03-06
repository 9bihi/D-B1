# System Patterns - Deutsch B1 Exam
## Version 2.0

---

## 🏗️ Architectural Pattern
Single-Activity Android app with declarative Compose UI, flat component-based navigation, and a layered data architecture (Static + JSON Assets + Room DB).

---

## 📐 Core Design Patterns

### 1. Single Activity Architecture
`MainActivity.kt` is the sole activity. All UI transitions happen inside `AppNavGraph.kt` via `NavHost`. This enables seamless Glassmorphism transitions and a shared `FloatingGlassNavBar` at the root scaffold level.

### 2. Declarative Navigation (Type-Safe Routes)
Routes defined as a `sealed class Screen` with named arguments. All `navController.navigate(...)` calls reference sealed object properties — no stringly-typed route magic.

### 3. Provider Pattern (Static Data)
`ExamData.kt` and `LearnData.kt` act as pseudo-repositories that expose immutable lists to UI screens. Screens query by provider and exam number; they never own the data.

### 4. JSON Asset Loading (New — Phase 4)
New content modules (Geschichten, GrammarDrills, Spiele, FlashcardData) load from `assets/*.json` files via a lightweight `AssetLoader` utility:
```kotlin
object AssetLoader {
    fun <T> load(context: Context, fileName: String, type: Type): T {
        val json = context.assets.open(fileName).bufferedReader().readText()
        return Gson().fromJson(json, type)
    }
}
```
This decouples content from code and avoids further APK bloat.

### 5. Sealed ApiResult for Network Calls
All `ApiRepository` methods return `ApiResult<T>`:
```kotlin
sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val message: String, val code: Int? = null) : ApiResult<Nothing>()
    object Loading : ApiResult<Nothing>()
}
```
UI screens observe this and render: Loading shimmer → Success content → Error card with retry.

### 6. Room DB via DatabaseProvider Singleton
```kotlin
object DatabaseProvider {
    @Volatile private var INSTANCE: AppDatabase? = null
    fun get(context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
        Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "db_b1")
            .fallbackToDestructiveMigration()
            .build().also { INSTANCE = it }
    }
}
```
Used by screens that need persistence (ResultSummaryScreen, FlashcardScreen, etc.).

### 7. State Hoisting
UI components are stateless. Variables (selectedIndex, currentQuestion, userAnswers) are hoisted to the nearest composable that needs them. No ViewModels in Phase 1/2; may be introduced in Phase 5 if complexity demands it.

### 8. FloatingGlassNavBar with Spring Physics
The most complex UI pattern. Uses `onGloballyPositioned` to track tab coordinates, then `animateFloatAsState(targetValue, spring(dampingRatio = Spring.DampingRatioMediumBouncy))` to animate the highlight pill. Do not refactor this without testing on real hardware — the spring values are tuned.

---

## 📁 Project Structure (Updated for v2.0)

```
com.deutschb1/
├── MainActivity.kt
├── navigation/
│   └── AppNavGraph.kt            (Screen sealed class + NavHost)
├── data/
│   ├── ExamData.kt               (Static: Goethe 1&2, ÖSD 1&2, TELC 1)
│   ├── LearnData.kt              (Static: Grammar, Vocab, Thematic phrases)
│   ├── GeschichtenData.kt        (NEW: JSON asset loader for reading stories)
│   ├── GrammarDrillData.kt       (NEW: JSON asset loader for grammar exercises)
│   ├── FlashcardData.kt          (NEW: JSON asset loader for vocab flashcard decks)
│   ├── SpielData.kt              (NEW: JSON asset loader for games content)
│   ├── ApiRepository.kt          (Retrofit calls → ApiResult<T>)
│   ├── AssetLoader.kt            (NEW: Generic JSON asset parser)
│   └── db/
│       ├── AppDatabase.kt        (NEW: Room database definition)
│       ├── DatabaseProvider.kt   (NEW: Singleton accessor)
│       ├── dao/
│       │   ├── UserExamResultDao.kt
│       │   ├── FlashcardProgressDao.kt
│       │   ├── SavedWordDao.kt
│       │   └── StudySessionDao.kt
│       └── entities/
│           ├── UserExamResult.kt
│           ├── FlashcardProgress.kt
│           ├── SavedWord.kt
│           └── StudySession.kt
├── ui/
│   ├── theme/                    (Color.kt, Theme.kt, Typography.kt, Shapes.kt)
│   ├── components/               (NEW: Shared reusable composables)
│   │   ├── GlassCard.kt
│   │   ├── ShimmerEffect.kt      (NEW: Loading skeleton)
│   │   ├── AudioPlayerBar.kt     (NEW: ExoPlayer UI)
│   │   ├── McQuestionCard.kt
│   │   └── ErrorStateCard.kt     (NEW: API error with retry)
│   ├── home/
│   │   ├── HomeScreen.kt
│   │   └── ProgressDashboardScreen.kt  (NEW)
│   ├── exams/
│   │   ├── ExamsHomeScreen.kt
│   │   ├── ProviderSkillSelector.kt
│   │   └── ModelltestSelectorScreen.kt (UPDATED: shows completion badges)
│   ├── exam/
│   │   ├── LesenScreen.kt
│   │   ├── HoerenScreen.kt       (UPDATED: ExoPlayer integration)
│   │   ├── SchreibenScreen.kt
│   │   ├── SprechenScreen.kt
│   │   └── ResultSummaryScreen.kt (NEW)
│   ├── learn/
│   │   ├── LearnHomeScreen.kt    (UPDATED: new module entries)
│   │   ├── CategoryDetailScreen.kt
│   │   ├── ThemePhraseListScreen.kt
│   │   ├── FlashcardDeckScreen.kt   (NEW)
│   │   ├── FlashcardScreen.kt       (NEW)
│   │   ├── GeschichtenListScreen.kt (NEW)
│   │   ├── GeschichteReaderScreen.kt (NEW)
│   │   ├── GrammarDrillScreen.kt    (NEW)
│   │   ├── WordVaultScreen.kt       (NEW)
│   │   ├── SpielMenuScreen.kt       (NEW)
│   │   └── games/
│   │       ├── WordMatchScreen.kt   (NEW)
│   │       └── FillBlankScreen.kt   (NEW)
│   └── translation/
│       ├── TranslationScreen.kt  (UPDATED: error states + copy button)
│       └── DictionaryScreen.kt   (UPDATED: error states + bookmark)
├── network/
│   ├── RetrofitClient.kt
│   └── ApiInterface.kt
└── assets/
    ├── audio/
    │   ├── goethe1_hoeren1.mp3
    │   └── ...
    ├── flashcards/
    │   ├── deck_beruf.json
    │   └── ...
    ├── geschichten/
    │   └── stories.json
    ├── grammar/
    │   └── drills.json
    └── spiele/
        └── games.json
```

---

## 🔄 Data Flow Patterns

### Exam Flow
User → ProviderSelector → ModelltestSelector (checks Room for completion badge) → SkillSelector → ExamScreen (fetches static `ExamData`) → ResultSummaryScreen (writes to Room `UserExamResultDao`)

### Learn / Flashcard Flow
User → LearnHome → FlashcardDeckScreen (loads JSON via `AssetLoader`) → FlashcardScreen (tracks session) → writes mastered cards to `FlashcardProgressDao`

### API Call Flow
User input → `ApiRepository.call()` → returns `ApiResult<T>` → UI observes: Loading shimmer / Success render / Error card + retry

### Bookmark Flow
User long-presses word anywhere → `SavedWordDao.insert()` → Word Vault Screen reads `SavedWordDao.getAll()` → "Review" button creates ad-hoc FlashcardScreen from saved words
