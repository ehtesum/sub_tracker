# Architecture Specification (ARCH) — SubTracker

## 1. High-Level System Architecture

```
+--------------------------------------------------------------------------+
|                        Jetpack Compose UI Layer                          |
|         [DashboardScreen]     [CalendarScreen]     [AnalyticsScreen]     |
+--------------------------------------------------------------------------+
                                     │ (StateFlow / User Events)
+--------------------------------------------------------------------------+
|                      ViewModel State Management Layer                    |
|             (SubscriptionViewModel, AnalyticsViewModel)                  |
+--------------------------------------------------------------------------+
                                     │
+--------------------------------------------------------------------------+
|                           Repository Layer                               |
|                     (SubscriptionRepositoryImpl)                         |
+--------------------------------------------------------------------------+
             │                                              │
             ▼                                              ▼
+--------------------------+                  +----------------------------+
| Room Database (SQLite)   |                  | WorkManager Alarm Engine   |
| - SubscriptionDao        |                  | - DailyCheckWorker         |
| - BillingHistoryDao      |                  | - NotificationDispatcher   |
+--------------------------+                  +----------------------------+
```

---

## 2. Notification Pipeline Flow

```
Daily Trigger at 09:00 AM (WorkManager)
              │
              ▼
`DailyCheckWorker` queries Room DB
              │
              ▼
Finds subscriptions where:
(next_billing_date - today) IN (7 days, 3 days, 1 day)
OR (trial_end_date - today) <= 3 days
              │
              ▼
Constructs Local Notification via Android `NotificationCompat.Builder`
              │
              ▼
User taps notification -> Launches app directly into subscription detail sheet
```
