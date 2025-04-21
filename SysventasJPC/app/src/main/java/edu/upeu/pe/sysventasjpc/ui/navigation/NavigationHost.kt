package edu.upeu.pe.sysventasjpc.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import edu.upeu.pe.sysventasjpc.ui.presentation.screens.Pantalla1
import edu.upeu.pe.sysventasjpc.ui.presentation.screens.Pantalla2
import edu.upeu.pe.sysventasjpc.ui.presentation.screens.Pantalla3
import edu.upeu.pe.sysventasjpc.ui.presentation.screens.Pantalla4
import edu.upeu.pe.sysventasjpc.ui.presentation.screens.Pantalla5
import edu.upeu.pe.sysventasjpc.ui.presentation.screens.login.LoginScreen
import edu.upeu.pe.sysventasjpc.ui.presentation.screens.producto.ProductoForm
import edu.upeu.pe.sysventasjpc.ui.presentation.screens.producto.ProductoMain

@Composable
fun NavigationHost(
    navController: NavHostController,
    darkMode: MutableState<Boolean>,
    modif:PaddingValues
) {
    NavHost(
        navController = navController, startDestination =
        Destinations.Login.route
    ) {
        composable(Destinations.Login.route){
            LoginScreen(navigateToHome = { navController.navigate(Destinations.Pantalla1.route)})
        }
        composable(Destinations.Pantalla1.route) {
            Pantalla1(navegarPantalla2 = { newText ->navController.navigate(Destinations.Pantalla2.createRoute(newText)) }
            )
        }

        composable( Destinations.Pantalla2.route, arguments = listOf(navArgument("newText") { defaultValue = "Pantalla 2"
        })
        ) { navBackStackEntry ->
            var newText =navBackStackEntry.arguments?.getString("newText")
            requireNotNull(newText)
        Pantalla2(newText, darkMode)
        }

        composable(Destinations.Pantalla3.route) {
            Pantalla3() }
        composable(Destinations.Pantalla4.route) {
            Pantalla4() }
        composable(Destinations.Pantalla5.route) {
            Pantalla5() }
        composable(Destinations.ProductoMainSC.route){
            ProductoMain(navegarEditarAct = {newText->

                navController.navigate(Destinations.ProductoFormSC.passId(newText))},
                navController =navController )
        }
        composable(Destinations.ProductoFormSC.route, arguments =
            listOf(navArgument("prodId"){
                defaultValue="prodId"
            })){navBackStackEntry -> var
                prodId=navBackStackEntry.arguments?.getString("prodId")
            requireNotNull(prodId)
            ProductoForm(text = prodId, darkMode = darkMode,
                navController=navController )
        }


    }

}