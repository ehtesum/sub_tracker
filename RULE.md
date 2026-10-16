# Engineering Rules & Guidelines (RULE) — SubTracker

## 1. Zero Network & Privacy Mandate
- **RULE-01: No Internet Access**: The application MUST NOT declare `android.permission.INTERNET` in `AndroidManifest.xml`.
- **RULE-02: Zero Cloud Telemetry**: Third-party crash reporters (e.g. Firebase Crashlytics) or tracking SDKs are strictly prohibited.

---

## 2. Notification Reliability Rules
- **RULE-03: WorkManager Rescheduling**: Daily alert checks MUST use periodic `PeriodicWorkRequestBuilder` with battery-not-low constraints.
- **RULE-04: Boot Restoration**: Implement a `BroadcastReceiver` listening for `ACTION_BOOT_COMPLETED` to reschedule alarms and workers after device reboots.

---

## 3. UI & State Architecture Rules
- **RULE-05: Unidirectional Data Flow (UDF)**: ViewModels expose immutable `StateFlow<UiState>`. Composables emit events up and receive state down.
- **RULE-06: Clean Currency Calculation**: All money calculations MUST use `BigDecimal` internally rather than raw floating-point `Double` to avoid IEEE-754 precision rounding errors.
