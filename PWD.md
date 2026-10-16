# Project Work Definition (PWD) — SubTracker

## 1. Executive Summary & Vision
SubTracker is an offline, privacy-first personal subscription and recurring bill manager for Android. It solves a universal consumer frustration: recurring charges, forgotten free trials, and silent price hikes. Unlike mainstream alternatives (Rocket Money, Mint) that demand sensitive bank login credentials and sell financial telemetry, SubTracker keeps all financial data strictly on the local device, delivering timely alerts before renewals and trials expire.

---

## 2. Core Value Proposition
- **100% Offline & Private**: Zero cloud servers, zero analytics SDKs, zero financial data aggregators.
- **Smart Renewal Alerts**: Configurable push notifications 1, 3, or 7 days before recurring charges hit.
- **Free Trial Guard**: Explicit tracking for limited-time trials with direct cancellation reminders.
- **Spending Analytics**: Clear monthly and yearly projection charts without spreadsheet complexity.
- **Local Backup & Restore**: Export encrypted JSON backups to internal storage or local SD card.

---

## 3. Target User Personas
- **Privacy-Conscious Individuals**: Users refusing to link bank credentials to cloud-based aggregators.
- **Students & Freelancers**: People balancing tight budgets across multiple SaaS tools, streaming platforms, and utilities.
- **Frequent Free-Trial Testers**: Users who frequently test 7-day or 30-day services and want to avoid unwanted automatic card charges.

---

## 4. MVP Feature Scope
1. Subscription cataloging (name, cost, billing cycle, category, payment method).
2. Renewal calendar and timeline view showing upcoming monthly expenses.
3. System notifications triggered via Android WorkManager at 09:00 AM on scheduled alert days.
4. Currency selector with multi-currency conversion support using manual conversion rates.
5. Export/import functionality using AES-encrypted JSON files.
