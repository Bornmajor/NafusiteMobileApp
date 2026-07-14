package com.majasociet.nafusitemobileapp.features.products.ui.viewmodel

import app.cash.turbine.test
import com.majasociet.nafusitemobileapp.features.products.data.models.Category
import com.majasociet.nafusitemobileapp.features.products.data.models.Product
import com.majasociet.nafusitemobileapp.features.products.domain.repository.ProductsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductsViewModelTest {

    private val productsRepository: ProductsRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init calls loadShopContent and updates state with products and categories on success`() = runTest {
        // Given
        val categories = listOf(Category("1", "Category 1"))
        val products = listOf(Product("1", "Product 1"))
        
        coEvery { productsRepository.getCategories() } returns Result.success(categories)
        coEvery { productsRepository.getProducts() } returns Result.success(products)

        // When
        val viewModel = ProductsViewModel(productsRepository)
        advanceUntilIdle()

        // Then
        assertEquals(categories, viewModel.productsState.value.categories)
        assertEquals(products, viewModel.productsState.value.products)
        assertEquals(false, viewModel.productsState.value.isLoading)
        assertEquals(null, viewModel.productsState.value.error)
    }

    @Test
    fun `init updates state with error and emits event when categories fetch fails`() = runTest {
        // Given
        val errorMessage = "Failed to load categories"
        coEvery { productsRepository.getCategories() } returns Result.failure(Exception(errorMessage))
        coEvery { productsRepository.getProducts() } returns Result.success(emptyList())

        // When
        val viewModel = ProductsViewModel(productsRepository)
        
        viewModel.productEvent.test {
            advanceUntilIdle()
            val event = awaitItem()
            assert(event is ProductsEvent.FailureFetchCategories)
            assertEquals("Failed to load categories", (event as ProductsEvent.FailureFetchCategories).message)
        }

        // Then
        assert(viewModel.productsState.value.error?.contains(errorMessage) == true)
        assertEquals(false, viewModel.productsState.value.isLoading)
    }

    @Test
    fun `init updates state with error and emits event when products fetch fails`() = runTest {
        // Given
        val errorMessage = "Failed to load products"
        coEvery { productsRepository.getCategories() } returns Result.success(emptyList())
        coEvery { productsRepository.getProducts() } returns Result.failure(Exception(errorMessage))

        // When
        val viewModel = ProductsViewModel(productsRepository)

        viewModel.productEvent.test {
            advanceUntilIdle()
            val event = awaitItem()
            assert(event is ProductsEvent.FailureFetchProducts)
            assert((event as ProductsEvent.FailureFetchProducts).message.contains(errorMessage))
        }

        // Then
        assert(viewModel.productsState.value.error?.contains(errorMessage) == true)
        assertEquals(false, viewModel.productsState.value.isLoading)
    }
}
