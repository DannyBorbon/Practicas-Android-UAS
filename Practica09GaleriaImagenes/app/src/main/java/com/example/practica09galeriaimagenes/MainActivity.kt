
package com.example.practica09galeriaimagenes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.example.practica09galeriaimagenes.data.ImageData
import com.example.practica09galeriaimagenes.ui.theme.screens.DetailScreen
import com.example.practica09galeriaimagenes.ui.theme.screens.GalleryScreen
import com.example.practica09galeriaimagenes.ui.theme.Practica09GaleriaImagenesTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            Practica09GaleriaImagenesTheme {

                var selectedImage by remember {
                    mutableStateOf<ImageData?>(null)
                }

                if (selectedImage == null) {

                    GalleryScreen(
                        onImageClick = { image ->
                            selectedImage = image
                        }
                    )

                } else {

                    DetailScreen(
                        image = selectedImage!!,
                        onBack = {
                            selectedImage = null
                        }
                    )
                }
            }
        }
    }
}
