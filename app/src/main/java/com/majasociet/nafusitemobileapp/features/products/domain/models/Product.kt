package com.majasociet.nafusitemobileapp.features.products.domain.models

/**
 * Domain representation of a Product.
 */
data class Product(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val price: Int = 0,
    val imageUrl: String = "",
    val category: String = "",
)
