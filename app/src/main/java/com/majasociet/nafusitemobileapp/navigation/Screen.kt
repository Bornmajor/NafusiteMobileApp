
// File path: com/majasociet/nafusitemobileapp/navigation/Screen.kt
package com.majasociet.nafusitemobileapp.navigation

import androidx.annotation.DrawableRes
import com.majasociet.nafusitemobileapp.R
import com.majasociet.nafusitemobileapp.features.auth.data.models.BasicRegistrationInfo
import kotlinx.serialization.Serializable

// Sub-graphs are now represented as simple serializable data structures
@Serializable
object MainBottomTabGraph
@Serializable
object RegistrationGraph
//@Serializable
//object HomeGraph
@Serializable
object ProfileGraph
@Serializable
object ProductsGraph
@Serializable
sealed class Screen(
    val label:String? = null,
    @DrawableRes val iconRes:Int,
) {
    @Serializable
    object AppSplashScreen
    @Serializable
    object WelcomeRegistrationScreen
    @Serializable
    object BasicInfoRegistrationScreen
    @Serializable
    data class UserPreferenceSelectionScreen(
        val registrationInfo: BasicRegistrationInfo
    )

    @Serializable
    data class PasswordSetupScreen(
        val registrationInfo: BasicRegistrationInfo,
        val selectedPreferences: List<String>
    )

    @Serializable
    object LoginScreen
    @Serializable
    object HomeScreen: Screen(
        label = "Home",
        iconRes = R.drawable.baseline_home_24
    )


    //Profile screens
    @Serializable
    object ProfileMainScreen
    @Serializable
    object UpdateProfileScreen
    @Serializable
    object WishlistScreen: Screen(
        label = "Wishlist",
        iconRes = R.drawable.baseline_favorite_24
    )
    @Serializable
    object CartProductsScreen: Screen(
        label = "Cart",
        iconRes = R.drawable.baseline_shopping_cart_24
    )
    @Serializable
    object OrdersScreen: Screen(
        label = "Orders",
        iconRes = R.drawable.baseline_inventory_2_24
    )
   @Serializable
    object SearchScreen

    @Serializable
    object CategoryScreen

    @Serializable
    object ListProductsByCategoryScreen


}
