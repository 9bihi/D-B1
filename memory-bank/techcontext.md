# Tech Context - Deutsch B1 Exam
## Version 2.3

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin 1.9.0+ |
| UI | Jetpack Compose + Material3 |
| Architecture | Single Activity, State Hoisting |
| Navigation | `androidx.navigation:navigation-compose` |
| HTTP | Retrofit 2 + OkHttp 4 (background, not exposed in UI) |
| JSON parsing | GSON |
| Offline content | JSON assets via `AssetLoader` |
| Persistence | Room DB (Phase 2) |
| Audio | Media3 ExoPlayer (Phase 3) |
| Images | Coil |
| Settings | DataStore Preferences |
| Animations | spring physics, `AnimatedContent`, `animateFloatAsState` |
| System UI | Accompanist `systemuicontroller` |

---

## 🗑️ Removed in v2.3

| Feature | Removed From |
|---|---|
| Translate bottom nav tab | `MainActivity.kt` NavItems |
| Translation home card | `HomeScreen.kt` |
| API Tools section | `LearnHomeScreen.kt` |
| Translation/Dictionary/VerbConjugation routes | `AppNavGraph.kt` |

Bottom nav is now **3 tabs**: Home · Exams · Learn.

---

## 🆕 New Asset Structure (v2.3)

```
app/src/main/assets/
├── flashcards/
│   └── decks.json          ← 10 decks × 30 cards (300 total)
├── geschichten/
│   └── stories.json        ← 10 graded stories (4×A2, 6×B1)
├── spiele/
│   ├── wortpaare.json      ← 5 sets × 10 word pairs
│   ├── lueckentext.json    ← 5 sets × 5–10 fill-in sentences
│   └── satzordnung.json    ← sentence ordering challenges
└── audio/
    └── *.mp3               ← Hören audio (Phase 3)
```

---

## 🆕 New Data Classes (v2.3)

```kotlin
// Flashcards
data class FlashcardDeck(val deckId: String, val deckName: String, val icon: String, val cards: List<Flashcard>)
data class Flashcard(val id: String, val german: String, val article: String?, val plural: String?, val english: String, val exampleSentence: String)

// Geschichten
data class Story(val id: String, val title: String, val level: String, val readingTimeMinutes: Int, val topic: String, val body: String, val vocabHints: List<VocabHint>, val questions: List<StoryQuestion>)
data class VocabHint(val word: String, val definition: String)
data class StoryQuestion(val question: String, val options: List<String>, val correctIndex: Int, val explanation: String)

// Spielen
data class WortpaarSet(val setId: String, val title: String, val pairs: List<WordPair>)
data class WordPair(val german: String, val english: String)
data class LueckentextSentence(val template: String, val answer: String, val distractors: List<String>, val explanation: String)
data class SatzordnungSentence(val words: List<String>, val correctOrder: List<Int>, val correctSentence: String, val explanation: String)
```

---

## 🆕 New Repositories (v2.3)

```kotlin
// All are object singletons, load once from assets, cache in memory

object FlashcardRepository {
    fun loadDecks(context: Context): List<FlashcardDeck>
    fun getDeck(context: Context, deckId: String): FlashcardDeck?
}

object GeschichtenRepository {
    fun loadStories(context: Context): List<Story>
    fun getStory(context: Context, storyId: String): Story?
    fun getByLevel(context: Context, level: String): List<Story>
}

object SpielRepository {
    fun loadWortpaare(context: Context): List<WortpaarSet>
    fun loadLueckentext(context: Context): List<LueckentextSet>
    fun loadSatzordnung(context: Context): List<SatzordnungSet>
}
```

---

## 🆕 New Screens (v2.3)

| Screen | Route | Description |
|---|---|---|
| `FlashcardDeckListScreen` | `Screen.FlashcardDeckList` | Grid of 10 deck cards |
| `FlashcardStudyScreen` | `Screen.FlashcardStudy/{deckId}` | Flip-card study mode |
| `GeschichtenListScreen` | `Screen.GeschichtenList` | Story list with level badges |
| `GeschichteReaderScreen` | `Screen.GeschichteReader/{storyId}` | Reader + vocab + quiz |
| `SpielMenuScreen` | `Screen.SpielMenu` | 3 game type selector |
| `WortpaarMatchScreen` | `Screen.WortpaarMatch/{setId}` | Tap-to-match game |
| `LueckentextScreen` | `Screen.Lueckentext/{setId}` | Fill-in-the-blank |
| `SatzordnungScreen` | `Screen.Satzordnung/{setId}` | Word ordering |

---

## ⚡ Performance Rules (unchanged)
- `AssetLoader` always on `Dispatchers.IO`
- Repository objects cache after first load — never re-parse
- Room DAOs always `suspend fun`
- All animations use `spring(dampingRatio = Spring.DampingRatioMediumBouncy)`
- API calls: 500ms debounce

---

## 🔒 Constraints
- No network calls in UI-visible features (v2.3 removes all API tools from UI)
- All content works 100% offline
- No user authentication / no PII
