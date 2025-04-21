package pe.edu.upeu.logingooglefire.presentation

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController


@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: SignInViewModel = viewModel()
) {
    val user by viewModel.user.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "¡Bienvenido!")
        Text(text = "Tu correo: ${user?.displayName ?: "No disponible"}")
        Text(text = "Tu correo: ${user?.email ?: "No disponible"}")
        Log.i("DMPX", "Correo: ${user?.displayName ?: "null"}")
        Button(
            onClick = {
                viewModel.signOut()
                navController.navigate("signin") {
                    popUpTo("home") { inclusive = true }
                    launchSingleTop = true
                }
            }
        ) {Text(text = "Salir") }
    }
}
