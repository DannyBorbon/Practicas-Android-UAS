package com.example.practica08roomcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.practica08roomcompose.data.TaskDatabase
import com.example.practica08roomcompose.data.TaskRepository
import com.example.practica08roomcompose.ui.theme.TaskScreen
import com.example.practica08roomcompose.ui.theme.TaskViewModel
import com.example.practica08roomcompose.ui.theme.TaskViewModelFactory
import com.example.practica08roomcompose.ui.theme.Practica08RoomComposeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Crear o recuperar la instancia Singleton de Room
        val database = TaskDatabase.getDatabase(
            applicationContext
        )

        // Crear el Repository
        val repository = TaskRepository(
            database.taskDao()
        )

        // Crear la fábrica del ViewModel
        val factory = TaskViewModelFactory(
            repository
        )

        setContent {

            Practica08RoomComposeTheme {

                val taskViewModel: TaskViewModel = viewModel(
                    factory = factory
                )

                TaskScreen(
                    viewModel = taskViewModel
                )
            }
        }
    }
}