package com.example.practica_5

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etCorreo: EditText
    private lateinit var etContrasena: EditText
    private lateinit var tvEstado: TextView

    private lateinit var preferencias: android.content.SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.statusBarColor = android.graphics.Color.rgb(18, 18, 18)
        window.navigationBarColor = android.graphics.Color.rgb(18, 18, 18)

        etNombre = findViewById(R.id.etNombre)
        etCorreo = findViewById(R.id.etCorreo)
        etContrasena = findViewById(R.id.etContrasena)
        tvEstado = findViewById(R.id.tvEstado)

        val btnGuardar: Button = findViewById(R.id.btnGuardar)
        val btnCargar: Button = findViewById(R.id.btnCargar)
        val btnBorrar: Button = findViewById(R.id.btnBorrar)

        preferencias = getSharedPreferences(
            "preferencias_usuario",
            MODE_PRIVATE
        )

        btnGuardar.setOnClickListener {
            guardarDatos()
        }

        btnCargar.setOnClickListener {
            cargarDatos(mostrarMensaje = true)
        }

        btnBorrar.setOnClickListener {
            borrarDatos()
        }

        // Recupera automáticamente los datos al abrir la aplicación.
        cargarDatos(mostrarMensaje = false)
    }

    private fun guardarDatos() {
        val nombre = etNombre.text.toString().trim()
        val correo = etCorreo.text.toString().trim()
        val contrasena = etContrasena.text.toString()

        if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
            mostrarEstado("Completa todos los campos.")
            Toast.makeText(
                this,
                "Todos los campos son obligatorios",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            mostrarEstado("El correo electrónico no es válido.")
            return
        }

        val guardado = preferencias.edit()
            .putString("nombre", nombre)
            .putString("correo", correo)
            .putString("contrasena", contrasena)
            .commit()

        if (guardado) {
            mostrarEstado("Datos guardados correctamente.")
            Toast.makeText(
                this,
                "Preferencias guardadas",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            mostrarEstado("No se pudieron guardar los datos.")
        }
    }

    private fun cargarDatos(mostrarMensaje: Boolean) {
        val nombre = preferencias.getString("nombre", null)
        val correo = preferencias.getString("correo", null)
        val contrasena = preferencias.getString("contrasena", null)

        if (nombre == null || correo == null || contrasena == null) {
            if (mostrarMensaje) {
                mostrarEstado("No hay datos guardados.")
                Toast.makeText(
                    this,
                    "No existen datos guardados",
                    Toast.LENGTH_SHORT
                ).show()
            }
            return
        }

        etNombre.setText(nombre)
        etCorreo.setText(correo)
        etContrasena.setText(contrasena)

        mostrarEstado("Datos recuperados correctamente.")

        if (mostrarMensaje) {
            Toast.makeText(
                this,
                "Datos cargados correctamente",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun borrarDatos() {
        preferencias.edit().clear().commit()

        etNombre.text.clear()
        etCorreo.text.clear()
        etContrasena.text.clear()

        mostrarEstado("Datos eliminados correctamente.")

        Toast.makeText(
            this,
            "Se borraron los datos guardados",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun mostrarEstado(mensaje: String) {
        tvEstado.text = "Estado: $mensaje"
    }
}
