# Database Specification (DATABASE) — SubTracker

## 1. Storage Architecture
- **Engine**: SQLite via Android Room Persistence Library.
- **Encryption**: Optional SQLCipher integration for encrypted local storage.
- **DAO Pattern**: Reactive queries returning Kotlin `Flow<List<SubscriptionEntity>>`.

---

## 2. Relational Schema & Entities

### Table: `subscriptions`
```sql
CREATE TABLE subscriptions (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,                          -- e.g. "Netflix Premium"
    amount REAL NOT NULL,                        -- e.g. 15.99
    currency TEXT NOT NULL DEFAULT 'USD',        -- ISO currency code
    billing_period TEXT NOT NULL,                -- 'WEEKLY' | 'MONTHLY' | 'QUARTERLY' | 'YEARLY'
    category TEXT NOT NULL,                      -- 'STREAMING' | 'UTILITIES' | 'SOFTWARE' | 'OTHER'
    next_billing_date INTEGER NOT NULL,          -- Unix epoch millisecond
    is_free_trial INTEGER NOT NULL DEFAULT 0,    -- 0 or 1 boolean
    trial_end_date INTEGER,                      -- Unix epoch millisecond (if trial)
    cancellation_url TEXT,                       -- Optional direct web link to cancel
    notes TEXT,
    created_at INTEGER NOT NULL
);
CREATE INDEX idx_sub_next_billing ON subscriptions(next_billing_date ASC);
CREATE INDEX idx_sub_trial ON subscriptions(is_free_trial, trial_end_date ASC);
```

### Table: `billing_history`
```sql
CREATE TABLE billing_history (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    subscription_id INTEGER NOT NULL,
    paid_date INTEGER NOT NULL,
    amount_paid REAL NOT NULL,
    currency TEXT NOT NULL,
    FOREIGN KEY(subscription_id) REFERENCES subscriptions(id) ON DELETE CASCADE
);
CREATE INDEX idx_history_sub_date ON billing_history(subscription_id, paid_date DESC);
```
