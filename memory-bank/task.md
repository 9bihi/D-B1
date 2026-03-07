# Task List - Deutsch B1 Exam
## Version 2.3 — UI Cleanup + Content Expansion

---

## 🚨 ACTIVE TASK
> Remove Translation tab + API Tools from UI. Then add Exams (Modelltest 2 + TELC 1), Flashcards (10 decks × 30), Geschichten (10 stories), Spielen (3 game types).

---

## 🔴 Phase 0-A — UI REMOVALS (Do First)

### Remove Translation everywhere
- [x] **[RM-1]** `MainActivity.kt` → Remove `NavItem("Translate", ...)` from bottom nav list. Recalibrate FloatingGlassNavBar for 3 tabs.
- [x] **[RM-2]** `ui/home/HomeScreen.kt` → Remove "Translation" quick card (subtitle: "Deutsch ↔ English · LibreTranslate").
- [x] **[RM-3]** `ui/learn/LearnHomeScreen.kt` → Remove entire "API Tools" section (Online Dictionary card + Verb Conjugation card + "🔧 API Tools" header).
- [x] **[RM-4]** `navigation/AppNavGraph.kt` → Remove `composable(Screen.Translation.route){}`, `composable(Screen.Dictionary.route){}`, `composable(Screen.VerbConjugation.route){}` blocks.
- [x] **[RM-5]** Delete or keep-but-orphan: `ui/translation/TranslationScreen.kt`, `ui/learn/DictionaryScreen.kt`, `ui/learn/VerbConjugationScreen.kt` (keep files, just no nav entry).

---

## 🔴 Phase 0-B — EXAM CONTENT

- [x] **[EX-1]** Add `GoetheExam2LesenParts` to `ExamData.kt` (3 Lesen parts, 15 questions — see implementation-plan.md)
- [x] **[EX-2]** Add `GoetheExam2HoerenParts` to `ExamData.kt` (transcript + 5 questions)
- [x] **[EX-3]** Add `GoetheExam2SchreibenTasks` to `ExamData.kt` (2 writing tasks with key points)
- [x] **[EX-4]** Add `GoetheExam2SprechenTasks` to `ExamData.kt` (3 speaking tasks)
- [x] **[EX-5]** Mirror above for `OesdExam2` — all 4 skills populated
- [x] **[EX-6]** Add `TelcExam1LesenParts` (2 parts), `TelcExam1SchreibenTasks`, `TelcExam1SprechenTasks`
- [x] **[EX-7]** Wire all new exam data into the `ModelltestSelectorScreen` and exam flow

---

## 🔴 Phase 0-C — FLASHCARDS MODULE

- [x] **[FC-1]** Create `assets/flashcards/decks.json` — 10 decks × 30 cards = 300 cards total
- [x] **[FC-2]** Create `data/FlashcardData.kt` with `Flashcard` and `FlashcardDeck` data classes
- [x] **[FC-3]** Create `data/FlashcardRepository.kt` — loads JSON from assets, caches in memory
- [x] **[FC-4]** Create `ui/learn/FlashcardDeckListScreen.kt` — grid of deck cards with icon + name + count
- [x] **[FC-5]** Create `ui/learn/FlashcardStudyScreen.kt` — flip card animation, progress bar, known/unknown buttons
- [x] **[FC-6]** Register `Screen.FlashcardDeckList` and `Screen.FlashcardStudy(deckId)` in `AppNavGraph.kt`
- [x] **[FC-7]** Add "Lernkarten" card to `LearnHomeScreen.kt` above existing content

---

## 🔴 Phase 0-D — GESCHICHTEN MODULE

- [x] **[GS-1]** Create `assets/geschichten/stories.json` — 10 stories (4×A2, 6×B1) with vocab hints + questions
- [x] **[GS-2]** Create `data/GeschichtenData.kt` with `Story`, `VocabHint`, `StoryQuestion` data classes
- [x] **[GS-3]** Create `data/GeschichtenRepository.kt`
- [x] **[GS-4]** Create `ui/learn/GeschichtenListScreen.kt` — list with level badges (A2=blue, B1=green), reading time, topic tag
- [x] **[GS-5]** Create `ui/learn/GeschichteReaderScreen.kt` — full story text + vocab hints (collapsible) + quiz at end
- [x] **[GS-6]** Register `Screen.GeschichtenList` and `Screen.GeschichteReader(storyId)` in NavGraph
- [x] **[GS-7]** Add "Geschichten" card to `LearnHomeScreen.kt`

---

## 🔴 Phase 0-E — SPIELEN MODULE

- [x] **[SP-1]** Create `assets/spiele/wortpaare.json` — 5 sets × 10 word pairs
- [x] **[SP-2]** Create `assets/spiele/lueckentext.json` — 5 sets × 5–10 sentences
- [x] **[SP-3]** Create `assets/spiele/satzordnung.json` — 2 sets of scrambled sentences
- [x] **[SP-4]** Create `data/SpielData.kt` with all game data classes
- [x] **[SP-5]** Create `ui/learn/SpielMenuScreen.kt` — 3 game type cards
- [x] **[SP-6]** Create `ui/learn/WortpaarMatchScreen.kt` — tap German → tap English matching game, timer, score
- [x] **[SP-7]** Create `ui/learn/LueckentextScreen.kt` — sentence with blank, 4 MC options, explanation on answer
- [x] **[SP-8]** Create `ui/learn/SatzordnungScreen.kt` — draggable word chips → arrange into sentence
- [x] **[SP-9]** Register all `Screen.Spiele*` routes in NavGraph
- [x] **[SP-10]** Add "Spielen" card to `LearnHomeScreen.kt`

---

## ✅ Done
- [x] App shell, NavGraph, FloatingGlassNavBar
- [x] Goethe + ÖSD Modelltest 1 — all 4 skills
- [x] Glassmorphism dark theme
- [x] Learn module base (Grammatik, Konnektoren, Wortschatz, Sprechen B1)
- [x] Memory Bank v2.3 initialized
