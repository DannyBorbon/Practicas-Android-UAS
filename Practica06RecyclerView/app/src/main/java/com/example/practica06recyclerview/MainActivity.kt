package com.example.practica06recyclerview

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var rvTasks: RecyclerView
    private lateinit var tvSummary: TextView

    private val tasks = listOf(
        TaskItem(
            1,
            "Estudiar RecyclerView",
            "Comprender Adapter, ViewHolder y LayoutManager.",
            "Estudio",
            "Alta",
            false
        ),
        TaskItem(
            2,
            "Entregar reporte",
            "Preparar el documento de evidencias de la práctica.",
            "Escuela",
            "Alta",
            false
        ),
        TaskItem(
            3,
            "Repasar Kotlin",
            "Practicar clases, propiedades y funciones.",
            "Programación",
            "Media",
            true
        ),
        TaskItem(
            4,
            "Organizar proyecto",
            "Revisar los archivos y mantener el código ordenado.",
            "Desarrollo",
            "Media",
            false
        ),
        TaskItem(
            5,
            "Probar la aplicación",
            "Verificar los clics, el desplazamiento y la interfaz.",
            "Pruebas",
            "Alta",
            false
        ),
        TaskItem(
            6,
            "Subir a GitHub",
            "Guardar los cambios y publicar el código fuente.",
            "Desarrollo",
            "Baja",
            false
        ),
        TaskItem(
            7,
            "Repasar HTTP",
            "Estudiar métodos, headers y respuestas de una API.",
            "Backend",
            "Media",
            true
        ),
        TaskItem(
            8,
            "Preparar evidencias",
            "Tomar capturas de la aplicación funcionando.",
            "Escuela",
            "Baja",
            false
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvTasks = findViewById(R.id.rvTasks)
        tvSummary = findViewById(R.id.tvSummary)

        setupRecyclerView()
        updateSummary()
    }

    private fun setupRecyclerView() {
        rvTasks.layoutManager = LinearLayoutManager(this)

        rvTasks.setHasFixedSize(true)

        rvTasks.adapter = TaskAdapter(tasks) { task ->
            showTaskDetails(task)
        }
    }

    private fun updateSummary() {
        val completed = tasks.count { it.completed }
        val total = tasks.size

        tvSummary.text =
            "$total tareas · $completed completadas · ${total - completed} pendientes"
    }

    private fun showTaskDetails(task: TaskItem) {
        val status = if (task.completed) {
            "Completada"
        } else {
            "Pendiente"
        }

        AlertDialog.Builder(this)
            .setTitle(task.title)
            .setMessage(
                "Descripción: ${task.description}\n\n" +
                        "Categoría: ${task.category}\n" +
                        "Prioridad: ${task.priority}\n" +
                        "Estado: $status\n" +
                        "ID: ${task.id}"
            )
            .setPositiveButton("Cerrar", null)
            .show()
    }
}
