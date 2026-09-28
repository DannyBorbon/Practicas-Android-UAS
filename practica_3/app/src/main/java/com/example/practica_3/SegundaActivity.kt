package com.example.practica_3

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SegundaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_segunda)

        val tvNombre = findViewById<TextView>(R.id.tvNombre)
        val tvCarrera = findViewById<TextView>(R.id.tvCarrera)
        val tvSemestre = findViewById<TextView>(R.id.tvSemestre)
        val btnRegresar = findViewById<Button>(R.id.btnRegresar)

        val nombre = intent.getStringExtra("nombre")
        val carrera = intent.getStringExtra("carrera")
        val semestre = intent.getStringExtra("semestre")

        tvNombre.text = "Nombre: $nombre"
        tvCarrera.text = "Carrera: $carrera"
        tvSemestre.text = "Semestre: $semestre"

        btnRegresar.setOnClickListener {
            finish()
        }
    }
}