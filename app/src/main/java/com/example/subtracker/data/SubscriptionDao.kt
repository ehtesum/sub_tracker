package com.example.subtracker.data

import androidx.room.*
import com.example.subtracker.model.Subscription
import kotlinx.coroutines.flow.Flow

@Dao
interface SubscriptionDao {
    @Query("SELECT * FROM subscriptions ORDER BY nextBillingEpoch ASC")
    fun getAllSubscriptions(): Flow<List<Subscription>>

    @Query("SELECT * FROM subscriptions WHERE isFreeTrial = 1 ORDER BY trialEndEpoch ASC")
    fun getActiveFreeTrials(): Flow<List<Subscription>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubscription(subscription: Subscription): Long

    @Update
    suspend fun updateSubscription(subscription: Subscription)

    @Delete
    suspend fun deleteSubscription(subscription: Subscription)

    @Query("SELECT * FROM subscriptions WHERE nextBillingEpoch <= :untilEpoch")
    suspend fun getDueBefore(untilEpoch: Long): List<Subscription>
}
