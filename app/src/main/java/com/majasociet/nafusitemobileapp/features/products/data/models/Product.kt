package com.majasociet.nafusitemobileapp.features.products.data.models

/**
 * This is the data class for a product.
 * @param id The unique identifier for the product.
 * @param name The name of the product.
 * @param description A description of the product.
 * @param price The price of the product.
 * @param imageUrl The URL of the product image.
 * @param category The category of the product.
 */
data class Product(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val price: Int = 0,
    val imageUrl: String = "",
    val category: String = "",
)
