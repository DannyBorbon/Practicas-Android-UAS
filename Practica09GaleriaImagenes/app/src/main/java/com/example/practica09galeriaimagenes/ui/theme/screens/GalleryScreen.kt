package com.example.practica09galeriaimagenes.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.practica09galeriaimagenes.data.ImageData
import com.example.practica09galeriaimagenes.ui.theme.components.GalleryItem

private val images = listOf(
    ImageData(1, "Montañas", "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?w=600"),
    ImageData(2, "Naturaleza", "https://images.unsplash.com/photo-1441974231531-c6227db76b6e?w=600"),
    ImageData(3, "Playa", "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?w=600"),
    ImageData(4, "Bosque", "https://images.unsplash.com/photo-1448375240586-882707db888b?w=600"),
    ImageData(5, "Desierto", "https://images.unsplash.com/photo-1509316785289-025f5b846b35?w=600"),
    ImageData(6, "Lago", "https://images.unsplash.com/photo-1470770841072-f978cf4d019e?w=600"),
    ImageData(7, "Cascada", "https://images.unsplash.com/photo-1432405972618-c60b0225b8f9?w=600"),
    ImageData(8, "Atardecer", "https://images.unsplash.com/photo-1472120435266-53107fd0c44a?w=600")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryScreen(
    onImageClick: (ImageData) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Mi Galería")
                }
            )
        }
    ) { innerPadding ->

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = images,
                key = { image -> image.id }
            ) { image ->

                GalleryItem(
                    image = image,
                    onClick = {
                        onImageClick(image)
                    }
                )
            }
        }
    }
}
