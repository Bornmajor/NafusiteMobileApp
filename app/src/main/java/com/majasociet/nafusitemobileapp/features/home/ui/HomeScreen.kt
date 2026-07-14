package com.majasociet.nafusitemobileapp.features.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.majasociet.nafusitemobileapp.features.auth.ui.viewmodel.AuthViewModel
import com.majasociet.nafusitemobileapp.features.home.ui.components.CategoryGroup
import com.majasociet.nafusitemobileapp.features.home.ui.components.HorizontalScrollableCategory
import com.majasociet.nafusitemobileapp.features.products.data.models.Category
import com.majasociet.nafusitemobileapp.features.products.ui.viewmodel.ProductsState
import com.majasociet.nafusitemobileapp.features.products.ui.viewmodel.ProductsViewModel
import com.majasociet.nafusitemobileapp.shared.components.AppLoaderUI
import com.majasociet.nafusitemobileapp.shared.components.BottomTabScreenScaffold
import com.majasociet.nafusitemobileapp.shared.components.ErrorMessage
import com.majasociet.nafusitemobileapp.ui.theme.AppTheme

@Composable
fun HomeScreen(
    authViewModel: AuthViewModel,
    navigateToProfile: () -> Unit,
    navigateToSearch: () -> Unit,
    navigateToCategory: (Category) -> Unit,
    productsViewModel: ProductsViewModel
) {
    val productsState = productsViewModel.productsState.collectAsStateWithLifecycle().value

    BottomTabScreenScaffold(
        navigateToProfile = navigateToProfile,
        navigateToSearch = navigateToSearch
    ) {
        HomeScreenContent(
            state = productsState,
            navigateToCategory = navigateToCategory
        )
    }
}

@Composable
fun HomeScreenContent(
    state: ProductsState,
    navigateToCategory: (Category) -> Unit
) {
    when {
        state.isLoading -> {
            AppLoaderUI(
                modifier = Modifier.fillMaxSize(),
                message = "Loading content..."
            )
        }

        state.error != null -> {
            ErrorMessage(
                modifier = Modifier.fillMaxSize(),
                message = state.error
            )
        }

        else -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(AppTheme.spacing.medium),
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.medium),
            ) {
                item {
                    HorizontalScrollableCategory(
                        categories = state.categories,
                        onSelectCategory = navigateToCategory
                    )
                }

                items(state.categories) { category ->
                    CategoryGroup(
                        title = category.title,
                        products = state.products.filter { it.category == category.id },
                        onSelectProduct = {
                            // Navigate to product detail screen
                        },
                        onExpandCategory = {
                            // Navigate to category screen
                        }
                    )
                }
            }
        }
    }
}