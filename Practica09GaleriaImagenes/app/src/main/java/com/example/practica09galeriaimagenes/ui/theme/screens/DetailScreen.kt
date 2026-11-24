package com.example.practica09galeriaimagenes.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import com.example.practica09galeriaimagenes.data.ImageData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    image: ImageData,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(image.title)
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Regresar"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            SubcomposeAsyncImage(
                model = image.imageUrl,
                contentDescription = image.title,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Fit,
                loading = {
                    CircularProgressIndicator(
                        color = Color.White
                    )
                },
                error = {
                    Text(
                        text = "No se pudo cargar la imagen",
                        color = Color.White
                    )
                },
                success = {
                    SubcomposeAsyncImageContent()
                }
            )
        }
    }
}
