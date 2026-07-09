package com.majasociet.nafusitemobileapp.features.products.data.repository

import com.google.firebase.database.FirebaseDatabase
import com.majasociet.nafusitemobileapp.features.products.data.models.Category
import com.majasociet.nafusitemobileapp.features.products.data.models.Product
import com.majasociet.nafusitemobileapp.features.products.domain.repository.ProductsRepository
import kotlinx.coroutines.tasks.await

/**
 * Products repository implementation
 * @param database - firebase database
 */
class ProductsRepositoryImpl(
    private val database: FirebaseDatabase,
): ProductsRepository {
    override suspend fun getCategories(): Result<List<Category>> {
        return try{
            // Fetch the snapshot using Coroutine tasks await extension
            val snapshot = database.reference.child("categories").get().await()
            val categoryList = mutableListOf<Category>()

            for (childSnapshot in snapshot.children) {
                // 1. Parse the inner fields (e.g., title: "Bracelets")
                val title = childSnapshot.child("title").getValue(String::class.java) ?: ""

                // 2. Grab the actual node key slug ("bracelets", "rings")
                val id = childSnapshot.key ?: ""

                categoryList.add(Category(id = id, title = title))
            }

            Result.success(categoryList)

        }catch (e: Exception){
            return Result.failure(e)
        }
    }

    override suspend fun getProducts(): Result<List<Product>> {
        return try{
            val snapshot = database.reference.child("products").get().await();
            val productList = mutableListOf<Product>()

            for (childSnapshot in snapshot.children) {
                // Parse the JSON directly into our Product class structure
                val product = childSnapshot.getValue(Product::class.java)

                if (product != null) {
                    // Grab the key slug (e.g., "prod_212e61...") and attach it as the ID
                    val productId = childSnapshot.key ?: ""
                    val finalizedProduct = product.copy(id = productId)

                    productList.add(finalizedProduct)
                }
            }
            Result.success(productList);

        }catch(e: Exception){
            Result.failure(e);
        }

    }

}