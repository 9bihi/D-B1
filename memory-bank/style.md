# Style Guide - Deutsch B1 Exam

## Aesthetic: "Glassmorphism"
The app uses a premium, dark-mode-first aesthetic known as Glassmorphism.

### Colors
- **Core Background**: `Color.Black` or `Color(0xFF121212)`.
- **Glass Card Background**: `Color(0xFF1C1C1E)` with `alpha = 0.88f`.
- **Accent/Text**: High-purity `Color.White`.
- **Muted Elements**: `Color.White.copy(alpha = 0.45f)`.
- **Glass Border**: `Color.White.copy(alpha = 0.18f)` (Width: 0.8.dp).

### Design Tokens
- **Corner Radius**:
    - Large Pills: `36.dp`.
    - Content Cards: `24.dp`.
    - Inner Highlights: `30.dp`.
- **Shadows**:
    - Elevation: `24.dp`.
    - Color: `Color.Black.copy(alpha = 0.6f)`.
- **Typography** (via `MaterialTheme.typography`):
    - Labels: `labelSmall`, 10.sp for the Bottom Bar.
    - Headers: Large sans-serif (Standard Compose Roboto/System font).

### Interaction Rules
1. **Bouncy Transitions**: All movement (indicators, content fades) SHOULD use `spring` animations with `MediumBouncy` damping.
2. **Haptic Feedback**: (Planned) Subtle haptics on exam selection.
3. **No Hard Edges**: Avoid sharp corners; every interactive element should have at least an 8.dp radius.
4. **Transparency as Hierarchy**: Use higher alpha for priority content and lower alpha (0.4f - 0.6f) for secondary info/captions.
