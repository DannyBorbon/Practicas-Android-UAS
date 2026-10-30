
package com.example.practica07menustoolbar

import android.os.Bundle
import android.widget.Toast
import android.view.MenuItem
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var btnPopupMenu: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ajustar la interfaz debajo de la barra de estado
        val root = findViewById<android.view.View>(android.R.id.content)

        ViewCompat.setOnApplyWindowInsetsListener(root) { view, insets ->

            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
            )

            view.setPadding(
                0,
                systemBars.top,
                0,
                systemBars.bottom
            )

            insets
        }

        toolbar = findViewById(R.id.toolbar)
        btnPopupMenu = findViewById(R.id.btnPopupMenu)

        configurarToolbar()
        configurarPopupMenu()
    }

    // OPTIONS MENU
    private fun configurarToolbar() {

        toolbar.setNavigationOnClickListener {
            mostrarMensaje("Menú de navegación seleccionado")
        }

        toolbar.setOnMenuItemClickListener { item ->

            when (item.itemId) {

                R.id.action_home -> {
                    mostrarMensaje("Inicio seleccionado")
                    true
                }

                R.id.action_search -> {
                    mostrarMensaje("Buscar seleccionado")
                    true
                }

                R.id.action_settings -> {
                    mostrarMensaje("Configuración seleccionada")
                    true
                }

                R.id.action_about -> {
                    mostrarAcercaDe()
                    true
                }

                else -> false
            }
        }
    }

    // POPUP MENU
    private fun configurarPopupMenu() {

        btnPopupMenu.setOnClickListener {

            val popupMenu = PopupMenu(this, btnPopupMenu)

            popupMenu.menuInflater.inflate(
                R.menu.popup_menu,
                popupMenu.menu
            )

            popupMenu.setOnMenuItemClickListener { item ->

                when (item.itemId) {

                    R.id.popup_edit -> {
                        mostrarMensaje("Editar seleccionado")
                        true
                    }

                    R.id.popup_share -> {
                        mostrarMensaje("Compartir seleccionado")
                        true
                    }

                    R.id.popup_delete -> {
                        confirmarEliminar()
                        true
                    }

                    else -> false
                }
            }

            popupMenu.show()
        }
    }

    // MENSAJES
    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    // ACERCA DE
    private fun mostrarAcercaDe() {

        AlertDialog.Builder(this)
            .setTitle("Acerca de")
            .setMessage(
                "Práctica 07\n\n" +
                        "Menús, Toolbar y Material Design 3\n\n" +
                        "Desarrollado con Kotlin y Android Studio."
            )
            .setPositiveButton("Aceptar", null)
            .show()
    }

    // CONFIRMAR ELIMINACIÓN
    private fun confirmarEliminar() {

        AlertDialog.Builder(this)
            .setTitle("Eliminar elemento")
            .setMessage("¿Estás seguro de que deseas eliminar este elemento?")
            .setNegativeButton("Cancelar", null)
            .setPositiveButton("Eliminar") { _, _ ->
                mostrarMensaje("Elemento eliminado")
            }
            .show()
    }
}
