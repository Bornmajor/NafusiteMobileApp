package com.majasociet.nafusitemobileapp.features.products.domain.repository

import com.majasociet.nafusitemobileapp.features.products.data.models.Category
import com.majasociet.nafusitemobileapp.features.products.data.models.Product

/**
 * Products repository interface
 */
interface ProductsRepository {
    // Get categories
    suspend fun getCategories(): Result<List<Category>>
    // Get all products
    suspend fun getProducts(): Result<List<Product>>
}