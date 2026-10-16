package com.example.subtracker.model

enum class BillingPeriod(val label: String, val monthsMultiplier: Double) {
    WEEKLY("Weekly", 4.33),
    MONTHLY("Monthly", 1.0),
    QUARTERLY("Quarterly", 0.333),
    YEARLY("Yearly", 1.0 / 12.0)
}

enum class Category(val label: String) {
    STREAMING("Streaming"),
    SOFTWARE("Software & Cloud"),
    UTILITIES("Utilities"),
    WORK("Work & Productivity"),
    FITNESS("Fitness & Health"),
    OTHER("Other")
}
