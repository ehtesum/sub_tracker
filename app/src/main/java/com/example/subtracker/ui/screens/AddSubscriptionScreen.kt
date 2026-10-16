package com.example.subtracker.ui.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.subtracker.model.BillingPeriod
import com.example.subtracker.model.Category
import java.util.concurrent.TimeUnit

data class TemplatePreset(val name: String, val amount: Double, val category: Category)

val QuickTemplates = listOf(
    TemplatePreset("Netflix", 15.99, Category.STREAMING),
    TemplatePreset("Spotify", 10.99, Category.STREAMING),
    TemplatePreset("GitHub Copilot", 10.00, Category.SOFTWARE),
    TemplatePreset("iCloud+", 2.99, Category.SOFTWARE),
    TemplatePreset("Gym Membership", 35.00, Category.FITNESS)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSubscriptionScreen(
    onDismiss: () -> Unit,
    onSave: (name: String, amount: Double, period: BillingPeriod, category: Category, isTrial: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf("") }
    var amountText by remember { mutableStateOf("") }
    var period by remember { mutableStateOf(BillingPeriod.MONTHLY) }
    var category by remember { mutableStateOf(Category.STREAMING) }
    var isFreeTrial by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("New Subscription") },
                navigationIcon = {
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Quick Presets:", style = MaterialTheme.typography.labelMedium)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                QuickTemplates.forEach { t ->
                    SuggestionChip(
                        onClick = {
                            name = t.name
                            amountText = t.amount.toString()
                            category = t.category
                        },
                        label = { Text(t.name) }
                    )
                }
            }

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Subscription Name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = amountText,
                onValueChange = { amountText = it },
                label = { Text("Price (USD)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Billing Period: ${period.label}")
                TextButton(onClick = {
                    val nextIndex = (period.ordinal + 1) % BillingPeriod.values().size
                    period = BillingPeriod.values()[nextIndex]
                }) {
                    Text("Change")
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Category: ${category.label}")
                TextButton(onClick = {
                    val nextIndex = (category.ordinal + 1) % Category.values().size
                    category = Category.values()[nextIndex]
                }) {
                    Text("Change")
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                Text("Is this a Free Trial?")
                Switch(checked = isFreeTrial, onCheckedChange = { isFreeTrial = it })
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    val parsedAmount = amountText.toDoubleOrNull() ?: 0.0
                    if (name.isNotBlank() && parsedAmount > 0.0) {
                        onSave(name.trim(), parsedAmount, period, category, isFreeTrial)
                    }
                },
                enabled = name.isNotBlank() && (amountText.toDoubleOrNull() ?: 0.0) > 0.0,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Save Subscription")
            }
        }
    }
}
