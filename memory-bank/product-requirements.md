# Product Requirements Document - Deutsch B1 Exam

## Project Name & Description
**Deutsch B1 Exam**: A premium Android application designed to help students prepare for the German B1 level language certificate (Goethe-Institut, ÖSD, and TELC).

## Core Purpose
The project aims to provide a comprehensive, mobile-first preparation platform that simulates real exam conditions and provides the necessary learning materials (grammar, vocabulary) in a modern, highly aesthetic interface.

## Target Audience
- International students preparing for university entrance in Germany/Austria.
- Professionals seeking work visas in German-speaking countries.
- Language learners moving from A2 to B1 level.

## Key Features & Mechanics
- **Exam Simulation**:
    - Support for multiple providers: **Goethe-Institut**, **ÖSD**, and **TELC** (planned).
    - Four core skills coverage: **Lesen** (Reading), **Hören** (Listening), **Schreiben** (Writing), and **Sprechen** (Speaking).
    - Timed practice tests with real exam structures (parts, tasks, instructions).
    - Interactive multiple-choice and true/false questions with explanations.
- **Learning Hub**:
    - Categorized content: Grammar, Vocabulary, Idioms, and Thematic Phrases.
    - Detailed drill-down into specific themes (e.g., Environment, Work, Health).
- **Utility Tools**:
    - **Dictionary**: Quick lookup for German words.
    - **Translator**: Integrated translation service for phrases and sentences.
- **Modern UI/UX**:
    - "Glassmorphism" design system with deep blacks, translucent cards, and smooth animations.
    - Floating bottom navigation for easy access.

## Out-of-Scope (Current Phase)
- **User Authentication/Cloud Sync**: Currently, the app appears to be local-first with no backend for progress tracking across devices. <!-- INFERRED: No Auth found in manifest or code -->
- **Real-time Tutoring**: The app is a self-study tool, not a live communication platform.
- **Video Content**: Education is primarily text and audio-based.

## Design Principles
1. **Premium Aesthetics**: High contrast (dark mode), subtle gradients, and glass effects to make learning feel "high-end."
2. **Focus**: Minimalist layouts to reduce cognitive load during mock exams.
3. **Responsiveness**: Smooth transitions between screens using Compose animations (Bouncy springs, fades).
