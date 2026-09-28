package com.example.practica_3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etCarrera = findViewById<EditText>(R.id.etCarrera)
        val etSemestre = findViewById<EditText>(R.id.etSemestre)
        val btnContinuar = findViewById<Button>(R.id.btnContinuar)

        btnContinuar.setOnClickListener {

            val nombre = etNombre.text.toString()
            val carrera = etCarrera.text.toString()
            val semestre = etSemestre.text.toString()

            val intent = Intent(this, SegundaActivity::class.java)

            intent.putExtra("nombre", nombre)
            intent.putExtra("carrera", carrera)
            intent.putExtra("semestre", semestre)

            startActivity(intent)
        }
    }
}