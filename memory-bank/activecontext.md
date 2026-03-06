# Active Context - Deutsch B1 Exam
## Version 2.1

---

## Current Focus
**Phase 0: API Overhaul** — All 3 API tools visible in the app's "API Tools" screen are broken. This must be fixed before any other feature work. The replacement APIs have been identified, verified, and are fully documented in `integrationmap.md`.

---

## API Tools — What's in the UI vs. What's Broken

| Screen Card | Subtitle Shown | Old (Broken) API | New (Verified) API |
|---|---|---|---|
| Online Dictionary | "Browse 5,000 German B1 words" | Google Books API (wrong use case) | German-Words-Library JSON (offline) + Wiktionary REST (online lookup) |
| Verb Conjugation | "8,000 verbs · All tenses" | No endpoint wired (stub screen) | `german-verbs-api.onrender.com` (MIT, free) |
| Translation | "Deutsch ↔ English · LibreTranslate" | LibreTranslate (unstable public instance) | MyMemory (primary, no key) + LibreTranslate (fallback) |

---

## Current State of Work

| Module | Status |
|---|---|
| App Shell + NavBar | ✅ Stable |
| Goethe Modelltest 1 (all 4 skills) | ✅ Complete |
| ÖSD Modelltest 1 (all 4 skills) | ✅ Complete |
| Goethe Modelltest 2 | ❌ Content empty |
| ÖSD Modelltest 2 | ❌ Content empty |
| Learn Module | 🟡 70% — needs enrichment from repos |
| **Dictionary (API fix)** | 🔧 In progress — replacing Google Books |
| **Verb Conjugation (API fix)** | 🔧 In progress — building screen + wiring API |
| **Translation (API fix)** | 🔧 In progress — MyMemory + fallback chain |
| Room DB / Persistence | ❌ Not implemented |
| ExoPlayer / Audio | ❌ Not implemented |
| Result Summary Screen | ❌ Not implemented |
| Geschichten Module | ❌ Not started |
| Flashcard / WordVault | ❌ Not started |
| Grammar Drill | ❌ Not started |
| Spiele / Games | ❌ Not started |

---

## External Repositories — Integration Summary

| Repo | Type | What It Adds |
|---|---|---|
| `greyels/deutsch-b1-prep` | Vocab lists | B1 thematic vocab → `FlashcardData.kt` |
| `MohammedDrissi/Deutsche-Geschichten-zum-Lesen` | Reading stories | New Geschichten module |
| `MohammedDrissi/Grammar-mit-mir` | Grammar exercises | Grammar Drill feature |
| `MohammedDrissi/WordVault-Vocabulary-Builder` | Bookmark UX | Save-word system |
| `deutschimalltag22-hash/sprachspiel-b1` | Word games | Spiele section |
| `yunus-topal/Deutsch-Lernen` | Thematic phrases | LearnData enrichment |
| `saqibroy/deutsch-b1-vokab` | 800+ B1 vocab | FlashcardData JSON decks |
| `saqibroy/German-b1-learning-tracker` | Progress schema | Progress Dashboard |

---

## Immediate Risks

- **Verb API Cold Start**: The German Verbs API runs on Render.com free tier. First request in a session takes 30–60 seconds. The UI must show a patient loading state — do not let the app appear frozen.
- **MyMemory Quota**: 5,000 words/day per IP. OkHttp cache prevents redundant calls but if a user intensively uses Translation, the fallback to LibreTranslate must work reliably.
- **Bundle Size**: Adding 5,000-word JSON to assets is acceptable (~200 KB). Still avoid adding large content as Kotlin static objects.
- **Navigation**: `VerbConjugationScreen` is a new route that must be registered in `AppNavGraph.kt` and the API Tools card must route to it.