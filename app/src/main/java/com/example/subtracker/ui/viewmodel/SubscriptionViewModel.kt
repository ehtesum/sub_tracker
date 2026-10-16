package com.example.subtracker.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.subtracker.data.SpendingSummary
import com.example.subtracker.data.SubscriptionRepository
import com.example.subtracker.model.BillingPeriod
import com.example.subtracker.model.Category
import com.example.subtracker.model.Subscription
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class DashboardUiState(
    val subscriptions: List<Subscription> = emptyList(),
    val summary: SpendingSummary = SpendingSummary(0.0, 0.0, 0, 0),
    val selectedCategory: Category? = null
)

class SubscriptionViewModel(private val repository: SubscriptionRepository) : ViewModel() {

    private val _selectedCategory = MutableStateFlow<Category?>(null)

    val uiState: StateFlow<DashboardUiState> = combine(
        repository.subscriptions,
        repository.spendingSummary,
        _selectedCategory
    ) { subs, summary, cat ->
        val filtered = if (cat == null) subs else subs.filter { it.category == cat }
        DashboardUiState(
            subscriptions = filtered,
            summary = summary,
            selectedCategory = cat
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DashboardUiState()
    )

    fun selectCategory(category: Category?) {
        _selectedCategory.value = category
    }

    fun addSubscription(
        name: String,
        amount: Double,
        period: BillingPeriod,
        category: Category,
        nextBillingEpoch: Long,
        isFreeTrial: Boolean = false,
        trialEndEpoch: Long? = null
    ) {
        viewModelScope.launch {
            val sub = Subscription(
                name = name,
                amount = amount,
                period = period,
                category = category,
                nextBillingEpoch = nextBillingEpoch,
                isFreeTrial = isFreeTrial,
                trialEndEpoch = trialEndEpoch
            )
            repository.addSubscription(sub)
        }
    }

    fun deleteSubscription(subscription: Subscription) {
        viewModelScope.launch {
            repository.deleteSubscription(subscription)
        }
    }
}
