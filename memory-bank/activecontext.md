# Active Context - Deutsch B1 Exam

## Current Focus
The project is entering **Phase 2: Integration & Bug-Fix Sprint**. All 8 external GitHub repositories have been reviewed and mapped to integration targets within the app. The goal is to fix all documented issues AND enrich the app with new content/features sourced from these repos.

---

## External Repositories — Integration Summary

| Repo | Type | What It Adds |
|---|---|---|
| `greyels/deutsch-b1-prep` | Vocab + grammar JSON/lists | B1 thematic word lists → `LearnData.kt` |
| `MohammedDrissi/Deutsche-Geschichten-zum-Lesen` | Reading stories (A2–B1 prose) | New **"Geschichten" module** (graded reading practice) |
| `MohammedDrissi/Grammar-mit-mir` | Grammar rule cards + mini-quizzes | New **Grammar Drill** tab inside Learn module |
| `MohammedDrissi/WordVault-Vocabulary-Builder` | Flashcard data (de ↔ en) | New **Flashcard / Spaced-Repetition** feature in Learn |
| `deutschimalltag22-hash/sprachspiel-b1` | B1 word games (matching, fill-blank) | New **"Spiele" (Games)** section under Learn |
| `yunus-topal/Deutsch-Lernen` | Structured lesson JSON (topics+phrases) | Enriches existing `LearnData.kt` categories |
| `saqibroy/deutsch-b1-vokab` | Curated 800+ B1 vocab with translations | Feeds the new **WordVault flashcard deck** |
| `saqibroy/German-b1-learning-tracker` | Progress-tracking schema (SQLite) | Informs the **Room DB schema** for `UserProgress` |

---

## Active Bugs Being Fixed (This Sprint)

### 🔴 Critical
1. **Empty Modelltest 2** — `hoerenParts`, `schreibenTasks`, `sprechenTasks` for Goethe 2 & ÖSD 2 return `emptyList()`. Screens are dead.
2. **No Audio Playback** — `HoerenScreen` renders transcript text but MediaPlayer/ExoPlayer is not wired. Play button does nothing.
3. **State Loss on Close** — All exam progress is in-memory only. App restart erases scores and position.

### 🟡 High Priority
4. **No Result Summary Screen** — After completing a Lesen/Hoeren part, there is no feedback screen. The user is left on a blank end state.
5. **Silent API Failures** — `ApiRepository` does not expose error states to the UI. Network errors (rate-limit, offline) fail silently or crash.
6. **No Copy Button in Translator** — Translation results cannot be copied.

### 🟠 Medium Priority
7. **Missing Loading States** — Translation and Dictionary screens have no shimmer/skeleton while data is fetching.
8. **TELC Provider is a Ghost** — TELC appears in the `ExamProvider` enum but has no content. Selecting it yields an empty screen.
9. **No Progress Indicators on Home** — The Home screen shows no % completion or streak data.
10. **Hardcoded Strings** — UI text is scattered in composables, not in `strings.xml`. Makes localization impossible.

---

## Current State of Work

| Module | Status |
|---|---|
| App Shell + NavBar | ✅ Stable |
| Goethe Modelltest 1 (all 4 skills) | ✅ Complete |
| ÖSD Modelltest 1 (all 4 skills) | ✅ Complete |
| Goethe Modelltest 2 | ❌ Content Empty |
| ÖSD Modelltest 2 | ❌ Content Empty |
| Learn (Grammar + Vocab categories) | 🟡 70% — needs enrichment from repos |
| Translation Tool | 🟡 Works but silent errors + no copy |
| Dictionary Tool | 🟡 Works but silent errors |
| Room DB / Persistence | ❌ Not implemented |
| ExoPlayer / Audio | ❌ Not implemented |
| Result Summary Screen | ❌ Not implemented |
| Geschichten Module (new) | ❌ Not started |
| Flashcard / WordVault (new) | ❌ Not started |
| Grammar Drill (new) | ❌ Not started |
| Spiele / Games (new) | ❌ Not started |

---

## Immediate Risks
- **Bundle Size**: Adding 800+ vocab items + reading stories to static Kotlin objects will further bloat the APK. **Mitigation**: Migrate content to JSON assets loaded lazily, or commit to Room DB sooner.
- **Navigation Complexity**: New modules (Geschichten, Flashcards, Spiele) require new routes in `AppNavGraph.kt`. The bottom nav bar currently has 4 tabs — a 5th or reorganisation of Learn sub-tabs is needed.
- **API Rate Limits**: MyMemory free tier allows ~5,000 words/day. No caching layer exists. Under heavy use, translation silently fails.