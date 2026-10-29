package com.example.practica06recyclerview

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private val tasks: List<TaskItem>,
    private val onTaskClick: (TaskItem) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val tvCategory: TextView =
            itemView.findViewById(R.id.tvCategory)

        val tvPriority: TextView =
            itemView.findViewById(R.id.tvPriority)

        val tvTitle: TextView =
            itemView.findViewById(R.id.tvTitle)

        val tvDescription: TextView =
            itemView.findViewById(R.id.tvDescription)

        val tvStatus: TextView =
            itemView.findViewById(R.id.tvStatus)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)

        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: TaskViewHolder,
        position: Int
    ) {
        val task = tasks[position]

        holder.tvCategory.text = task.category.uppercase()
        holder.tvTitle.text = task.title
        holder.tvDescription.text = task.description
        holder.tvPriority.text = "Prioridad: ${task.priority}"

        if (task.completed) {
            holder.tvStatus.text = "✓ Completada"
            holder.tvStatus.setTextColor(Color.rgb(21, 128, 61))
            holder.tvPriority.setTextColor(Color.rgb(21, 128, 61))
        } else {
            holder.tvStatus.text = "● Pendiente"
            holder.tvStatus.setTextColor(Color.rgb(180, 83, 9))

            holder.tvPriority.setTextColor(
                when (task.priority.lowercase()) {
                    "alta" -> Color.rgb(220, 38, 38)
                    "media" -> Color.rgb(180, 83, 9)
                    else -> Color.rgb(37, 99, 235)
                }
            )
        }

        holder.itemView.setOnClickListener {
            onTaskClick(task)
        }
    }

    override fun getItemCount(): Int = tasks.size
}
