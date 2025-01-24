package com.example.littlelemon2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon2.screens.Home
import com.example.littlelemon2.screens.Onboarding
import com.example.littlelemon2.screens.Profile
import com.example.littlelemon2.utiltiy.AppDatabase

@Composable
fun Navigation(startDestination: String, database: AppDatabase) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Onboarding.route) {
            Onboarding(navController)
        }
        composable(Home.route) {
            Home(navController, database)
        }
        composable(Profile.route) {
            Profile(navController)
        }
    }
}