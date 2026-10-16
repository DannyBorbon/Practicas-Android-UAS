package com.example.miniproyecto01
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import java.net.URLEncoder
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

private val Fondo = Color(0xFF121212)
private val Superficie = Color(0xFF1E1E1E)
private val Azul = Color(0xFF90CAF9)
private val TextoSecundario = Color(0xFFBDBDBD)

private val TemaOscuro = darkColorScheme(
    primary = Azul,
    onPrimary = Color(0xFF10202E),
    secondary = Color(0xFF80CBC4),
    background = Fondo,
    surface = Superficie,
    onBackground = Color.White,
    onSurface = Color.White
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme(colorScheme = TemaOscuro) {
                SistemaRegistro()
            }
        }
    }
}

@Composable
fun SistemaRegistro() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "registro"
    ) {
        composable("registro") {
            PantallaRegistro(
                alRegistrar = { matricula, nombre, carrera, turno, activo ->
                    val matriculaUrl = URLEncoder.encode(
                        matricula, StandardCharsets.UTF_8.toString()
                    )
                    val nombreUrl = URLEncoder.encode(
                        nombre, StandardCharsets.UTF_8.toString()
                    )
                    val carreraUrl = URLEncoder.encode(
                        carrera, StandardCharsets.UTF_8.toString()
                    )

                    navController.navigate(
                        "detalle/$matriculaUrl/$nombreUrl/$carreraUrl/$turno/$activo"
                    )
                }
            )
        }

        composable(
            route = "detalle/{matricula}/{nombre}/{carrera}/{turno}/{activo}",
            arguments = listOf(
                navArgument("matricula") { type = NavType.StringType },
                navArgument("nombre") { type = NavType.StringType },
                navArgument("carrera") { type = NavType.StringType },
                navArgument("turno") { type = NavType.StringType },
                navArgument("activo") { type = NavType.BoolType }
            )
        ) { entrada ->
            PantallaDetalle(
                matricula = URLDecoder.decode(
                    entrada.arguments?.getString("matricula").orEmpty(),
                    StandardCharsets.UTF_8.toString()
                ),
                nombre = URLDecoder.decode(
                    entrada.arguments?.getString("nombre").orEmpty(),
                    StandardCharsets.UTF_8.toString()
                ),
                carrera = URLDecoder.decode(
                    entrada.arguments?.getString("carrera").orEmpty(),
                    StandardCharsets.UTF_8.toString()
                ),
                turno = entrada.arguments?.getString("turno").orEmpty(),
                activo = entrada.arguments?.getBoolean("activo") ?: false,
                alVolver = {
                    navController.popBackStack()
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaRegistro(
    alRegistrar: (
        String, String, String, String, Boolean
    ) -> Unit
) {
    val contexto = LocalContext.current

    val preferencias = remember {
        contexto.getSharedPreferences(
            "preferencias_estudiantes",
            Context.MODE_PRIVATE
        )
    }

    var matricula by remember {
        mutableStateOf(
            preferencias.getString("matricula", "") ?: ""
        )
    }

    var nombre by remember {
        mutableStateOf(
            preferencias.getString("nombre", "") ?: ""
        )
    }

    var carrera by remember {
        mutableStateOf(
            preferencias.getString("carrera", "Ingeniería de Software")
                ?: "Ingeniería de Software"
        )
    }

    var turno by remember {
        mutableStateOf(
            preferencias.getString("turno", "Matutino") ?: "Matutino"
        )
    }

    var activo by remember {
        mutableStateOf(preferencias.getBoolean("activo", true))
    }

    var menuExpandido by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }

    val carreras = listOf(
        "Ingeniería de Software",
        "Ingeniería en Sistemas",
        "Administración",
        "Contaduría",
        "Psicología"
    )

    Scaffold(
        containerColor = Fondo
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Registro de estudiantes",
                style = MaterialTheme.typography.headlineMedium,
                color = Azul,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Ingresa la información del estudiante.",
                color = TextoSecundario
            )

            OutlinedTextField(
                value = matricula,
                onValueChange = { matricula = it },
                label = { Text("Matrícula") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre completo") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Text(
                text = "Carrera",
                fontWeight = FontWeight.Medium
            )

            ExposedDropdownMenuBox(
                expanded = menuExpandido,
                onExpandedChange = {
                    menuExpandido = !menuExpandido
                }
            ) {
                OutlinedTextField(
                    value = carrera,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Selecciona una carrera") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = menuExpandido
                        )
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                DropdownMenu(
                    expanded = menuExpandido,
                    onDismissRequest = {
                        menuExpandido = false
                    }
                ) {
                    carreras.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                carrera = opcion
                                menuExpandido = false
                            }
                        )
                    }
                }
            }

            Text(
                text = "Turno",
                fontWeight = FontWeight.Medium
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = turno == "Matutino",
                    onClick = { turno = "Matutino" }
                )
                Text("Matutino")

                Spacer(modifier = Modifier.padding(horizontal = 8.dp))

                RadioButton(
                    selected = turno == "Vespertino",
                    onClick = { turno = "Vespertino" }
                )
                Text("Vespertino")
            }

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Superficie
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Estudiante activo",
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = if (activo) "Activo" else "Inactivo",
                            color = TextoSecundario
                        )
                    }

                    Switch(
                        checked = activo,
                        onCheckedChange = { activo = it }
                    )
                }
            }

            if (error.isNotBlank()) {
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Button(
                onClick = {
                    if (
                        matricula.isBlank() ||
                        nombre.isBlank()
                    ) {
                        error = "Completa la matrícula y el nombre."
                    } else {
                        error = ""

                        preferencias.edit()
                            .putString("matricula", matricula.trim())
                            .putString("nombre", nombre.trim())
                            .putString("carrera", carrera)
                            .putString("turno", turno)
                            .putBoolean("activo", activo)
                            .apply()

                        alRegistrar(
                            matricula.trim(),
                            nombre.trim(),
                            carrera,
                            turno,
                            activo
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text("Registrar estudiante")
            }

            OutlinedButton(
                onClick = {
                    preferencias.edit().clear().apply()
                    matricula = ""
                    nombre = ""
                    carrera = carreras.first()
                    turno = "Matutino"
                    activo = true
                    error = ""
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Limpiar datos guardados")
            }

            Text(
                text = "Los datos se guardan localmente en el dispositivo.",
                color = TextoSecundario,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun PantallaDetalle(
    matricula: String,
    nombre: String,
    carrera: String,
    turno: String,
    activo: Boolean,
    alVolver: () -> Unit
) {
    Scaffold(
        containerColor = Fondo
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Text(
                text = "Registro confirmado",
                style = MaterialTheme.typography.headlineMedium,
                color = Azul,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Se han registrado los siguientes datos:",
                color = TextoSecundario
            )

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Superficie
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    DatoEstudiante("Matrícula", matricula)
                    DatoEstudiante("Nombre completo", nombre)
                    DatoEstudiante("Carrera", carrera)
                    DatoEstudiante("Turno", turno)
                    DatoEstudiante(
                        "Estatus",
                        if (activo) "Activo" else "Inactivo"
                    )
                }
            }

            Button(
                onClick = alVolver,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text("Volver al formulario")
            }
        }
    }
}

@Composable
fun DatoEstudiante(
    etiqueta: String,
    valor: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = etiqueta,
            color = Azul,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = valor,
            color = Color.White
        )
    }
}
