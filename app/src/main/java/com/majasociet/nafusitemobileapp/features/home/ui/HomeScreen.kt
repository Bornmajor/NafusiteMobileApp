package com.majasociet.nafusitemobileapp.features.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.majasociet.nafusitemobileapp.features.auth.ui.viewmodel.AuthViewModel
import com.majasociet.nafusitemobileapp.shared.components.AppButton
import com.majasociet.nafusitemobileapp.ui.theme.AppTheme

@Composable
fun HomeScreen(
    authViewModel: AuthViewModel,
    navigateToProfile: () -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(text = "Home Screen")
        AppButton(
            text = "Go to profile",
            onClick = navigateToProfile
        )
        Spacer(modifier = Modifier.padding(AppTheme.spacing.medium))
        AppButton(
            text = "Logout",
            onClick = {
                authViewModel.logoutUser()
            }
        )
    }

}