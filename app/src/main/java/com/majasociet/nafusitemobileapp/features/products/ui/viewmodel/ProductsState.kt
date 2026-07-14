package com.majasociet.nafusitemobileapp.features.products.ui.viewmodel

import com.majasociet.nafusitemobileapp.features.products.domain.models.Category
import com.majasociet.nafusitemobileapp.features.products.domain.models.Product

/**
 * UI State for the Products/Home screens.
 */
data class ProductsState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val categories: List<Category> = emptyList(),
    val error: String? = null
)
