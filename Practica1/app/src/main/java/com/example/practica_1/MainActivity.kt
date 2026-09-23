package com.example.practica_1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val btnSaludar = findViewById<Button>(R.id.btnSaludar)
        val tvSaludo = findViewById<TextView>(R.id.tvSaludo)

        btnSaludar.setOnClickListener {

            val nombre = etNombre.text.toString().trim()

            if (nombre.isNotEmpty()) {
                tvSaludo.text = "¡Hola, $nombre!"
            } else {
                tvSaludo.text = "Por favor, escribe tu nombre."
            }
        }
    }
}