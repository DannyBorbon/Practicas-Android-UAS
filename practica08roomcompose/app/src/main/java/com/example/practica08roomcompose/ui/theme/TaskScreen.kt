package com.example.practica08roomcompose.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.practica08roomcompose.data.Task

private val BackgroundDark = Color(0xFF101014)
private val CardDark = Color(0xFF1B1B23)
private val Purple = Color(0xFFB69CFF)
private val MutedText = Color(0xFF9693A3)
private val Green = Color(0xFF80CBC4)

@Composable
fun TaskScreen(
    viewModel: TaskViewModel
) {

    val tasks by viewModel.tasks.collectAsStateWithLifecycle()

    var showDialog by remember { mutableStateOf(false) }
    var editingTask by remember { mutableStateOf<Task?>(null) }

    val completedTasks = tasks.count { it.isCompleted }
    val pendingTasks = tasks.size - completedTasks

    Scaffold(
        containerColor = BackgroundDark,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    editingTask = null
                    showDialog = true
                },
                containerColor = Purple,
                contentColor = Color(0xFF24134B),
                shape = RoundedCornerShape(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar tarea"
                )
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundDark)
                .padding(innerPadding)
                .padding(horizontal = 22.dp)
        ) {

            Spacer(modifier = Modifier.height(28.dp))

            // Encabezado
            Text(
                text = "MY WORKSPACE",
                color = Purple,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Mis tareas",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Organiza tus actividades y alcanza tus objetivos.",
                color = MutedText,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(25.dp))

            // Panel de estadísticas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                StatCard(
                    title = "TOTAL",
                    value = tasks.size.toString(),
                    modifier = Modifier.weight(1f),
                    valueColor = Color.White
                )

                StatCard(
                    title = "PENDIENTES",
                    value = pendingTasks.toString(),
                    modifier = Modifier.weight(1f),
                    valueColor = Purple
                )

                StatCard(
                    title = "COMPLETADAS",
                    value = completedTasks.toString(),
                    modifier = Modifier.weight(1f),
                    valueColor = Green
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Encabezado de la lista
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Todas las tareas",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = "${tasks.size} registros",
                    color = MutedText,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Lista reactiva
            if (tasks.isEmpty()) {

                EmptyTasks()

            } else {

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 90.dp)
                ) {

                    items(
                        items = tasks,
                        key = { it.id }
                    ) { task ->

                        TaskCard(
                            task = task,

                            onToggle = {
                                viewModel.toggleTask(task)
                            },

                            onEdit = {
                                editingTask = task
                                showDialog = true
                            },

                            onDelete = {
                                viewModel.deleteTask(task)
                            }
                        )
                    }
                }
            }
        }
    }

    // Diálogo para registrar o editar
    if (showDialog) {

        TaskDialog(
            task = editingTask,

            onDismiss = {
                showDialog = false
                editingTask = null
            },

            onSave = { title, description ->

                if (editingTask == null) {
                    viewModel.addTask(title, description)
                } else {
                    viewModel.updateTask(
                        editingTask!!.copy(
                            title = title.trim(),
                            description = description.trim()
                        )
                    )
                }

                showDialog = false
                editingTask = null
            }
        )
    }
}

@Composable
private fun StatCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
    valueColor: Color
) {

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(CardDark)
            .padding(13.dp)
    ) {

        Text(
            text = title,
            color = MutedText,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = value,
            color = valueColor,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun TaskCard(
    task: Task,
    onToggle: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardDark
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                // Botón para completar
                IconButton(
                    onClick = onToggle,
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(
                            if (task.isCompleted) Green
                            else Color(0xFF33313F)
                        )
                ) {

                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Cambiar estado",
                        tint = if (task.isCompleted)
                            Color(0xFF102C29)
                        else Color.White,
                        modifier = Modifier.size(17.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = task.title,
                        color = if (task.isCompleted)
                            MutedText
                        else Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        textDecoration = if (task.isCompleted)
                            TextDecoration.LineThrough
                        else TextDecoration.None
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = if (task.isCompleted)
                            "Completada"
                        else "Pendiente",
                        color = if (task.isCompleted) Green else Purple,
                        fontSize = 11.sp
                    )
                }

                IconButton(onClick = onEdit) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar",
                        tint = MutedText,
                        modifier = Modifier.size(19.dp)
                    )
                }

                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = Color(0xFFFF7780),
                        modifier = Modifier.size(19.dp)
                    )
                }
            }

            if (task.description.isNotBlank()) {

                Spacer(modifier = Modifier.height(12.dp))

                HorizontalDivider(
                    color = Color(0xFF34323D)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = task.description,
                    color = MutedText,
                    fontSize = 13.sp,
                    lineHeight = 19.sp
                )
            }
        }
    }
}

@Composable
private fun EmptyTasks() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 65.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(75.dp)
                .clip(CircleShape)
                .background(CardDark),
            contentAlignment = Alignment.Center
        ) {

            Icon(
                imageVector = Icons.Default.TaskAlt,
                contentDescription = null,
                tint = Purple,
                modifier = Modifier.size(35.dp)
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "Todo comienza aquí",
            color = Color.White,
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(7.dp))

        Text(
            text = "Agrega tu primera tarea con el botón +",
            color = MutedText,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun TaskDialog(
    task: Task?,
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit
) {

    var title by remember(task) {
        mutableStateOf(task?.title ?: "")
    }

    var description by remember(task) {
        mutableStateOf(task?.description ?: "")
    }

    val isEditing = task != null

    AlertDialog(
        onDismissRequest = onDismiss,

        containerColor = CardDark,

        title = {
            Text(
                text = if (isEditing)
                    "Editar tarea"
                else "Nueva tarea",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        },

        text = {

            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Título") },
                    placeholder = { Text("Ej. Estudiar Kotlin") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = darkTextFieldColors()
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Descripción") },
                    placeholder = {
                        Text("Describe tu actividad...")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    maxLines = 4,
                    colors = darkTextFieldColors()
                )
            }
        },

        confirmButton = {

            Button(
                onClick = {
                    onSave(title, description)
                },
                enabled = title.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple,
                    contentColor = Color(0xFF24134B)
                )
            ) {
                Text(
                    text = if (isEditing) "Guardar" else "Agregar"
                )
            }
        },

        dismissButton = {

            TextButton(onClick = onDismiss) {
                Text(
                    text = "Cancelar",
                    color = MutedText
                )
            }
        }
    )
}

@Composable
private fun darkTextFieldColors() =
    OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Purple,
        unfocusedBorderColor = Color(0xFF484552),
        focusedLabelColor = Purple,
        unfocusedLabelColor = MutedText,
        focusedTextColor = Color.White,
        unfocusedTextColor = Color.White,
        cursorColor = Purple,
        focusedPlaceholderColor = MutedText,
        unfocusedPlaceholderColor = MutedText
    )