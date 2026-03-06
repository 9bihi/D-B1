# Architecture - Deutsch B1 Exam
## Version 2.1 - Post-Integration Baseline

---

## System Overview
Single-activity Android app built entirely in Jetpack Compose. Uses a modern modular UI architecture with declarative navigation, a robust persistence layer (Room), and hybrid content loading (static objects for core exams, JSON-based assets for stories/games/flashcards).

---

## High-Level Architecture Diagram

```
[MainActivity]
      |
      +-- [DeutschB1App] (Scaffold + Custom Theming)
              |
              +-- [AppNavGraph] (NavHost)
              |       |
              |       +-- [HomeScreen]
              |       |       +-- [ProgressDashboardScreen] (Stats, Streaks, History)
              |       |
              |       +-- [ExamsHomeScreen]
              |       |       +-- [ProviderSkillSelector]
              |       |               +-- [ModelltestSelectorScreen] (Room completion badges)
              |       |                       +-- [Exam Screens] (Lesen, Hoeren, Schreiben, Sprechen)
              |       |                       +-- [ResultSummaryScreen] (Score summary + Persistence)
              |       |
              |       +-- [LearnHomeScreen] (Content Hub)
              |       |       +-- [Flashcard Module] (Deck selection → Flashcard UI)
              |       |       +-- [Geschichten Module] (Story List → Immersive Reader)
              |       |       +-- [Grammar Drills] (Topic selection → Interactive drills)
              |       |       +-- [Spiele (Games)] (Word Match, Fill-in-the-Blank)
              |       |       +-- [WordVaultScreen] (Personal saved dictionary)
              |       |
              |       +-- [TranslationScreen] (LibreTranslate + Shimmer loading)
              |       +-- [DictionaryScreen] (Deep integration with Word Vault)
              |
              +-- [FloatingGlassNavBar] (Custom Pill-shaped Bottom Bar)
```

---

## Data Architecture

```
OFFLINE CONTENT LAYER
┌─────────────────────────────────────────────────────────┐
│  ExamData.kt (Full content for Goethe, ÖSD, TELC)       │
│  LearnData.kt (Thematic phrases & core grammar logic)   │
│  FlashcardData.kt ──► assets/flashcards/*.json          │
│  GeschichtenData.kt ──► assets/geschichten/stories.json │
│  GrammarDrillData.kt ──► enriching LearnData logic      │
│  SpielData.kt ──► assets/spiele/games.json              │
└─────────────────────────────────────────────────────────┘

USER PERSISTENCE LAYER (Room DB)
┌─────────────────────────────────────────────────────────┐
│  UserExamResultDao   (persist exam performance history) │
│  FlashcardProgressDao (track mastered cards for SRS)    │
│  SavedWordDao         (unified word bookmarking)        │
│  StudySessionDao      (aggregates time/activity stats)  │
└─────────────────────────────────────────────────────────┘

NETWORK LAYER (ApiRepository)
┌─────────────────────────────────────────────────────────┐
│  ApiResult<T> Pattern (Loading/Success/Error states)    │
│  Shimmer UI Feedback integration                        │
│  LibreTranslate API Integration (Self-hosted/Public)    │
└─────────────────────────────────────────────────────────┘
```

---

## Key Design Patterns

### 1. Immersive Story Reading
The `GeschichteReaderScreen` uses a custom `ClickableText` implementation allowing word-level granularity. Tapping a word triggers a bottom sheet translation and offers direct integration with the `SavedWordDao`.

### 2. Shimmer & Feedback
Standardized `ShimmerItem` component used across all asynchronous data loads (Translation, Stats) to provide a premium "skeleton" loading effect.

### 3. Progressive Disclosure
The `ProgressDashboardScreen` transforms raw `StudySession` logs into meaningful metrics (streaks, total hours, module-specific progress) using specialized aggregation queries in `StudySessionDao`.

---

## Global State Management

| Component | Scope | Notes |
|---|---|---|
| `NavController` | App-wide | Orchestrates transitions between all 15+ screens |
| `DatabaseProvider` | Singleton | Thread-safe Room instance for all DAOs |
| `Theme / Shimmer` | Global | Unified glassmorphism tokens and shimmer gradients |

---

## Resolved & Current Tech Debt

| Issue | Status | Resolution |
|---|---|---|
| Hardcoded UI strings | ✅ Resolved | Extracted to `strings.xml` for core nav and buttons |
| TELC missing content | ✅ Resolved | Full mock test content added to `ExamData.kt` |
| No Result Summary | ✅ Resolved | Persisted history now visible in Stats dashboard |
| Asset Loading UI | ✅ Resolved | Shimmer implemented for all deferred loading |
| No ViewModels | 🟡 Pending | Screens hold internal state; refactor needed if logic expands |
| Audio Assets | 🔴 Open | Code is ready (ExoPlayer); requires physical `.mp3` files in `assets/` |
