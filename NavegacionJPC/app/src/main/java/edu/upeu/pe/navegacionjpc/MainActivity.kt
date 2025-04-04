package edu.upeu.pe.navegacionjpc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import edu.upeu.pe.navegacionjpc.ui.presentation.component.MyAppDrawer
import edu.upeu.pe.navegacionjpc.ui.theme.DarkColorScheme
import edu.upeu.pe.navegacionjpc.ui.theme.LightColorScheme
import edu.upeu.pe.navegacionjpc.ui.theme.NavegacionJPCTheme
import edu.upeu.pe.navegacionjpc.ui.theme.ThemeType
import edu.upeu.pe.navegacionjpc.ui.theme.sGreenlightScheme
import edu.upeu.pe.navegacionjpc.ui.theme.sGreendarkScheme
import edu.upeu.pe.navegacionjpc.ui.utils.conttexto
import edu.upeu.pe.navegacionjpc.ui.utils.isNight

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeType= remember{mutableStateOf(ThemeType.RED) }
            val darkThemex= isNight()
            val darkTheme = remember { mutableStateOf(darkThemex)  }

            conttexto.CONTEXTO_APPX=this
            val colorScheme=when(themeType.value){
                ThemeType.RED->{if (darkTheme.value) DarkColorScheme else LightColorScheme}
                ThemeType.GREEN->{if (darkTheme.value) sGreendarkScheme else sGreenlightScheme}
                else->{
                    LightColorScheme}
            }

           NavegacionJPCTheme(colorScheme = colorScheme) {
                 MyAppDrawer(darkMode = darkTheme, themeType = themeType)}

        }
    }
}

/*
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
    NavegacionJPCTheme (colorScheme = sGreenlightScheme {
        Greeting("Android")
    }
}*/