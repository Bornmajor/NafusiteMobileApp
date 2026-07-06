package com.majasociet.nafusitemobileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.majasociet.nafusitemobileapp.features.auth.data.repository.AuthRepositoryImpl
import com.majasociet.nafusitemobileapp.features.auth.ui.AppSplashScreen
import com.majasociet.nafusitemobileapp.features.auth.ui.viewmodel.AuthViewModel
import com.majasociet.nafusitemobileapp.navigation.AppNavHost
import com.majasociet.nafusitemobileapp.ui.theme.NafusiteMobileAppTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.majasociet.nafusitemobileapp.features.profile.data.remote.network.RetrofitClient
import com.majasociet.nafusitemobileapp.features.profile.data.repository.CloudinaryRepository
import com.majasociet.nafusitemobileapp.features.profile.data.repository.ProfileRepositoryImpl
import com.majasociet.nafusitemobileapp.features.profile.ui.viewmodel.ProfileViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val auth = FirebaseAuth.getInstance()
            val database = FirebaseDatabase.getInstance()



            val authRepository = AuthRepositoryImpl(
                auth = auth,
                database = database
            )

            val authViewModel: AuthViewModel = viewModel(
                factory = viewModelFactory {
                    initializer {
                        AuthViewModel(
                            authRepository = authRepository
                        )
                    }
                }
            )
            val cloudinaryRepository = CloudinaryRepository(
                cloudinaryApiService = RetrofitClient.instance,
                context = applicationContext
            )
            val profileRepository = ProfileRepositoryImpl(
                database = database,
                cloudinaryRepository = cloudinaryRepository
            )

            val profileViewModel: ProfileViewModel = viewModel(
                factory = viewModelFactory {
                    initializer {
                        ProfileViewModel(
                            profileRepository = profileRepository
                        )
                    }
                }
            )

            val navController = rememberNavController()
            NafusiteMobileAppTheme {
                AppNavHost(
                    authViewModel = authViewModel,
                    profileViewModel = profileViewModel,
                    navController = navController
                )
            }
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    NafusiteMobileAppTheme {
//
//    }
//}