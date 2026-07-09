package com.majasociet.nafusitemobileapp.features.products.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.majasociet.nafusitemobileapp.features.products.data.models.ProductsState
import com.majasociet.nafusitemobileapp.features.products.domain.repository.ProductsRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Products view model - handles products logic
 * @param productsRepository - products repository
 */
class ProductsViewModel(
    private val productsRepository: ProductsRepository
): ViewModel() {

    private val _productsState = MutableStateFlow<ProductsState>(ProductsState())
    val productsState = _productsState.asStateFlow()

    private val _productEvent = MutableSharedFlow<ProductsEvent>()
    val productEvent = _productEvent.asSharedFlow()

    init {
        Timber.d("ShopViewModel initialized. Fetching concurrent shop data...")
        loadShopContent()
    }

    private fun loadShopContent() {
        // Set loading to true once at the start
        _productsState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            try {
                // 1. Kick off BOTH requests concurrently using async
                val categoriesDeferred = async { productsRepository.getCategories() }
                val productsDeferred = async { productsRepository.getProducts() }

                // 2. Wait for both results to complete using await()
                val categoriesResult = categoriesDeferred.await()
                val productsResult = productsDeferred.await()

                // 3. Process the results together safely
                var categoriesList = _productsState.value.categories
                var productsList = _productsState.value.products
                var errorMessage: String? = null

                categoriesResult.onSuccess { categoriesList = it }
                categoriesResult.onFailure { exception ->
                    errorMessage = "Failed to load categories: ${exception.localizedMessage}"
                    _productEvent.emit(ProductsEvent.FailureFetchCategories("Failed to load categories"))
                    Timber.e(exception, exception.localizedMessage)
                }

                productsResult.onSuccess { productsList = it }
                productsResult.onFailure { exception ->
                    // Keep existing error if categories already failed, or use this one
                    if (errorMessage == null) errorMessage = "Failed to load products : ${exception.localizedMessage}"
                    _productEvent.emit(ProductsEvent.FailureFetchProducts("Failed to load products: ${exception.localizedMessage}" ))
                    Timber.e(exception, exception.localizedMessage)
                }

                // 4. Update the UI state exactly ONCE
                _productsState.update {
                    it.copy(
                        isLoading = false,
                        categories = categoriesList,
                        products = productsList,
                        error = errorMessage
                    )
                }

            } catch (e: Exception) {
                // Catch any unexpected coroutine exceptions
                _productsState.update { it.copy(isLoading = false, error = "An unexpected error occurred") }
                Timber.e(e, "Exception inside loadShopContent")
            }
        }
    }

    // You can safely delete the standalone fetchCategories() and fetchProducts() functions now!
}

sealed interface ProductsEvent{
    data class FailureFetchCategories(val message: String): ProductsEvent
    data class FailureFetchProducts(val message: String): ProductsEvent
}