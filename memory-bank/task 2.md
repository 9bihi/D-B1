# Task List - Deutsch B1 Exam
## Version 2.4 — TELC Full Integration

---

## 🚨 ACTIVE TASK
> Integrate complete TELC Modelltest 1 + 2 content into ExamData.kt.
> All code is in `memory-bank/telc-examdata.md` — ready to paste.

---

## 🔴 TELC INTEGRATION — Do Now

### Step 1 — Paste into ExamData.kt
Copy the full Kotlin code from `telc-examdata.md` into `ExamData.kt`.
This adds:
- `TelcExam1LesenParts` — 4 sections: Lesen T1/T2/T3 + Sprachbausteine T1 (25 questions)
- `TelcExam1HoerenParts` — 3 sections: Hören T1/T2/T3 (20 questions + full transcripts)
- `TelcExam1SchreibenTasks` — 1 writing task (persönlicher Brief)
- `TelcExam1SprechenTasks` — 3 speaking tasks
- `TelcExam2LesenParts` — 4 sections: Lesen T1/T2/T3 + Sprachbausteine T1 (25 questions)
- `TelcExam2HoerenParts` — 3 sections (20 questions + full transcripts)
- `TelcExam2SchreibenTasks` — 1 writing task (halbformeller Brief / WG-Zimmer)
- `TelcExam2SprechenTasks` — 3 speaking tasks

### Step 2 — Register in allExams list
```kotlin
Exam(provider=ExamProvider.TELC, modelltestNumber=1,
     lesenParts=TelcExam1LesenParts, hoerenParts=TelcExam1HoerenParts,
     schreibenTasks=TelcExam1SchreibenTasks, sprechenTasks=TelcExam1SprechenTasks),
Exam(provider=ExamProvider.TELC, modelltestNumber=2,
     lesenParts=TelcExam2LesenParts, hoerenParts=TelcExam2HoerenParts,
     schreibenTasks=TelcExam2SchreibenTasks, sprechenTasks=TelcExam2SprechenTasks)
```

### Step 3 — Audio placeholder files
```
assets/audio/telc1_hoeren1.mp3  telc1_hoeren2.mp3  telc1_hoeren3.mp3
assets/audio/telc2_hoeren1.mp3  telc2_hoeren2.mp3  telc2_hoeren3.mp3
```
HoerenScreen should show transcript as fallback until real audio is added.

### Step 4 — Verify ModelltestSelectorScreen shows MT1 + MT2 for TELC

---

## 🔴 Phase 0-A — UI REMOVALS
- [x] Remove Translate tab from bottom nav (MainActivity.kt)
- [x] Remove Translation card from HomeScreen
- [x] Remove API Tools section (Dictionary + VerbConjugation) from LearnHomeScreen

## 🔴 Phase 0-C/D/E — New Content Modules
- [x] Flashcards (10 decks × 30 cards) — content in implementation-plan.md
- [x] Geschichten (10 stories) — content in implementation-plan.md
- [x] Spielen (3 game types) — content in implementation-plan.md

## 🟡 Phase 1 — Remaining Bugs
- [x] ResultSummaryScreen (create + wire)
- [x] Goethe Modelltest 2 content (content in implementation-plan.md)
- [x] ÖSD Modelltest 2 content

## ✅ Done
- [x] App shell + NavGraph + FloatingGlassNavBar
- [x] Goethe Modelltest 1 — all 4 skills
- [x] ÖSD Modelltest 1 — all 4 skills
- [x] TELC Modelltest 1 — full content (telc-examdata.md)
- [x] TELC Modelltest 2 — full content (telc-examdata.md)
- [x] Memory Bank v2.4 complete
