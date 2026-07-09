package com.majasociet.nafusitemobileapp.features.home.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.majasociet.nafusitemobileapp.features.products.data.models.Category
import com.majasociet.nafusitemobileapp.ui.theme.AppTheme

/**
 * This is a horizontal scrollable list of categories.
 * @param categories The list of categories to display.
 * @param onSelectCategory The callback to be invoked when a category is selected.
 */
@Composable
fun HorizontalScrollableCategory(
    categories: List<Category>,
    onSelectCategory: (Category) -> Unit
){
    LazyRow (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            AppTheme.spacing.small
        )
    ) {
        items(categories){ category ->
          CategoryTab(
              name = category.title,
              onClick = {
                  onSelectCategory(category)
              }
          )

        }
    }

}