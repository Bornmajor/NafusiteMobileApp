package com.majasociet.nafusitemobileapp.features.products.data.models

data class ProductsState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val categories: List<Category> = emptyList(),
    val error: String? = null
)
