package com.majasociet.nafusitemobileapp.features.products.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.majasociet.nafusitemobileapp.features.products.data.models.Product
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductsRepositoryImplTest {

    private lateinit var repository: ProductsRepositoryImpl
    private val database: FirebaseDatabase = mockk()
    private val databaseReference: DatabaseReference = mockk()

    @Before
    fun setUp() {
        mockkStatic("kotlinx.coroutines.tasks.TasksKt")
        every { database.reference } returns databaseReference
        repository = ProductsRepositoryImpl(database)
    }

    @After
    fun tearDown() {
        unmockkStatic("kotlinx.coroutines.tasks.TasksKt")
    }

    @Test
    fun `getCategories success returns list of categories`() = runTest {
        // Given
        val task: Task<DataSnapshot> = mockk()
        val snapshot: DataSnapshot = mockk()
        val childSnapshot: DataSnapshot = mockk()
        val titleSnapshot: DataSnapshot = mockk()

        every { databaseReference.child("categories") } returns databaseReference
        every { databaseReference.get() } returns task
        coEvery { task.await() } returns snapshot

        every { snapshot.children } returns listOf(childSnapshot)
        every { childSnapshot.key } returns "cat1"
        every { childSnapshot.child("title") } returns titleSnapshot
        every { titleSnapshot.getValue(String::class.java) } returns "Category 1"

        // When
        val result = repository.getCategories()

        // Then
        assertTrue(result.isSuccess)
        val categories = result.getOrNull()
        assertEquals(1, categories?.size)
        assertEquals("cat1", categories?.get(0)?.id)
        assertEquals("Category 1", categories?.get(0)?.title)
    }

    @Test
    fun `getProducts success returns list of products`() = runTest {
        // Given
        val task: Task<DataSnapshot> = mockk()
        val snapshot: DataSnapshot = mockk()
        val childSnapshot: DataSnapshot = mockk()
        val product = Product(name = "Product 1")

        every { databaseReference.child("products") } returns databaseReference
        every { databaseReference.get() } returns task
        coEvery { task.await() } returns snapshot

        every { snapshot.children } returns listOf(childSnapshot)
        every { childSnapshot.key } returns "prod1"
        every { childSnapshot.getValue(Product::class.java) } returns product

        // When
        val result = repository.getProducts()

        // Then
        assertTrue(result.isSuccess)
        val products = result.getOrNull()
        assertEquals(1, products?.size)
        assertEquals("prod1", products?.get(0)?.id)
        assertEquals("Product 1", products?.get(0)?.name)
    }

    @Test
    fun `getProducts failure returns error`() = runTest {
        // Given
        val task: Task<DataSnapshot> = mockk()
        every { databaseReference.child("products") } returns databaseReference
        every { databaseReference.get() } returns task
        coEvery { task.await() } throws Exception("Database error")

        // When
        val result = repository.getProducts()

        // Then
        assertTrue(result.isFailure)
        assertEquals("Database error", result.exceptionOrNull()?.message)
    }
}
