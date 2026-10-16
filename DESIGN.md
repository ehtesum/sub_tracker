# Design Specification (DESIGN) — SubTracker

## 1. Visual Aesthetics & Styling Tokens
- **Theme**: Minimalist Financial Dark & Light Theme (Material 3 Dynamic Theming).
- **Primary Color**: Emerald Teal (`#0D9488` Light / `#2DD4BF` Dark) representing financial clarity.
- **Alert Colors**: Amber Warning (`#F59E0B`) for renewals due within 3 days; Coral Crimson (`#EF4444`) for expiring free trials.
- **Backgrounds**: Slate Dark (`#0F172A`), Surface Card (`#1E293B`).
- **Typography**: Inter for UI controls, Outfit for numeric totals and currency values.

---

## 2. Screen Hierarchy & Navigation

### 2.1 Dashboard (`DashboardScreen.kt`)
- **Header Card**: Total Monthly Projected Spend (large text), Next Upcoming Charge pill badge.
- **Horizontal Scroll Carousel**: Expiring Free Trials warning cards.
- **Vertical Feed**: Categorized upcoming payment cards sorted chronologically by due date.
- **Floating Action Button (FAB)**: Quick add subscription modal with pre-configured templates (Netflix, Spotify, GitHub, iCloud, YouTube Premium).

### 2.2 Calendar & Timeline View (`CalendarScreen.kt`)
- Month-view calendar displaying dots on days with scheduled billing events.
- Tapping a date filters the bottom sheet list to show charges landing on that specific day.

### 2.3 Analytics Screen (`AnalyticsScreen.kt`)
- Donut chart breaking down monthly expenditure by category.
- Annual cost breakdown table ranking highest expense services.
