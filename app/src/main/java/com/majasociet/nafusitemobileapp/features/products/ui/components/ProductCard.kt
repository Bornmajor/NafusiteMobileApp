package com.majasociet.nafusitemobileapp.features.products.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.majasociet.nafusitemobileapp.R
import com.majasociet.nafusitemobileapp.features.products.data.models.Product
import com.majasociet.nafusitemobileapp.ui.theme.AppTheme

/**
 * This is a card for a product.
 */
@Composable
fun ProductCard(
    product: Product,
    onSelectProduct: (Product)-> Unit,
    isLiked: Boolean = false,
) {
    Column(
        modifier = Modifier.width(200.dp).clickable {
            onSelectProduct(product)
        },
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.small),
        horizontalAlignment = Alignment.Start
    ) {
        AsyncImage(
            model = product.imageUrl,
            contentDescription = product.name,
            modifier = Modifier.size(200.dp),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            //Text content
            Column(
                modifier = Modifier.weight(2f),
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.small)
            ) {
                Text(
                    text = product.name,
                )
                Text(
                    text = product.price.toString()
                )
            }
            // Wishlist item
            LikeButton(
                modifier = Modifier.weight(1f),
                isLiked = isLiked,
                onLike = {

                }
            )


        }
    }



}

@Composable
fun LikeButton(
    modifier: Modifier = Modifier,
    isLiked: Boolean = false,
    onLike: () -> Unit
){
    var updatedIsLiked by remember { mutableStateOf(isLiked) }
    IconButton(
        onClick = {
            updatedIsLiked = !updatedIsLiked
            onLike()
        }
    ){

        Icon(
            painter = if(updatedIsLiked) painterResource(R.drawable.baseline_favorite_24)
            else
            painterResource(R.drawable.outline_favorite_border_24),
            contentDescription = "Like button",
            modifier = Modifier.size(28.dp),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}