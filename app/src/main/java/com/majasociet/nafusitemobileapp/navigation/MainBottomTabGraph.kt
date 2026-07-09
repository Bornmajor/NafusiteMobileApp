package com.majasociet.nafusitemobileapp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.majasociet.nafusitemobileapp.features.auth.ui.viewmodel.AuthViewModel
import com.majasociet.nafusitemobileapp.features.carts.ui.CartProductsScreen
import com.majasociet.nafusitemobileapp.features.home.ui.HomeScreen
import com.majasociet.nafusitemobileapp.features.orders.ui.OrdersScreen
import com.majasociet.nafusitemobileapp.features.products.ui.viewmodel.ProductsViewModel
import com.majasociet.nafusitemobileapp.features.wishlist.ui.WishListScreen


fun NavGraphBuilder.mainBottomTabGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    productsViewModel: ProductsViewModel
) {
    navigation<MainBottomTabGraph>(
        startDestination = Screen.HomeScreen
    ) {
        composable<Screen.HomeScreen> {
            MainBottomTabScreen(
                navController = navController,
                authViewModel = authViewModel,
                productsViewModel = productsViewModel,
                selectedIndex = 0
            )
        }
        composable<Screen.WishlistScreen> {
            MainBottomTabScreen(
                navController = navController,
                authViewModel = authViewModel,
                productsViewModel = productsViewModel,
                selectedIndex = 1
            )
        }
        composable<Screen.CartProductsScreen> {
            MainBottomTabScreen(
                navController = navController,
                authViewModel = authViewModel,
                productsViewModel = productsViewModel,
                selectedIndex = 2
            )
        }
        composable<Screen.OrdersScreen> {
            MainBottomTabScreen(
                navController = navController,
                authViewModel = authViewModel,
                productsViewModel = productsViewModel,
                selectedIndex = 3
            )
        }
    }
}

// This is the actual composable screen with the scaffold
@Composable
fun MainBottomTabScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    productsViewModel: ProductsViewModel,
    selectedIndex: Int
) {
    val navItems = listOf(
        Screen.HomeScreen,
        Screen.WishlistScreen,
        Screen.CartProductsScreen,
        Screen.OrdersScreen
    )

    NavigationSuiteScaffold(
        modifier = Modifier.safeDrawingPadding(),
        containerColor = MaterialTheme.colorScheme.primary.copy(
            alpha = 0.05f
        ),
        navigationSuiteItems = {
            navItems.forEachIndexed { index, screen ->
                val isSelected = selectedIndex == index
                item(
                    selected = isSelected,
                    onClick = {
                        if (selectedIndex != index) {
                            navController.navigate(screen) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = screen.iconRes),
                            contentDescription = screen.label,
                            modifier = Modifier.padding(bottom = 8.dp),
                            tint = if (isSelected) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurface
                        )
                    },
                    label = {
                        screen.label?.let {
                            Text(it,
                                style = TextStyle(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                )
                            )
                        }
                    }

                )
            }
        }
    ) {
        // Content based on selected tab
        when (selectedIndex) {
            0 -> HomeScreen(
                authViewModel = authViewModel,
                productsViewModel = productsViewModel,
                navigateToSearch = {
                    navController.navigate(Screen.SearchScreen)
                },
                navigateToProfile = {
                    navController.navigate(Screen.ProfileMainScreen)
                },
                navigateToCategory = {
                    navController.navigate(Screen.SearchScreen)
                }
            )
            1 -> {
                WishListScreen(
                    navigateToSearch = {
                        navController.navigate(Screen.SearchScreen)
                    },
                    navigateToProfile = {
                        navController.navigate(Screen.ProfileMainScreen)
                    }
                )
            }
            2 -> {
                CartProductsScreen(
                    navigateToSearch = {
                        navController.navigate(Screen.SearchScreen)
                    },
                    navigateToProfile = {
                        navController.navigate(Screen.ProfileMainScreen)
                    }
                )
            }
            3 -> {
                OrdersScreen(
                    navigateToSearch = {
                        navController.navigate(Screen.SearchScreen)
                    },
                    navigateToProfile = {
                        navController.navigate(Screen.ProfileMainScreen)
                    }
                )
            }
        }
    }
}