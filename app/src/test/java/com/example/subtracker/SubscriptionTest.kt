package com.example.subtracker

import com.example.subtracker.data.SpendingSummary
import com.example.subtracker.model.BillingPeriod
import com.example.subtracker.model.Category
import com.example.subtracker.model.Subscription
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.concurrent.TimeUnit

class SubscriptionTest {

    @Test
    fun testMonthlyEquivalentCalculations() {
        val monthlySub = Subscription(
            name = "Netflix",
            amount = 15.0,
            period = BillingPeriod.MONTHLY,
            nextBillingEpoch = 0
        )
        assertEquals(15.0, monthlySub.monthlyEquivalent, 0.001)

        val yearlySub = Subscription(
            name = "Amazon Prime",
            amount = 120.0,
            period = BillingPeriod.YEARLY,
            nextBillingEpoch = 0
        )
        assertEquals(10.0, yearlySub.monthlyEquivalent, 0.001)

        val weeklySub = Subscription(
            name = "Meal Kit",
            amount = 20.0,
            period = BillingPeriod.WEEKLY,
            nextBillingEpoch = 0
        )
        assertEquals(20.0 * 4.33, weeklySub.monthlyEquivalent, 0.001)

        val quarterlySub = Subscription(
            name = "Security Suite",
            amount = 30.0,
            period = BillingPeriod.QUARTERLY,
            nextBillingEpoch = 0
        )
        assertEquals(30.0 * 0.333, quarterlySub.monthlyEquivalent, 0.001)
    }

    @Test
    fun testDaysUntilDueAndDueSoon() {
        val now = 1_000_000_000_000L
        val twoDaysLater = now + TimeUnit.DAYS.toMillis(2)
        val fiveDaysLater = now + TimeUnit.DAYS.toMillis(5)

        val dueSoonSub = Subscription(
            name = "Spotify",
            amount = 9.99,
            period = BillingPeriod.MONTHLY,
            nextBillingEpoch = twoDaysLater
        )
        assertEquals(2L, dueSoonSub.daysUntilDue(now))
        assertTrue(dueSoonSub.isDueSoon(thresholdDays = 3, now = now))

        val dueLaterSub = Subscription(
            name = "Gym",
            amount = 45.0,
            period = BillingPeriod.MONTHLY,
            nextBillingEpoch = fiveDaysLater
        )
        assertEquals(5L, dueLaterSub.daysUntilDue(now))
        assertFalse(dueLaterSub.isDueSoon(thresholdDays = 3, now = now))
    }

    @Test
    fun testPastDueSubscriptionDoesNotReturnNegativeDays() {
        val now = 1_000_000_000_000L
        val threeDaysAgo = now - TimeUnit.DAYS.toMillis(3)

        val overdueSub = Subscription(
            name = "Past Due Bill",
            amount = 25.0,
            period = BillingPeriod.MONTHLY,
            nextBillingEpoch = threeDaysAgo
        )

        // coerceAtLeast(0) ensures no negative days
        assertEquals(0L, overdueSub.daysUntilDue(now))
        assertTrue(overdueSub.isDueSoon(thresholdDays = 1, now = now))
    }

    @Test
    fun testFreeTrialEndEpochTakesPrecedence() {
        val now = 1_000_000_000_000L
        val trialEndsInOneDay = now + TimeUnit.DAYS.toMillis(1)
        val billingInThirtyDays = now + TimeUnit.DAYS.toMillis(30)

        val trialSub = Subscription(
            name = "Audible Trial",
            amount = 14.99,
            period = BillingPeriod.MONTHLY,
            nextBillingEpoch = billingInThirtyDays,
            isFreeTrial = true,
            trialEndEpoch = trialEndsInOneDay
        )

        assertEquals(1L, trialSub.daysUntilDue(now))
        assertTrue(trialSub.isDueSoon(thresholdDays = 2, now = now))
    }

    @Test
    fun testSpendingSummaryAggregation() {
        val subs = listOf(
            Subscription(name = "Netflix", amount = 15.0, period = BillingPeriod.MONTHLY, nextBillingEpoch = 0, category = Category.STREAMING),
            Subscription(name = "Cloud Backup", amount = 120.0, period = BillingPeriod.YEARLY, nextBillingEpoch = 0, category = Category.SOFTWARE),
            Subscription(name = "Trial App", amount = 0.0, isFreeTrial = true, period = BillingPeriod.MONTHLY, nextBillingEpoch = 0, category = Category.WORK)
        )

        val monthly = subs.sumOf { it.monthlyEquivalent }
        val summary = SpendingSummary(
            monthlyTotal = monthly,
            yearlyTotal = monthly * 12.0,
            activeSubscriptionsCount = subs.size,
            activeTrialsCount = subs.count { it.isFreeTrial }
        )

        assertEquals(25.0, summary.monthlyTotal, 0.001)
        assertEquals(300.0, summary.yearlyTotal, 0.001)
        assertEquals(3, summary.activeSubscriptionsCount)
        assertEquals(1, summary.activeTrialsCount)
    }

    @Test
    fun testCategoryFilteringAndGrouping() {
        val subs = listOf(
            Subscription(name = "Disney+", amount = 10.0, period = BillingPeriod.MONTHLY, nextBillingEpoch = 0, category = Category.STREAMING),
            Subscription(name = "Hulu", amount = 15.0, period = BillingPeriod.MONTHLY, nextBillingEpoch = 0, category = Category.STREAMING),
            Subscription(name = "JetBrains", amount = 25.0, period = BillingPeriod.MONTHLY, nextBillingEpoch = 0, category = Category.SOFTWARE)
        )

        val streamingTotal = subs
            .filter { it.category == Category.STREAMING }
            .sumOf { it.monthlyEquivalent }

        val softwareTotal = subs
            .filter { it.category == Category.SOFTWARE }
            .sumOf { it.monthlyEquivalent }

        assertEquals(25.0, streamingTotal, 0.001)
        assertEquals(25.0, softwareTotal, 0.001)
    }
}
