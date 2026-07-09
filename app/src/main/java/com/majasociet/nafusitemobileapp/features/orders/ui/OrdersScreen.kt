package com.majasociet.nafusitemobileapp.features.orders.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.majasociet.nafusitemobileapp.shared.components.AppButton
import com.majasociet.nafusitemobileapp.shared.components.BottomTabScreenScaffold
import com.majasociet.nafusitemobileapp.ui.theme.AppTheme

@Composable
fun OrdersScreen(
    navigateToSearch:()-> Unit,
    navigateToProfile:() -> Unit
){
    BottomTabScreenScaffold(
        navigateToProfile = navigateToProfile,
        navigateToSearch = navigateToSearch,
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Text(text = "Orders Screen")

            }
        }
    )
}