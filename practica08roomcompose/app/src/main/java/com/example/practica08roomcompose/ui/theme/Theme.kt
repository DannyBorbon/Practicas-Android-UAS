package com.example.practica08roomcompose.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFB69CFF),
    onPrimary = Color(0xFF24134B),

    secondary = Color(0xFF80CBC4),
    onSecondary = Color(0xFF003731),

    background = Color(0xFF101014),
    onBackground = Color(0xFFF4F1FA),

    surface = Color(0xFF19191F),
    onSurface = Color(0xFFF4F1FA),

    surfaceVariant = Color(0xFF292832),
    onSurfaceVariant = Color(0xFFCAC5D5),

    error = Color(0xFFFF6B75)
)

@Composable
fun Practica08RoomComposeTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        content = content
    )
}