package com.example.practica08roomcompose.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    // Obtener todas las tareas en tiempo real
    @Query("SELECT * FROM tasks ORDER BY id DESC")
    fun getAllTasks(): Flow<List<Task>>

    // Insertar una nueva tarea
    @Insert
    suspend fun insertTask(task: Task)

    // Actualizar una tarea existente
    @Update
    suspend fun updateTask(task: Task)

    // Eliminar una tarea
    @Delete
    suspend fun deleteTask(task: Task)
}