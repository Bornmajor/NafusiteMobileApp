package com.majasociet.nafusitemobileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.majasociet.nafusitemobileapp.features.auth.data.repository.AuthRepositoryImpl
import com.majasociet.nafusitemobileapp.features.auth.ui.viewmodel.AuthViewModel
import com.majasociet.nafusitemobileapp.features.products.data.repository.ProductsRepositoryImpl
import com.majasociet.nafusitemobileapp.features.products.ui.viewmodel.ProductsViewModel
import com.majasociet.nafusitemobileapp.features.profile.data.repository.CloudinaryRepository
import com.majasociet.nafusitemobileapp.features.profile.data.repository.ProfileRepositoryImpl
import com.majasociet.nafusitemobileapp.features.profile.ui.viewmodel.ProfileViewModel
import com.majasociet.nafusitemobileapp.navigation.AppNavHost
import com.majasociet.nafusitemobileapp.shared.network.RetrofitClient
import com.majasociet.nafusitemobileapp.ui.theme.NafusiteMobileAppTheme

class MainActivity : ComponentActivity() {

    // Instantiate Core Singletons and Repositories safely OUTSIDE the Compose rendering cycle
    private val auth by lazy { FirebaseAuth.getInstance() }
    private val database by lazy { FirebaseDatabase.getInstance() }

    private val authRepository by lazy {
        AuthRepositoryImpl(auth = auth, database = database)
    }

    private val cloudinaryRepository by lazy {
        CloudinaryRepository(
            cloudinaryApiService = RetrofitClient.instance,
            context = applicationContext
        )
    }

    private val profileRepository by lazy {
        ProfileRepositoryImpl(database = database, cloudinaryRepository = cloudinaryRepository)
    }

    private val productsRepository by lazy {
        ProductsRepositoryImpl(database = database)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            //   ViewModels are safely attached via Factories referencing the stable class repositories
            val authViewModel: AuthViewModel = viewModel(
                factory = viewModelFactory {
                    initializer { AuthViewModel(authRepository = authRepository) }
                }
            )

            val profileViewModel: ProfileViewModel = viewModel(
                factory = viewModelFactory {
                    initializer { ProfileViewModel(profileRepository = profileRepository) }
                }
            )

            val productsViewModel: ProductsViewModel = viewModel(
                factory = viewModelFactory {
                    initializer { ProductsViewModel(productsRepository = productsRepository) }
                }
            )

            val navController = rememberNavController()

            NafusiteMobileAppTheme {
                AppNavHost(
                    authViewModel = authViewModel,
                    profileViewModel = profileViewModel,
                    productsViewModel = productsViewModel,
                    navController = navController
                )
            }
        }
    }
}