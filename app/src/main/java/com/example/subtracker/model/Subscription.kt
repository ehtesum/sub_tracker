package com.example.subtracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.concurrent.TimeUnit

@Entity(tableName = "subscriptions")
data class Subscription(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val amount: Double,
    val currency: String = "USD",
    val period: BillingPeriod = BillingPeriod.MONTHLY,
    val category: Category = Category.OTHER,
    val nextBillingEpoch: Long,
    val isFreeTrial: Boolean = false,
    val trialEndEpoch: Long? = null,
    val cancellationUrl: String = ""
) {
    val monthlyEquivalent: Double
        get() = amount * period.monthsMultiplier

    fun daysUntilDue(now: Long = System.currentTimeMillis()): Long {
        val targetEpoch = if (isFreeTrial && trialEndEpoch != null) trialEndEpoch else nextBillingEpoch
        val diff = targetEpoch - now
        return TimeUnit.MILLISECONDS.toDays(diff).coerceAtLeast(0)
    }

    fun isDueSoon(thresholdDays: Long = 3, now: Long = System.currentTimeMillis()): Boolean {
        return daysUntilDue(now) in 0..thresholdDays
    }
}
