package pe.edu.upeu.logingooglefire.presentation
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SignInViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _isAuthenticated = mutableStateOf(false)
    val isAuthenticated: State<Boolean> = _isAuthenticated

    private val _user = MutableStateFlow(FirebaseAuth.getInstance().currentUser)
    val user: StateFlow<FirebaseUser?> = _user

    fun handleSignInResult(task: Task<GoogleSignInAccount>, context: Context) {
        try {
            val account = task.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account.idToken!!, context)
        } catch (e: ApiException) {
            Toast.makeText(context, "Error: ${e.statusCode}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?, context: Context) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val email = auth.currentUser?.email ?: ""
                    _user.value=auth.currentUser
                    _isAuthenticated.value = true
                    Toast.makeText(context, "Signed in as $email", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Failed to sign-in", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun signOut() {
        Log.i("DMPX", "Salio")
        auth.signOut()
        _isAuthenticated.value = false
        _user.value = null
    }
}
