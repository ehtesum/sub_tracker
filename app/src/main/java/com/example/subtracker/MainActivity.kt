package com.example.subtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import com.example.subtracker.data.AppDatabase
import com.example.subtracker.data.SubscriptionRepository
import com.example.subtracker.ui.screens.AddSubscriptionScreen
import com.example.subtracker.ui.screens.DashboardScreen
import com.example.subtracker.ui.theme.DarkColors
import com.example.subtracker.ui.viewmodel.SubscriptionViewModel
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getInstance(applicationContext)
        val repository = SubscriptionRepository(database.subscriptionDao())
        val viewModel = SubscriptionViewModel(repository)

        setContent {
            MaterialTheme(colorScheme = DarkColors) {
                var showAddScreen by remember { mutableStateOf(false) }

                if (showAddScreen) {
                    AddSubscriptionScreen(
                        onDismiss = { showAddScreen = false },
                        onSave = { name, amount, period, category, isTrial ->
                            val now = System.currentTimeMillis()
                            val nextDue = now + TimeUnit.DAYS.toMillis(30)
                            val trialEnd = if (isTrial) now + TimeUnit.DAYS.toMillis(7) else null

                            viewModel.addSubscription(
                                name = name,
                                amount = amount,
                                period = period,
                                category = category,
                                nextBillingEpoch = nextDue,
                                isFreeTrial = isTrial,
                                trialEndEpoch = trialEnd
                            )
                            showAddScreen = false
                        }
                    )
                } else {
                    DashboardScreen(
                        viewModel = viewModel,
                        onNavigateToAdd = { showAddScreen = true }
                    )
                }
            }
        }
    }
}
