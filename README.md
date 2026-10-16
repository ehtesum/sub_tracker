# SubTracker 📱

> A privacy-first, zero-cloud subscription and recurring expense tracker for Android.

Most subscription management apps today ask for your bank login credentials through third-party data brokers like Plaid, track your habits, and store your financial history on their servers. 

**SubTracker** takes the opposite approach: it runs 100% offline on your device using local SQLite (Room), requires no account creation, and alerts you before free trials convert or credit card renewal charges hit.

---

## Highlights

- 🔒 **Zero Permissions & No Cloud**: No internet permission required in the manifest. Your financial figures never leave your phone.
- ⏳ **Free Trial Guard**: Explicit tracking for trials (e.g. 7-day or 30-day). Priority notifications pop up before auto-billing triggers.
- 🔔 **Reliable Local Alerts**: Uses Android `AlarmManager` with exact alarms at 09:00 AM on configurable alert days (1, 3, or 7 days prior).
- 📊 **Normalized Spend Forecasting**: Converts weekly, monthly, quarterly, and annual billing periods into single-glance monthly and yearly projections.
- 🎨 **Modern Material 3 UI**: Built natively in Jetpack Compose with Dark Mode default, category color tagging, and fluid animations.

---

## Tech Stack

| Layer | Technology |
| :--- | :--- |
| **Language** | Kotlin 1.9+ |
| **UI Framework** | Jetpack Compose + Material 3 |
| **Database** | Room SQLite + Kotlin Coroutines `Flow` |
| **State Management** | Android Jetpack `ViewModel` + `StateFlow` |
| **Scheduling** | Android `AlarmManager` (`RTC_WAKEUP`) + BroadcastReceiver |
| **Testing** | JUnit 4 + Kotlin Coroutines Test |

---

## Project Structure

```
sub_tracker/
├── app/
│   ├── build.gradle.kts
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml
│       │   └── java/com/example/subtracker/
│       │       ├── data/           # Room Database, DAO, Repository
│       │       ├── model/          # Subscription & Enums data models
│       │       ├── ui/             # Compose Screens (Dashboard, AddSubscription)
│       │       └── worker/         # RenewalAlarmReceiver for push notifications
│       └── test/                   # JUnit unit tests
├── ARCH.md                         # Architecture & data flow specifications
├── DATABASE.md                     # SQLite table schema & indices
├── DESIGN.md                       # Design tokens, palette & typography
├── PWD.md                          # Project definition & problem statement
└── REQ.md                          # Functional & non-functional requirements
```

---

## Getting Started

### Prerequisites
- Android Studio Iguana (2023.2.1) or newer
- Android SDK 34 (compileSdk = 34, minSdk = 26)
- JDK 17

### Building from Source

```bash
# Clone the repository
git clone https://github.com/your-username/sub_tracker.git
cd sub_tracker

# Build debug APK
./gradlew assembleDebug

# Run unit tests
./gradlew testDebugUnitTest
```

---

## Unit Testing

The test suite covers billing period conversions, free trial deadline precedence, past-due date coercion, and monthly spending aggregation:

```bash
./gradlew test
```

Test file: [`app/src/test/java/com/example/subtracker/SubscriptionTest.kt`](file:///c:/Users/user/Desktop/projects/new_projects/sub_tracker/app/src/test/java/com/example/subtracker/SubscriptionTest.kt)

---

## Known Limitations

1. **Manual Entry Required**: Because SubTracker does not connect to bank APIs, you must manually log new subscriptions and price changes.
2. **Device-Specific Alarms**: On aggressive battery-saving OEM ROMs (MIUI, ColorOS), background exact alarms can be delayed unless battery optimization is disabled for the app.
3. **Single Currency Projection**: Currency conversions are calculated against a fixed base currency without live Forex exchange rate fetching.

---

## Future Work & Roadmap

- [ ] **Encrypted Local Backups**: Export and import encrypted JSON backups using Android Storage Access Framework (SAF).
- [ ] **Bank Statement CSV Import**: Parse exported CSVs from major banks (Chase, Revolut, Monzo) locally to auto-detect recurring bills.
- [ ] **Home Screen Glance Widget**: Android 12+ AppWidget showing total monthly spend and subscriptions due in the next 7 days.
- [ ] **Wear OS Companion**: Quick wrist glance tile for upcoming bill dues.

---

## License

MIT License. Feel free to use, modify, and build upon this project.
