package com.majasociet.nafusitemobileapp.features.auth.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.majasociet.nafusitemobileapp.R
import com.ocnyang.compose_loading.DoubleBounce

/**
 * Splash screen for the app act as Loader before showing registration journey.
 */
@Composable
fun AppSplashScreen(){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Image(
            painter = painterResource(R.drawable.square_logo),
            contentDescription = "App Logo"
        )

        DoubleBounce(
            color = MaterialTheme.colorScheme.primary
        )
    }
}