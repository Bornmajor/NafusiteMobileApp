package com.majasociet.nafusitemobileapp.features.home.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.majasociet.nafusitemobileapp.R
import com.majasociet.nafusitemobileapp.features.products.data.models.Category
import com.majasociet.nafusitemobileapp.features.products.data.models.Product
import com.majasociet.nafusitemobileapp.features.products.ui.components.ProductCard
import com.majasociet.nafusitemobileapp.ui.theme.AppTheme

/**
 * This is the category group for a category.
 */
@Composable
fun CategoryGroup(
    onExpandCategory: (Category) -> Unit,
    title: String,
    products: List<Product>,
    onSelectProduct: (Product) -> Unit
    ){

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        HeaderGroup(
            title = title,
            onExpandCategory = onExpandCategory
        )
        if(products.isNotEmpty()){
            ProductsGroup(
                products = products,
                onSelectProduct = onSelectProduct
            )
        }else{
            Text(
                text = "No products found",
                style = MaterialTheme.typography.bodyMedium
            )
        }

    }

}

/**
 * This is the products group for a category.
 */
@Composable
fun ProductsGroup(
    products: List<Product>,
    onSelectProduct: (Product) -> Unit
){
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            AppTheme.spacing.medium
        )
    ) {
        items(products){
            product ->
            ProductCard(
                product = product,
                onSelectProduct = onSelectProduct
            )
        }
    }
}

/**
 * This is the header group for a category.
 */
@Composable
fun HeaderGroup(
    title: String,
    onExpandCategory: (Category) -> Unit
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge
        )
        IconButton(
            onClick = {

            }
        ) {
            Icon(
                painter = painterResource(R.drawable.arrow_forward_ios_24px),
                modifier = Modifier.size(30.dp),
                contentDescription = "Expand category"
            )
        }
    }
}