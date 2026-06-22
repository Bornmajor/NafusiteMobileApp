package com.majasociet.nafusitemobileapp.features.auth.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.majasociet.nafusitemobileapp.features.auth.ui.components.AuthOnboardingScaffold
import com.majasociet.nafusitemobileapp.shared.components.MultiSelectPillGroup
import com.majasociet.nafusitemobileapp.ui.theme.AppTheme

@Composable
fun UserPreferenceSelectionScreen(
    navigatePasswordSetup: () -> Unit
){
    // List of catalog choices to feed into the component
    val productCategories = remember {
        listOf("Necklaces", "Bracelets", "Earrings", "Luxury Watches", "Leather Bags", "Rings", "Anklets", "Premium Wallets")
    }
    // Track chosen pills using a State Set
    var selectedCategories by remember { mutableStateOf(setOf<String>()) }

    AuthOnboardingScaffold(
        currentStep = 2,
        bottomAction = navigatePasswordSetup,
        canProceed = selectedCategories.isNotEmpty(),
        content = {
            Column(
                modifier = Modifier.padding(
                    top = AppTheme.spacing.large,
                    bottom = AppTheme.spacing.large
                )
            ){
                Text(
                    "What kind of products are you interested in?",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier =  Modifier.padding(AppTheme.spacing.medium))

                MultiSelectPillGroup(
                    items = productCategories,
                    selectedItems = selectedCategories,
                    onSelectionChanged = { newSelection ->
                        selectedCategories = newSelection
                    }
                )
            }

        }
    )

}