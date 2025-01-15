package com.example.littlelemon2

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
import com.example.littlelemon2.navigation.Home
import com.example.littlelemon2.navigation.Onboarding
import com.example.littlelemon2.navigation.Navigation
import com.example.littlelemon2.ui.theme.LittleLemon2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LittleLemon2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
                    val sharedPreferences = getSharedPreferences(SharedPrefsKeys.SHARED_PREFS_NAME, MODE_PRIVATE)
                    val isRegistered = sharedPreferences.getBoolean(SharedPrefsKeys.IS_REGISTERED, false)

                    val navController = rememberNavController()
                    Navigation(navController, if (isRegistered) Home.route else Onboarding.route)
                }
            }
        }
    }
}

