package com.majasociet.nafusitemobileapp.features.carts.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.majasociet.nafusitemobileapp.shared.components.BottomTabScreenScaffold

@Composable
fun CartProductsScreen(
    navigateToSearch:()-> Unit,
    navigateToProfile:() -> Unit
) {
    BottomTabScreenScaffold(
        navigateToProfile = navigateToProfile,
        navigateToSearch = navigateToSearch,
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Text(text = "Cart Screen")

            }
        }
    )
}