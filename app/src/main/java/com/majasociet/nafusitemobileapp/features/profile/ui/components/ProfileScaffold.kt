package com.majasociet.nafusitemobileapp.features.profile.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.majasociet.nafusitemobileapp.shared.components.AppButton
import com.majasociet.nafusitemobileapp.shared.components.SegmentedStepper
import com.majasociet.nafusitemobileapp.ui.theme.AppTheme

@Composable
fun ProfileScaffold(
    modifier: Modifier = Modifier,
    bottomAction:(@Composable () -> Unit)?=null,
    content: @Composable () -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            if(bottomAction != null){
                Box(
                    modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars).padding(AppTheme.spacing.medium)
                ){
                    bottomAction()
                }
            }
        }
    ){ innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding)
                .padding(AppTheme.spacing.medium)
        ) {
            content()
        }
    }
}