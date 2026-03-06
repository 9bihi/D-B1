# System Patterns - Deutsch B1 Exam
## Version 2.3

---

## 🏗️ Architectural Pattern
Single-Activity, Feature-Module Compose Navigation. Offline-first.

---

## 🎨 Design Patterns

| Pattern | Usage |
|---|---|
| Repository (object singleton) | `FlashcardRepository`, `GeschichtenRepository`, `SpielRepository`, `VerbRepository` |
| Sealed class result | `ApiResult<T>` for all async operations |
| State hoisting | All screen state in ViewModel or composable state, not in components |
| Factory pattern | `AssetLoader` creates typed objects from JSON assets |

---

## 📁 Project Structure (v2.3)

```
app/src/main/
├── java/.../
│   ├── MainActivity.kt              ← Single activity, 3-tab NavBar (Home/Exams/Learn)
│   ├── navigation/
│   │   ├── AppNavGraph.kt           ← All screen routes
│   │   └── Screen.kt                ← Sealed class of all destinations
│   ├── data/
│   │   ├── ExamData.kt              ← Static exam content (Goethe 1+2, ÖSD 1+2, TELC 1)
│   │   ├── LearnData.kt             ← Static grammar/vocab learn content
│   │   ├── FlashcardRepository.kt   ← Loads assets/flashcards/decks.json
│   │   ├── GeschichtenRepository.kt ← Loads assets/geschichten/stories.json
│   │   ├── SpielRepository.kt       ← Loads assets/spiele/*.json
│   │   ├── AssetLoader.kt           ← Generic JSON asset parser
│   │   └── db/                      ← Room (Phase 2)
│   ├── ui/
│   │   ├── components/
│   │   │   ├── GlassCard.kt
│   │   │   ├── FloatingGlassNavBar.kt
│   │   │   ├── ShimmerEffect.kt
│   │   │   ├── ErrorStateCard.kt
│   │   │   ├── McQuestionCard.kt
│   │   │   └── AudioPlayerBar.kt    ← Phase 3
│   │   ├── home/
│   │   │   └── HomeScreen.kt        ← Streak + Prüfungsanbieter cards (no Translation card)
│   │   ├── exams/
│   │   │   ├── ExamProviderListScreen.kt
│   │   │   ├── ProviderSkillSelectorScreen.kt
│   │   │   ├── ModelltestSelectorScreen.kt
│   │   │   ├── LesenScreen.kt
│   │   │   ├── HoerenScreen.kt
│   │   │   ├── SchreibenScreen.kt
│   │   │   ├── SprechenScreen.kt
│   │   │   └── ResultSummaryScreen.kt  ← Phase 1
│   │   ├── learn/
│   │   │   ├── LearnHomeScreen.kt       ← Konnektoren, Grammatik, Sprechen, Wortschatz, Lernkarten, Geschichten, Spielen
│   │   │   ├── GrammatikScreen.kt
│   │   │   ├── KonnektorenScreen.kt
│   │   │   ├── WortschatzScreen.kt
│   │   │   ├── SprechenLearnScreen.kt
│   │   │   ├── FlashcardDeckListScreen.kt  ← NEW v2.3
│   │   │   ├── FlashcardStudyScreen.kt     ← NEW v2.3
│   │   │   ├── GeschichtenListScreen.kt    ← NEW v2.3
│   │   │   ├── GeschichteReaderScreen.kt   ← NEW v2.3
│   │   │   ├── SpielMenuScreen.kt          ← NEW v2.3
│   │   │   ├── WortpaarMatchScreen.kt      ← NEW v2.3
│   │   │   ├── LueckentextScreen.kt        ← NEW v2.3
│   │   │   └── SatzordnungScreen.kt        ← NEW v2.3
│   │   └── theme/
│   │       └── Theme.kt
└── assets/
    ├── flashcards/decks.json
    ├── geschichten/stories.json
    ├── spiele/wortpaare.json
    ├── spiele/lueckentext.json
    └── spiele/satzordnung.json
```

---

## 🎮 Game Screen Patterns

### WortpaarMatch — State Machine
```
IDLE → SELECTING_GERMAN → SELECTING_ENGLISH → CHECK_MATCH
                                                   ↓
                                          MATCH (green flash) → remove pair
                                          MISMATCH (red flash) → deselect
                                          ALL_MATCHED → show score + confetti
```

### FlashcardStudy — Card State
```
FRONT_VISIBLE ← (flip) → BACK_VISIBLE
User rates: KNOWN (+1 to known count) | UNKNOWN (card goes back to queue)
Progress: known/total → shown in top bar
Completion: all known → ResultCard (deck score, time, retry option)
```

### LueckentextScreen — Question Flow
```
SHOW_SENTENCE_WITH_BLANK → USER_SELECTS_OPTION →
  CORRECT: green highlight + show explanation → next (after 1.5s)
  WRONG: red highlight + show correct + explanation → next (after 2s)
END: score card (X/N correct)
```

---

## 🔑 Key Rules (DO NOT CHANGE)

1. **FloatingGlassNavBar**: Has spring physics. 3 tabs now. Do not hardcode tab count — use `navItems.size`.
2. **AssetLoader on IO**: Every `context.assets.open(...)` call must be on `Dispatchers.IO`.
3. **Repository cache**: Check `if (cache != null) return cache!!` before reloading from assets.
4. **No hardcoded strings in Compose**: Use `stringResource()` or constants — never raw strings in UI.
5. **GlassCard everywhere**: All content cards use the `GlassCard` composable — never plain `Card`.
6. **spring() for animations**: No `tween()` unless absolutely necessary. spring = iOS feel.
