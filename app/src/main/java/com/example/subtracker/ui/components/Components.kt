package com.example.subtracker.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.subtracker.data.SpendingSummary
import com.example.subtracker.model.Category
import com.example.subtracker.model.Subscription
import com.example.subtracker.ui.theme.AlertWarning
import java.util.Locale

@Composable
fun SpendingHeaderCard(summary: SpendingSummary, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "Total Monthly Spend",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = String.format(Locale.US, "$%.2f", summary.monthlyTotal),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Yearly: " + String.format(Locale.US, "$%.2f", summary.yearlyTotal),
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "${summary.activeSubscriptionsCount} active bills",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryFilterRow(
    selectedCategory: Category?,
    onCategorySelected: (Category?) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterChip(
            selected = selectedCategory == null,
            onClick = { onCategorySelected(null) },
            label = { Text("All") }
        )
        Category.values().forEach { category ->
            FilterChip(
                selected = selectedCategory == category,
                onClick = { onCategorySelected(category) },
                label = { Text(category.label) }
            )
        }
    }
}

@Composable
fun SubscriptionCard(
    subscription: Subscription,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = subscription.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    if (subscription.isFreeTrial) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(
                            color = AlertWarning.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "TRIAL",
                                color = AlertWarning,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                val daysLeft = subscription.daysUntilDue()
                val dueText = if (daysLeft == 0L) "Due today" else "Renews in $daysLeft days"
                Text(
                    text = "${subscription.period.label} • $dueText",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (daysLeft <= 3) AlertWarning else Color.Gray
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = String.format(Locale.US, "$%.2f", subscription.amount),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                TextButton(
                    onClick = onDelete,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("Delete", color = Color.Gray, fontSize = 12.sp)
                }
            }
        }
    }
}
