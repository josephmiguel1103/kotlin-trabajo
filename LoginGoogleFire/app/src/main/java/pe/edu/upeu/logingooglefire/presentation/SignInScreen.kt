package pe.edu.upeu.logingooglefire.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import pe.edu.upeu.logingooglefire.R

@Composable
fun SignInScreen(
    navController: NavHostController,
    viewModel: SignInViewModel = viewModel()
) {
    val context = LocalContext.current
    val isAuthenticated by viewModel.isAuthenticated

    val googleSignInLauncher =rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                viewModel.handleSignInResult(task, context)
            } else {
                Toast.makeText(context, "Failed to sign-in", Toast.LENGTH_SHORT).show()
            }
        }

    // Navegación automática si ya está autenticado
    LaunchedEffect(isAuthenticated) {
        if (isAuthenticated) {
            navController.navigate("home") {
                popUpTo("signin") { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            signInWithGoogle(googleSignInLauncher, context)
        }) {
            Text("Sign in with Google")
        }
    }
}

fun signInWithGoogle(
    googleSignInLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>,
    context: Context
) {
    val webClientID = context.getString(R.string.id_web_client_fire)
    val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(webClientID)
        .requestEmail()
        .build()

    val client = GoogleSignIn.getClient(context, options)
    client.signOut()
    googleSignInLauncher.launch(client.signInIntent)
}
