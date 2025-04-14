package pe.edu.upeu.navigationjpc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pe.edu.upeu.navigationjpc.ui.presentation.component.MyAppDrawer
import pe.edu.upeu.navigationjpc.ui.theme.DarkColorScheme
import pe.edu.upeu.navigationjpc.ui.theme.LightColorScheme
import pe.edu.upeu.navigationjpc.ui.theme.NavigationJPCTheme
import pe.edu.upeu.navigationjpc.ui.theme.ThemeType
import pe.edu.upeu.navigationjpc.ui.theme.sGreenDarkScheme
import pe.edu.upeu.navigationjpc.ui.theme.sGreenLightScheme
import pe.edu.upeu.navigationjpc.utils.conttexto
import pe.edu.upeu.navigationjpc.utils.isNight

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val themeType = remember { mutableStateOf(ThemeType.RED) }
            val darkthemeX = isNight()
            val darktheme = remember { mutableStateOf(darkthemeX) }
            conttexto.CONTEXTO_APPX=this
            val colorScheme = when(themeType.value){
                ThemeType.RED->{ if(darktheme.value) DarkColorScheme else LightColorScheme}
                ThemeType.GREEN->{ if(darktheme.value) sGreenDarkScheme else sGreenLightScheme}
                else->{LightColorScheme}
            }
            NavigationJPCTheme(colorScheme = colorScheme) {
                MyAppDrawer(darkMode = darktheme, themeType = themeType)
                /*Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android DMP",
                        modifier = Modifier.padding(innerPadding)
                    )
                }*/
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NavigationJPCTheme(colorScheme = sGreenLightScheme) {
        Greeting("Android")
    }
}