# Active Context - Deutsch B1 Exam
## Version 2.3 — UI Cleanup + Major Content Expansion

---

## 🗑️ ITEMS TO REMOVE (Based on Screenshots)

### From Home Screen (Image 2 — yellow circle):
- **"Translation" card** on the Home screen (subtitle: "Deutsch ↔ English · LibreTranslate")
- **"Translate" bottom navigation tab** (4th tab, rightmost, with ⇄A icon)

### From Learn Screen (Image 1 — yellow circle):
- **"Online Dictionary" card** (subtitle: "Browse 5,000 German B1 words")
- **"Verb Conjugation" card** (subtitle: "8,000 verbs · All tenses")

### Files to change:
| File | Change |
|---|---|
| `ui/home/HomeScreen.kt` | Remove Translation card from the quick-tools list |
| `MainActivity.kt` | Remove "Translate" from the bottom NavBar `NavItem` list. Shift remaining 3 tabs (Home, Exams, Learn). |
| `navigation/AppNavGraph.kt` | Remove `Screen.Translation`, `Screen.VerbConjugation`, `Screen.Dictionary` routes (or keep routes but remove entry points) |
| `ui/learn/LearnHomeScreen.kt` | Remove "API Tools" section entirely (both Dictionary + VerbConjugation cards) |

### Navigation after removal:
Bottom nav becomes **3 tabs**: Home · Exams · Learn
The FloatingGlassNavBar spring physics must be recalibrated for 3 tabs (not 4).

---

## 🆕 CONTENT TO ADD (This Sprint)

### 1. Exams — More Mock Test Content
- Goethe Modelltest 2 (all 4 skills) — fully populated
- ÖSD Modelltest 2 (all 4 skills) — fully populated  
- TELC Modelltest 1 (all 4 skills) — fully populated (was stub)
- All content researched against official exam formats (see ExamData additions)

### 2. Flashcards — 10 Decks × 30 Cards = 300 Cards
Real B1 vocabulary from official Goethe/ÖSD curriculum:
- Arbeit & Beruf, Bildung, Familie, Freizeit, Gesundheit, Kommunikation, Natur & Umwelt, Öffentliches Leben, Reisen & Verkehr, Wohnen

### 3. Geschichten — 10 Graded Stories (A2 → B1)
Original short German stories (200–400 words each) with:
- Vocab hints per story
- 3–5 comprehension questions per story
- Level tags: A2 (4 stories) + B1 (6 stories)

### 4. Spielen — 3 Game Types
- **Wortpaar-Match**: Tap German word → tap matching English translation
- **Lückentext**: Fill-in-the-blank sentence, 4 MC options
- **Satzordnung**: Arrange scrambled words into correct German sentence

---

## Current State

| Module | Status |
|---|---|
| Translation tab/card removal | 🔧 This sprint |
| Dictionary/VerbConj removal from Learn | 🔧 This sprint |
| Goethe Modelltest 2 content | 🔧 This sprint |
| ÖSD Modelltest 2 content | 🔧 This sprint |
| TELC Modelltest 1 content | 🔧 This sprint |
| Flashcard decks (10 × 30) | 🔧 This sprint |
| Geschichten module (10 stories) | 🔧 This sprint |
| Spielen module (3 game types) | 🔧 This sprint |
| Room DB / Persistence | ❌ Phase 2 |
| ExoPlayer / Audio | ❌ Phase 3 |
