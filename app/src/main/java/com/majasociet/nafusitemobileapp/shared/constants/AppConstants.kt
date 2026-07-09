package com.majasociet.nafusitemobileapp.shared.constants

import androidx.compose.runtime.Stable
import com.majasociet.nafusitemobileapp.features.products.data.models.Category
import com.majasociet.nafusitemobileapp.features.products.data.models.Product

@Stable
object AppConstants {
    const val DEFAULT_AVATAR_URL = "https://res.cloudinary.com/yaal5uun/image/upload/v1782973019/image_placeholder_hlglbe.png"
    val DUMMY_CATEGORIES: List<Category> = listOf(
        Category(id = "1", title = "Category 1"),
        Category(id = "2", title = "Category 2"),
        Category(id = "3", title = "Category 3"),
        Category(id = "4", title = "Category 4"),
        Category(id = "5", title = "Category 5"),
    )

    val DUMMY_PRODUCTS: List<Product> = listOf(
        Product(
            id = "1",
            name = "Product 1",
            description = "Description",
            price = 1000,
            imageUrl = "https://res.cloudinary.com/ducxbf5ex/image/upload/v1739710968/nafusite-products/Rings/mqx21mzzodqlwtws1eoi.jpg",
            category = "Category 1"
        ),
        Product(
            id = "2",
            name = "Product 2",
            description = "Description",
            price = 1000,
            imageUrl = "https://res.cloudinary.com/ducxbf5ex/image/upload/v1739710968/nafusite-products/Rings/mqx21mzzodqlwtws1eoi.jpg",
            category = "Category 1"
        ),
        Product(
            id = "3",
            name = "Product 3",
            description = "Description",
            price = 1000,
            imageUrl = "https://res.cloudinary.com/ducxbf5ex/image/upload/v1739710968/nafusite-products/Rings/mqx21mzzodqlwtws1eoi.jpg",
            category = "Category 1"
        ),
        Product(
            id = "4",
            name = "Product 4",
            description = "Description",
            price = 2000,
            imageUrl = "https://res.cloudinary.com/ducxbf5ex/image/upload/v1739657485/nafusite-products/szb70sbxyopqbsnwdaa4.jpg",
            category = "Category 2"
        ),
        Product(
            id = "5",
            name = "Product 5",
            description = "Description",
            price = 2000,
            imageUrl = "https://res.cloudinary.com/ducxbf5ex/image/upload/v1739657485/nafusite-products/szb70sbxyopqbsnwdaa4.jpg",
            category = "Category 2"
        ),
        Product(
            id = "6",
            name = "Product 6",
            description = "Description",
            price = 2000,
            imageUrl = "https://res.cloudinary.com/ducxbf5ex/image/upload/v1739657485/nafusite-products/szb70sbxyopqbsnwdaa4.jpg",
            category = "Category 2"
        ),




    )

}