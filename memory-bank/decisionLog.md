# Decision Log - Deutsch B1 Exam
## Version 2.3

---

## 2024-03-05: Jetpack Compose over XML
- **Decision**: Jetpack Compose exclusively.
- **Rationale**: Glassmorphism + spring animations require Compose.

## 2024-03-05: Static Data Models for Initial Launch
- **Decision**: Static Kotlin objects (`ExamData.kt`, `LearnData.kt`). New content in JSON assets.
- **Rationale**: Zero DB overhead for read-only content. Faster compile. Editable without recompile for JSON assets.

## 2024-03-05: Retrofit + OkHttp for Networking
- **Decision**: Retrofit 2 + OkHttp 4.
- **Status**: Still used for any remaining network calls. API tool routes removed in v2.3.

## 2024-03-06: Room DB over DataStore
- **Decision**: Room for all user progress persistence.
- **Rationale**: Relational queries needed (exam→score→timestamp).

## 2024-03-06: ExoPlayer (Media3)
- **Decision**: `media3-exoplayer` for Hören audio.
- **Rationale**: MediaPlayer has memory leak pattern in Compose.

## 2024-03-06: JSON Assets for New Content Modules
- **Decision**: Flashcards, Geschichten, Spielen stored as `assets/*.json`.
- **Rationale**: APK stays lean. Content editable post-release via update. AssetLoader on IO dispatcher.

---

## 2024-03-06: v2.3 — Remove Translation/API Tools Features

- **Decision**: Remove "Translate" bottom nav tab, "Translation" home card, and "API Tools" section (Dictionary + Verb Conjugation) from Learn screen entirely.
- **Rationale**: User confirmed these features are not wanted. Removing them simplifies the app to 3 tabs (Home · Exams · Learn) and removes broken API surface area.
- **Alternatives Rejected**: Fixing the APIs (previously attempted in v2.2) — user prefers removal over fixing.

---

## 2024-03-06: v2.3 — Content Expansion: Researched B1 Material

- **Decision**: Add Goethe Modelltest 2, ÖSD Modelltest 2, TELC Modelltest 1 (all 4 skills each), plus Flashcards (10 decks × 30 cards), Geschichten (10 graded stories), Spielen (3 game types).
- **Rationale**: App previously had Modelltest 1 only for Goethe + ÖSD; TELC was a stub. Content was insufficient for serious exam prep.
- **Research basis**:
  - Goethe B1 exam format sourced from official Goethe-Institut Modellsatz documentation.
  - ÖSD B1 exam format sourced from ÖSD official sample exam specifications.
  - TELC B1 exam format sourced from TELC Deutsch B1 Modelltest documentation.
  - Flashcard vocabulary aligned to Goethe/ÖSD official Wortliste B1 (published thematic vocabulary lists).
  - Stories written to CEFR A2/B1 descriptors (word frequency, sentence complexity, topic relevance).
  - Game content targets grammatical structures explicitly tested in B1 exams: Präpositionen, Konjunktiv II, Relativsätze, Konnektoren.

---

## 2024-03-06: v2.3 — Flashcard Architecture: JSON Asset + In-Memory Cache

- **Decision**: `FlashcardRepository` object loads `assets/flashcards/decks.json` once on first access, caches result in memory.
- **Rationale**: 300 cards × ~200 bytes avg = ~60 KB. Fits comfortably in memory. No DB overhead for static vocab content. Room `FlashcardProgress` entity (Phase 2) will track mastery separately.
- **Alternatives Rejected**: Hardcoded Kotlin data classes — too verbose (~1500 lines), no runtime editability.

---

## 2024-03-06: v2.3 — Geschichten: Graded Reader Approach

- **Decision**: 10 stories (4×A2, 6×B1), each with vocab hints and 4 comprehension questions.
- **Rationale**: Graded readers are a proven language acquisition method. A2 stories give confidence; B1 stories prepare for exam reading passages. Questions match Goethe Lesen Teil 2 format (detail comprehension).
- **Content standard**: Stories written with CEFR B1 lexical density targets: 95% high-frequency vocabulary, short/medium sentences, realistic everyday situations.

---

## 2024-03-06: v2.3 — Spielen: 3 Game Types Selected

- **Decision**: Wortpaar-Match (vocabulary), Lückentext (grammar in context), Satzordnung (word order).
- **Rationale**:
  - Wortpaar-Match → directly reinforces Flashcard vocabulary.
  - Lückentext → mirrors Goethe Lesen Teil 4 and TELC Sprachbausteine format.
  - Satzordnung → targets German word order (V2 rule, Nebensatz verb-final) — the #1 grammar difficulty for B1 candidates.
- **Alternatives Rejected**: Listening games (require audio assets not yet in place). Writing correction (requires AI feedback — out of scope).
