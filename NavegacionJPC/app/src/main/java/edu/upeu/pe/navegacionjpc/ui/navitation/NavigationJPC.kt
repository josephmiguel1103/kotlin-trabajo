package edu.upeu.pe.navegacionjpc.ui.navitation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.upeu.pe.navegacionjpc.ui.presentation.screens.HomeScreen
import edu.upeu.pe.navegacionjpc.ui.presentation.screens.ProfileScreen
import edu.upeu.pe.navegacionjpc.ui.presentation.screens.SettingsScreen

@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(navController, startDestination = "home") {
        composable("home") { HomeScreen() }
        composable("profile") { ProfileScreen() }
        composable("settings") { SettingsScreen() }
    }
}