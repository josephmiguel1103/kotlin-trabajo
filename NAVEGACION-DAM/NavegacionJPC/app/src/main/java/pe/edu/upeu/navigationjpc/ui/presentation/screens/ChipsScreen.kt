package pe.edu.upeu.navigationjpc.ui.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun ChipsScreen() {
    val chips = listOf("Todos", "Populares", "Nuevos", "Top Ventas", "Ofertas")
    var selected by remember { mutableStateOf("Todos") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("selecionaste : $selected")
        Text("Filtros:")
        Spacer(modifier = Modifier.height(12.dp))

        FlowRow(
            mainAxisSpacing = 8.dp,
            crossAxisSpacing = 8.dp
        ) {
            chips.forEach { chip ->
                FilterChip(
                    selected = selected == chip,
                    onClick = { selected = chip },
                    label = { Text(chip) }
                )
            }
        }
    }
}
