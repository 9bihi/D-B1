# Decision Log - Deutsch B1 Exam

## Technical Decisions

### 2024-03-05: Jetpack Compose over XML
- **Context**: Deciding on the UI framework for a new "premium" app.
- **Decision**: Use Jetpack Compose exclusively.
- **Rationale**: Compose allows for the complex "Glassmorphism" layer stacking and spring-physics animations much more easily than traditional XML views.

### 2024-03-05: Static Data Models for Initial Launch
- **Context**: Choosing between a local DB (Room) or static Kotlin objects for exam content.
- **Decision**: Start with static lists in `ExamData.kt` and `LearnData.kt`.
- **Rationale**: Faster initial development and ensures all content is available offline immediately without complex migration bridge. **Status: To be revisited for progress tracking.**

### 2024-03-05: Retrofit/GSON for Networking
- **Context**: Need to connect to public dictionary/translation APIs.
- **Decision**: Industry-standard Retrofit + OkHttp stack.
- **Rationale**: Low boilerplate and high reliability for the small number of endpoints required.

### 2024-03-06: Memory Bank Implementation
- **Context**: Need to document the project for future AI development.
- **Decision**: Implement a comprehensive `memory-bank/` directory.
- **Rationale**: Provides a single source of truth for architectural patterns and design tokens.
