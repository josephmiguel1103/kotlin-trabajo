package pe.edu.upeu.navigationjpc.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import pe.edu.upeu.navigationjpc.ui.presentation.screens.BarcodeScanningScreen
import pe.edu.upeu.navigationjpc.ui.presentation.screens.CalcUPeU
import pe.edu.upeu.navigationjpc.ui.presentation.screens.ChipsScreen
import pe.edu.upeu.navigationjpc.ui.presentation.screens.HomeScreen
import pe.edu.upeu.navigationjpc.ui.presentation.screens.ProfileScreen
import pe.edu.upeu.navigationjpc.ui.presentation.screens.SettingsScreen

@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(navController, startDestination = "home") {
        composable("home") { HomeScreen() }
        composable("profile") { ProfileScreen() }
        composable("settings") { SettingsScreen() }
        composable("calc") { CalcUPeU() }
        composable("barcode") { BarcodeScanningScreen(navController) }
        composable("chips_screen") { ChipsScreen()     }

    }
}