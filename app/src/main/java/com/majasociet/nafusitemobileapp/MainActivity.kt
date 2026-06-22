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
import com.majasociet.nafusitemobileapp.features.auth.ui.AppSplashScreen
import com.majasociet.nafusitemobileapp.navigation.AppNavHost
import com.majasociet.nafusitemobileapp.ui.theme.NafusiteMobileAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NafusiteMobileAppTheme {
                val navController = rememberNavController()
                AppNavHost(navController)
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