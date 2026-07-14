package com.majasociet.nafusitemobileapp.features.auth.ui

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.majasociet.nafusitemobileapp.BuildConfig
import com.majasociet.nafusitemobileapp.R
import com.majasociet.nafusitemobileapp.features.auth.ui.components.CustomVideoPlayer
import com.majasociet.nafusitemobileapp.shared.components.AppButton
import com.majasociet.nafusitemobileapp.ui.theme.AppTheme

/**
 * Welcome screen first screen registration journey.
 * @param navigateRegistrationOnboarding - navigate to registration onboarding screen
 * @param navigateLogin - navigate to login screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeRegistrationScreen (
    navigateRegistrationOnboarding: () -> Unit,
    navigateLogin: () -> Unit
){
    val context = LocalContext.current
    val rawVideoUri = "android.resource://${context.packageName}/${R.raw.sales_video}".toUri()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { /* Leave empty or add text if needed */ },
                // 2. Set the background container colors completely transparent
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(0.1f),
                    scrolledContainerColor = Color.Transparent
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).background(
                MaterialTheme.colorScheme.primary.copy(
                    alpha = 0.1f
                )
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier =  Modifier.weight(1f).padding(AppTheme.spacing.medium)
            ){
                CustomVideoPlayer(
                    localVideoUri = rawVideoUri,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Column(
                modifier = Modifier.weight(1f).fillMaxSize().padding(AppTheme.spacing.medium),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text("Welcome to Nafusite"
                    ,style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    textAlign = TextAlign.Center
                )

                Text("Explore our premium collection of necklaces, bracelets, earrings, watches, and luxury bags—all uniquely designed to elevate your everyday style."
                    ,style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.medium)
                ) {
                    AppButton(
                        text = "Get Started",
                        onClick = navigateRegistrationOnboarding,
                        modifier = Modifier.width(250.dp)
                    )
                    AppButton(
                        text = "Login",
                        onClick = navigateLogin,
                        modifier = Modifier.width(250.dp),
                        isOutlined = true
                    )
                }

            }







        }
    }

}