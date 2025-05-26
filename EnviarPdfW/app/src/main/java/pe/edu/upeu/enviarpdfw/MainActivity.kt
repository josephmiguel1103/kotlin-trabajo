package pe.edu.upeu.enviarpdfw

import android.Manifest
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import pe.edu.upeu.enviarpdfw.presentation.PdfPreviewScreen
import pe.edu.upeu.enviarpdfw.presentation.PdfScreen
import pe.edu.upeu.enviarpdfw.presentation.PdfViewModel
import pe.edu.upeu.enviarpdfw.utils.isNetworkAvailable

@OptIn(ExperimentalPermissionsApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel = PdfViewModel()

        setContent {
            val context = LocalContext.current

            val permisos = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                listOf(
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.ACCESS_NETWORK_STATE
                )
            } else {
                listOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_NETWORK_STATE
                )
            }

            val otorgarp = rememberMultiplePermissionsState(permissions = permisos)

            LaunchedEffect(key1 = true) {
                if (otorgarp.allPermissionsGranted) {
                    Toast.makeText(context, "Permisos concedidos", Toast.LENGTH_SHORT).show()
                } else {
                    if (otorgarp.shouldShowRationale) {
                        Toast.makeText(context, "La aplicación requiere permisos para funcionar", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "Solicitando permisos...", Toast.LENGTH_SHORT).show()
                    }
                    otorgarp.launchMultiplePermissionRequest()
                }

                Toast.makeText(
                    context,
                    "Conectado: ${isNetworkAvailable(context)}",
                    Toast.LENGTH_LONG
                ).show()
            }

            val navController = rememberNavController()
            NavHost(navController, startDestination = "form_screen") {
                composable("form_screen") {
                    PdfScreen(navController, viewModel = viewModel)
                }
                composable(
                    route = "preview_pdf/{pdfPath}",
                    arguments = listOf(navArgument("pdfPath") { type = NavType.StringType })
                ) { backStackEntry ->
                    val encodedPath = backStackEntry.arguments?.getString("pdfPath")
                    encodedPath?.let {
                        val uri = Uri.parse(Uri.decode(it))
                        PdfPreviewScreen(pdfUri = uri, navController)
                    }
                }
            }
        }
    }
}
