
package com.example.practica10usodecamara

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

private val BackgroundDark = Color(0xFF09090D)
private val CardDark = Color(0xFF17171F)
private val Purple = Color(0xFF9166FF)
private val TextWhite = Color(0xFFF5F5F7)
private val TextGray = Color(0xFF92929E)
private val Green = Color(0xFF4ADE80)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CameraApp()
        }
    }
}

@Composable
fun CameraApp() {

    val context = LocalContext.current

    // Estado inicial del permiso
    var hasPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    // Estado de la fotografía
    var capturedImage by remember {
        mutableStateOf<android.graphics.Bitmap?>(null)
    }

    var statusMessage by remember {
        mutableStateOf("Permiso pendiente")
    }

    // Contrato para abrir la cámara y recibir un Bitmap
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->

        if (bitmap != null) {
            capturedImage = bitmap
            statusMessage = "Fotografía capturada correctamente"
        } else {
            statusMessage = "No se capturó ninguna fotografía"
        }
    }

    // Contrato para solicitar el permiso de cámara
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->

        hasPermission = isGranted

        if (isGranted) {
            statusMessage = "Permiso concedido"
            cameraLauncher.launch(null)
        } else {
            statusMessage = "Permiso de cámara denegado"
        }
    }

    // Interfaz principal
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Purple,
            background = BackgroundDark,
            surface = CardDark,
            onBackground = TextWhite,
            onSurface = TextWhite
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundDark)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            // Encabezado
            Text(
                text = "Practica 10 Uso de Cámara",
                color = Purple,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 3.sp
            )
            Spacer(modifier = Modifier.height(30.dp))

            // Área de fotografía
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CardDark
                )
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {

                    if (capturedImage != null) {

                        Image(
                            bitmap = capturedImage!!.asImageBitmap(),
                            contentDescription = "Fotografía capturada",
                            modifier = Modifier
                                .fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                    } else {

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Box(
                                modifier = Modifier
                                    .size(90.dp)
                                    .background(
                                        Color(0xFF29223C),
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {

                                Text(
                                    text = "📷",
                                    fontSize = 38.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            Text(
                                text = "Aun no hay fotografía",
                                color = TextWhite,
                                fontSize = 19.sp,
                                fontWeight = FontWeight.SemiBold
                            )

                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            // Estado del permiso
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        CardDark,
                        RoundedCornerShape(12.dp)
                    )
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(9.dp)
                        .background(
                            if (hasPermission) Green else Purple,
                            CircleShape
                        )
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {

                    Text(
                        text = if (hasPermission)
                            "Cámara autorizada"
                        else
                            "Permiso de cámara",
                        color = TextWhite,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        text = statusMessage,
                        color = TextGray,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Botón de permisos
            OutlinedButton(
                onClick = {
                    permissionLauncher.launch(
                        Manifest.permission.CAMERA
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                enabled = !hasPermission,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Purple,
                    disabledContentColor = TextGray
                )
            ) {

                Text(
                    text = if (hasPermission)
                        "PERMISO CONCEDIDO"
                    else
                        "SOLICITAR PERMISO",
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Botón para tomar fotografía
            Button(
                onClick = {

                    if (hasPermission) {
                        cameraLauncher.launch(null)
                    } else {
                        statusMessage = "Primero autoriza la cámara"
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple,
                    contentColor = Color.White
                )
            ) {

                Text(
                    text = "TOMAR FOTO",
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Volver a tomar una fotografía
            TextButton(
                onClick = {
                    capturedImage = null
                    statusMessage = "Listo para una nueva captura"
                }
            ) {

                Text(
                    text = "Limpiar fotografía",
                    color = TextGray,
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "PRÁCTICA 10 ",
                color = Color(0xFF555560),
                fontSize = 10.sp,
                letterSpacing = 1.5.sp
            )
        }
    }
}
