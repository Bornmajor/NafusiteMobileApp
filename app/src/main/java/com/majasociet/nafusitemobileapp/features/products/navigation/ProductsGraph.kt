package com.majasociet.nafusitemobileapp.features.products.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.majasociet.nafusitemobileapp.features.products.ui.CategoryScreen
import com.majasociet.nafusitemobileapp.features.products.ui.ListProductsByCategoryScreen
import com.majasociet.nafusitemobileapp.navigation.ProductsGraph
import com.majasociet.nafusitemobileapp.navigation.Screen

fun NavGraphBuilder.productsGraph(
    navController: NavHostController
) {

    navigation<ProductsGraph>(
        startDestination = Screen.CategoryScreen
    ){
        composable<Screen.CategoryScreen> {
            CategoryScreen()
        }
        composable<Screen.ListProductsByCategoryScreen> {
            ListProductsByCategoryScreen()
        }
    }
}