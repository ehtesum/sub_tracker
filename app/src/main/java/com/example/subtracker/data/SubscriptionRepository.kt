package com.example.subtracker.data

import com.example.subtracker.model.Subscription
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class SpendingSummary(
    val monthlyTotal: Double,
    val yearlyTotal: Double,
    val activeSubscriptionsCount: Int,
    val activeTrialsCount: Int
)

class SubscriptionRepository(private val dao: SubscriptionDao) {

    val subscriptions: Flow<List<Subscription>> = dao.getAllSubscriptions()

    val spendingSummary: Flow<SpendingSummary> = subscriptions.map { list ->
        val monthly = list.sumOf { it.monthlyEquivalent }
        val trialsCount = list.count { it.isFreeTrial }
        SpendingSummary(
            monthlyTotal = monthly,
            yearlyTotal = monthly * 12.0,
            activeSubscriptionsCount = list.size,
            activeTrialsCount = trialsCount
        )
    }

    suspend fun addSubscription(subscription: Subscription): Long {
        return dao.insertSubscription(subscription)
    }

    suspend fun updateSubscription(subscription: Subscription) {
        dao.updateSubscription(subscription)
    }

    suspend fun deleteSubscription(subscription: Subscription) {
        dao.deleteSubscription(subscription)
    }
}
