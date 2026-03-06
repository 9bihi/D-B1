# Active Context - Deutsch B1 Exam
## Version 2.2 — UI Icon Fix + API Complete Overhaul

---

## 🚨 Current Sprint: Fix 2 Confirmed Live Bugs (Seen in Screenshots)

---

## Bug 1 — Provider Icon Broken on Skill Selector Screen

### What the screenshots show
- **Provider List screen** (Prüfungsanbieter): ✅ Icons render correctly (Goethe green logo, ÖSD blue logo, TELC red logo).
- **Skill Selector screen** (after tapping a provider): ❌ The top provider card shows a **generic white square** on a mismatched background color instead of the real provider logo. Pink arrows in screenshots point directly to this.
  - Goethe screen: blue circle + white square (should be green Goethe logo)
  - ÖSD screen: red circle + white square (should be blue ÖSD logo)
  - TELC screen: green circle + white square (should be red TELC logo)

### Root Cause
The `ProviderSkillSelectorScreen` (skill selection screen) renders the provider header card using a **different icon rendering path** than the `ExamProviderListScreen`. The provider list screen correctly uses `painterResource(R.drawable.ic_goethe)` etc., but the skill selector screen's header card is likely calling `AsyncImage` with a null/empty URL, or referencing a drawable ID that doesn't exist (e.g. using a string from the `ExamProvider` data class that isn't mapped to a real drawable resource), which falls back to a default placeholder (white square).

### Fix
In `ProviderSkillSelectorScreen.kt`, the provider header card must use the **same icon rendering logic** as `ExamProviderListScreen.kt`. Specifically:
1. Create a mapping function `fun ExamProvider.toIconRes(): Int` that returns the correct `R.drawable.*` for each provider.
2. Use `painterResource(provider.toIconRes())` in the header card — NOT `AsyncImage` or a URL-based loader.
3. Ensure the background tint on the icon container also uses `provider.toBrandColor()` (Goethe=#00A550, ÖSD=#0070C0, TELC=#E2001A).

---

## Bug 2 — All 3 API Tools Fail (Confirmed in Screenshots)

### Screenshot evidence
| Screen | Error Shown | Root Cause |
|---|---|---|
| Wörterbuch (Dictionary) | "Verbindung fehlgeschlagen. Bitte erneut versuchen." | Wiktionary REST API: Android HTTPS call failing OR response `de` key absent for complex compound word "Abbaumaschinen". The HTML inside definition strings also causes Gson parse crash. |
| Verbkonjugation | "Verbindung fehlgeschlagen. Server lädt möglicherweise noch (~30s)." | Render.com free tier: server is SLEEPING and does not wake up reliably within OkHttp's timeout window. The API call just times out. |
| Übersetzer (Translation) | "Keine Verbindung. Bitte erneut versuchen." | MyMemory API URL construction or encoding issue; OR the Retrofit `@Url` annotation isn't working correctly because Retrofit was built with a fixed base URL, making absolute URL calls fail silently. |

### Fixes (see implementation-plan.md Phase 0 for full code)
1. **Dictionary**: Replace Wiktionary with an **offline-first** approach using bundled JSON + `api.dictionaryapi.dev` for English fallback definitions. Wiktionary is dropped.
2. **Verb Conjugation**: Drop Render.com API entirely. Replace with a **fully offline** bundled JSON of the 200 most common German verbs. No network needed, 100% reliable.
3. **Translation**: Replace MyMemory + LibreTranslate with the **Google Translate unofficial gtx endpoint** which is the most reliable no-key translation API available and is used by dozens of open-source Android apps. Fix the Retrofit setup to use `OkHttpClient` directly (not Retrofit interface) for these calls.

---

## Current State of Work

| Module | Status |
|---|---|
| App Shell + NavBar | ✅ Stable |
| Goethe Modelltest 1 | ✅ Complete |
| ÖSD Modelltest 1 | ✅ Complete |
| Goethe Modelltest 2 | ❌ Content empty |
| ÖSD Modelltest 2 | ❌ Content empty |
| **Provider Icon on Skill Screen** | 🔧 Fix in progress |
| **Dictionary API** | 🔧 Replacing with offline JSON + DictionaryAPI |
| **Verb Conjugation API** | 🔧 Replacing with offline bundled conjugation JSON |
| **Translation API** | 🔧 Replacing with Google gtx endpoint |
| Room DB / Persistence | ❌ Not implemented |
| ExoPlayer / Audio | ❌ Not implemented |
| Result Summary Screen | ❌ Not implemented |
| New Learn Modules | ❌ Not started |
