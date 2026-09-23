package com.example.practica_2

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvDisplay: TextView

    private var primerNumero = 0.0
    private var operador = ""
    private var nuevoNumero = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvDisplay = findViewById(R.id.tvDisplay)

        val botonesNumeros = listOf(
            R.id.btn0,
            R.id.btn1,
            R.id.btn2,
            R.id.btn3,
            R.id.btn4,
            R.id.btn5,
            R.id.btn6,
            R.id.btn7,
            R.id.btn8,
            R.id.btn9
        )

        botonesNumeros.forEach { id ->
            findViewById<Button>(id).setOnClickListener {
                agregarNumero((it as Button).text.toString())
            }
        }

        findViewById<Button>(R.id.btnAdd).setOnClickListener {
            seleccionarOperacion("+")
        }

        findViewById<Button>(R.id.btnSubtract).setOnClickListener {
            seleccionarOperacion("-")
        }

        findViewById<Button>(R.id.btnMultiply).setOnClickListener {
            seleccionarOperacion("*")
        }

        findViewById<Button>(R.id.btnDivide).setOnClickListener {
            seleccionarOperacion("/")
        }

        findViewById<Button>(R.id.btnEquals).setOnClickListener {
            calcularResultado()
        }

        findViewById<Button>(R.id.btnClear).setOnClickListener {
            limpiar()
        }
    }

    private fun agregarNumero(numero: String) {

        if (nuevoNumero || tvDisplay.text.toString() == "0") {
            tvDisplay.text = numero
            nuevoNumero = false
        } else {
            tvDisplay.append(numero)
        }
    }

    private fun seleccionarOperacion(operacion: String) {

        primerNumero = tvDisplay.text.toString().toDouble()
        operador = operacion
        nuevoNumero = true
    }

    private fun calcularResultado() {

        val segundoNumero = tvDisplay.text.toString().toDouble()

        val resultado = when (operador) {

            "+" -> primerNumero + segundoNumero

            "-" -> primerNumero - segundoNumero

            "*" -> primerNumero * segundoNumero

            "/" -> {
                if (segundoNumero == 0.0) {
                    tvDisplay.text = "Error"
                    nuevoNumero = true
                    return
                }

                primerNumero / segundoNumero
            }

            else -> return
        }

        tvDisplay.text = if (resultado % 1.0 == 0.0) {
            resultado.toInt().toString()
        } else {
            resultado.toString()
        }

        nuevoNumero = true
    }

    private fun limpiar() {

        tvDisplay.text = "0"
        primerNumero = 0.0
        operador = ""
        nuevoNumero = true
    }
}