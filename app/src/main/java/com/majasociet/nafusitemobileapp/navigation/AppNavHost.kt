package com.majasociet.nafusitemobileapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.majasociet.nafusitemobileapp.features.auth.navigation.registrationNavGraph
import com.majasociet.nafusitemobileapp.features.auth.ui.viewmodel.AuthViewModel
import com.majasociet.nafusitemobileapp.features.products.navigation.productsGraph
import com.majasociet.nafusitemobileapp.features.products.ui.viewmodel.ProductsViewModel
import com.majasociet.nafusitemobileapp.features.profile.navigation.profileGraph
import com.majasociet.nafusitemobileapp.features.profile.ui.viewmodel.ProfileViewModel
import com.majasociet.nafusitemobileapp.features.search.ui.SearchScreen


/**
 * This is the main navigation host for the app.
 * @param navController The navigation controller to use passed from the main activity.
 */
@Composable
fun AppNavHost(
    authViewModel: AuthViewModel,
    profileViewModel: ProfileViewModel,
    navController: NavHostController,
    productsViewModel: ProductsViewModel
) {
    val authState = authViewModel.authState.collectAsStateWithLifecycle().value

    NavHost(
        navController = navController,
        startDestination = if(authState.isLogin) MainBottomTabGraph else RegistrationGraph
    ) {

        //Registration navigation graph
        registrationNavGraph(
            navController = navController,
            authViewModel = authViewModel,
            profileViewModel = profileViewModel
        )

        //Profile
        profileGraph(
            profileViewModel = profileViewModel,
            authViewModel = authViewModel,
            navController = navController,
        )

        // Search screen
        composable<Screen.SearchScreen> {
            SearchScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        //Products
        productsGraph(
            navController = navController
        )


        //Bottom tab
        mainBottomTabGraph(
            navController = navController,
            authViewModel = authViewModel,
            productsViewModel = productsViewModel
        )


        //Other navigation graphs

    }
}
