package com.example.practica_4

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.DatePicker
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var spinnerActividad: Spinner
    private lateinit var radioGroupPrioridad: RadioGroup

    private lateinit var checkInternet: CheckBox
    private lateinit var checkComputadora: CheckBox
    private lateinit var checkMaterial: CheckBox

    private lateinit var switchNotificaciones: Switch
    private lateinit var seekBarProgreso: SeekBar
    private lateinit var datePickerActividad: DatePicker
    private lateinit var timePickerActividad: TimePicker

    private lateinit var textProgreso: TextView
    private lateinit var textResultado: TextView
    private lateinit var btnProcesar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inicializarControles()
        configurarSpinner()
        configurarEventos()

        timePickerActividad.setIs24HourView(true)
    }

    private fun inicializarControles() {
        spinnerActividad = findViewById(R.id.spinnerActividad)
        radioGroupPrioridad = findViewById(R.id.radioGroupPrioridad)

        checkInternet = findViewById(R.id.checkInternet)
        checkComputadora = findViewById(R.id.checkComputadora)
        checkMaterial = findViewById(R.id.checkMaterial)

        switchNotificaciones = findViewById(R.id.switchNotificaciones)

        seekBarProgreso = findViewById(R.id.seekBarProgreso)
        datePickerActividad = findViewById(R.id.datePickerActividad)
        timePickerActividad = findViewById(R.id.timePickerActividad)

        textProgreso = findViewById(R.id.textProgreso)
        textResultado = findViewById(R.id.textResultado)
        btnProcesar = findViewById(R.id.btnProcesar)
    }

    private fun configurarSpinner() {
        val actividades = arrayOf(
            "Estudiar",
            "Programar",
            "Hacer ejercicio",
            "Leer",
            "Realizar una tarea"
        )

        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            actividades
        )

        adaptador.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        spinnerActividad.adapter = adaptador

        spinnerActividad.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val actividad =
                        parent.getItemAtPosition(position).toString()

                    textResultado.text =
                        "Actividad seleccionada: $actividad"
                }

                override fun onNothingSelected(
                    parent: AdapterView<*>
                ) {
                    // No se seleccionó ninguna actividad.
                }
            }
    }

    private fun configurarEventos() {
        radioGroupPrioridad.setOnCheckedChangeListener {
                _, checkedId ->

            val prioridad = when (checkedId) {
                R.id.radioBaja -> "Baja"
                R.id.radioMedia -> "Media"
                R.id.radioAlta -> "Alta"
                else -> "Sin seleccionar"
            }

            textResultado.text =
                "Prioridad seleccionada: $prioridad"
        }

        val listenerCheckBox =
            CompoundButton.OnCheckedChangeListener { _, _ ->

                val recursos = obtenerRecursos()

                textResultado.text =
                    "Recursos seleccionados: " +
                            if (recursos.isEmpty()) {
                                "Ninguno"
                            } else {
                                recursos.joinToString(", ")
                            }
            }

        checkInternet.setOnCheckedChangeListener(listenerCheckBox)
        checkComputadora.setOnCheckedChangeListener(listenerCheckBox)
        checkMaterial.setOnCheckedChangeListener(listenerCheckBox)

        switchNotificaciones.setOnCheckedChangeListener {
                _, activado ->

            textResultado.text =
                if (activado) {
                    "Notificaciones activadas."
                } else {
                    "Notificaciones desactivadas."
                }
        }

        seekBarProgreso.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {

                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    textProgreso.text = "5. Progreso: $progress%"
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    // El usuario comienza a mover el control.
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    // El usuario termina de mover el control.
                }
            }
        )

        btnProcesar.setOnClickListener {
            procesarInformacion()
        }
    }

    private fun obtenerRecursos(): List<String> {
        val recursos = mutableListOf<String>()

        if (checkInternet.isChecked) {
            recursos.add("Internet")
        }

        if (checkComputadora.isChecked) {
            recursos.add("Computadora")
        }

        if (checkMaterial.isChecked) {
            recursos.add("Material de apoyo")
        }

        return recursos
    }

    private fun procesarInformacion() {
        val actividad =
            spinnerActividad.selectedItem?.toString()
                ?: "Sin seleccionar"

        val prioridad = when (
            radioGroupPrioridad.checkedRadioButtonId
        ) {
            R.id.radioBaja -> "Baja"
            R.id.radioMedia -> "Media"
            R.id.radioAlta -> "Alta"
            else -> "Sin seleccionar"
        }

        val recursos = obtenerRecursos()

        val recursosTexto =
            if (recursos.isEmpty()) {
                "Ninguno"
            } else {
                recursos.joinToString(", ")
            }

        val notificaciones =
            if (switchNotificaciones.isChecked) {
                "Activadas"
            } else {
                "Desactivadas"
            }

        val progreso = seekBarProgreso.progress

        val fecha = String.format(
            Locale.getDefault(),
            "%02d/%02d/%04d",
            datePickerActividad.dayOfMonth,
            datePickerActividad.month + 1,
            datePickerActividad.year
        )

        val hora = String.format(
            Locale.getDefault(),
            "%02d:%02d",
            timePickerActividad.hour,
            timePickerActividad.minute
        )

        val resumen = """
            ACTIVIDAD REGISTRADA

            Tipo de actividad: $actividad
            Prioridad: $prioridad
            Recursos: $recursosTexto
            Notificaciones: $notificaciones
            Progreso: $progreso%
            Fecha: $fecha
            Hora: $hora
        """.trimIndent()

        textResultado.text = resumen
    }
}