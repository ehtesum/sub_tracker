# Requirements Specification (REQ) — SubTracker

## 1. Functional Requirements (FR)

### FR-01: Subscription CRUD Operations
- Users MUST be able to create, view, edit, and delete subscription entries with fields: Name, Cost, Currency, Billing Frequency (Weekly, Monthly, Quarterly, Yearly), Start Date, Next Billing Date, Category, and Payment Method.
- The app MUST support tagging entries as "Free Trial" with a mandatory trial end date.

### FR-02: Notification Scheduling
- The app MUST schedule local system notifications via Android `WorkManager` or `AlarmManager` (Exact Alarms).
- Notifications MUST trigger at a user-customizable hour (default 09:00 AM) at selected intervals: 7 days, 3 days, and 1 day prior to billing.

### FR-03: Analytics & Forecasting
- The app MUST compute total expected monthly expenditure and total annual expenditure dynamically.
- The dashboard MUST categorize spending by segment (Streaming, Utilities, Cloud/Software, Fitness, Work).

### FR-04: Offline Data Portability
- The app MUST support exporting user data into an encrypted `.subtrack` JSON payload protected by a user passphrase.
- The app MUST validate and restore exported backup payloads without internet connectivity.

---

## 2. Non-Functional Requirements (NFR)

### NFR-01: Performance & Startup
- Cold start time MUST be under 500 ms on mid-range Android devices (Snapdragon 600 series or equivalent).
- Dashboard scroll frame rate MUST sustain 60 FPS minimum without jank.

### NFR-02: Security & Permissions
- The app MUST NOT request `android.permission.INTERNET`.
- Sensitive fields (notes, billing identifiers) MUST be stored encrypted using SQLCipher.

### NFR-03: System Compatibility
- Minimum API Level: Android 8.0 (API 26).
- Target API Level: Android 15 (API 35).
