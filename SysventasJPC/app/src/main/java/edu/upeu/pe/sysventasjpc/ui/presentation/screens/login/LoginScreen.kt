package edu.upeu.pe.sysventasjpc.ui.presentation.screens.login

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.k0shk0sh.compose.easyforms.BuildEasyForms
import com.github.k0shk0sh.compose.easyforms.EasyFormsResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import edu.upeu.pe.sysventasjpc.ui.presentation.components.ErrorImageAuth
import edu.upeu.pe.sysventasjpc.ui.presentation.components.ImageLogin
import edu.upeu.pe.sysventasjpc.ui.presentation.components.ProgressBarLoading

import edu.upeu.pe.sysventasjpc.ui.theme.LightRedColors
import edu.upeu.pe.sysventasjpc.utils.ComposeReal
import edu.upeu.pe.sysventasjpc.utils.TokenUtils

import edu.upeu.pe.sysventasjpc.modelo.UsuarioDto
import edu.upeu.pe.sysventasjpc.ui.presentation.components.form.EmailTextField
import edu.upeu.pe.sysventasjpc.ui.presentation.components.form.LoginButton
import edu.upeu.pe.sysventasjpc.ui.presentation.components.form.PasswordTextField
import edu.upeu.pe.sysventasjpc.ui.theme.SysVentasJPCTheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoginScreen(
    navigateToHome: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val isLoading by viewModel.isLoading.observeAsState(false)
    val isLogin by viewModel.islogin.observeAsState(false)
    val isError by viewModel.isError.observeAsState(false)
    val loginResul by viewModel.listUser.observeAsState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        ImageLogin()
        Text("Login Screen", fontSize = 40.sp)
        BuildEasyForms { easyForm ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                EmailTextField(easyForms = easyForm, text ="","E-Mail:", "U")
                PasswordTextField(easyForms = easyForm, text ="", label ="Password:" )
                LoginButton(easyForms=easyForm, onClick = {
                    val dataForm=easyForm.formData()
                    val user=UsuarioDto(
                        (dataForm.get(0) as EasyFormsResult.StringResult).value,
                        (dataForm.get(1) as EasyFormsResult.StringResult).value)
                    viewModel.loginSys(user)
                    scope.launch {
                        delay(3600)
                        if(isLogin && loginResul!=null){
                            Log.i("TOKENV", TokenUtils.TOKEN_CONTENT)
                            Log.i("DATA", loginResul!!.user)
                            navigateToHome.invoke()
                        }else{
                            Log.v("ERRORX", "Error logeo")
                            Toast.makeText(context,"Error al conectar",Toast.LENGTH_LONG).show()
                        }
                    }
                },

                label = "Log In"
                )
                /*Button(onClick = {
                    navigateToHome.invoke()
                }) {
                    Text("Ir a Detalle")
                }*/
                ComposeReal.COMPOSE_TOP.invoke()
            }
        }
    }
    ErrorImageAuth(isImageValidate = isError)
    ProgressBarLoading(isLoading = isLoading)
}


@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    val colors = LightRedColors
    val darkTheme = isSystemInDarkTheme()
    SysVentasJPCTheme(colorScheme = colors) {
        LoginScreen(
            navigateToHome = {}
        )
    }
}