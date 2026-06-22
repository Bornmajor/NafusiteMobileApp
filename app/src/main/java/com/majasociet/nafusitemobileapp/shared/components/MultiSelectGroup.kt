package com.majasociet.nafusitemobileapp.shared.components
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.majasociet.nafusitemobileapp.ui.theme.AppTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MultiSelectPillGroup(
    items: List<String>,
    selectedItems: Set<String>, // Uses a Set to track multi-selection uniquely
    onSelectionChanged: (Set<String>) -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth(),
    horizontalSpacing: Dp = 8.dp,
    verticalSpacing: Dp = 8.dp
) {
    // FlowRow automatically wraps items to the next line when they overflow horizontally
    FlowRow(
        modifier = modifier,
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(horizontalSpacing),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(verticalSpacing)
    ) {
        items.forEach { item ->
            val isSelected = selectedItems.contains(item)

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp)) // Gives it a smooth "pill" appearance
                    .background(
                        if (isSelected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                    )
                    .border(
                        width = 1.dp,
                        color = if (isSelected) Color.Transparent else MaterialTheme.colorScheme.outlineVariant,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .clickable {
                        // Create a mutable copy of the current set to alter selection values
                        val updatedSelection = selectedItems.toMutableSet()
                        if (isSelected) {
                            updatedSelection.remove(item) // Uncheck if already selected
                        } else {
                            updatedSelection.add(item) // Add if newly selected
                        }
                        onSelectionChanged(updatedSelection)
                    }
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                Text(
                    text = item,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isSelected) MaterialTheme.colorScheme.onPrimary
                    else MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}