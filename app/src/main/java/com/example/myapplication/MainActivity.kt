package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.scrollViewPrincipal)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }




        val valorPropiedad: EditText = findViewById(R.id.valorPropiedad)
        val montoPrestamo: EditText = findViewById(R.id.montoPrestamo)
        val plazo: EditText = findViewById(R.id.plazo)
        val tasaInteres: EditText = findViewById(R.id.tasaInteres)
        val btnCalcular: Button = findViewById(R.id.btnCalcular)
        val resultado: TextView = findViewById(R.id.resultado)

        btnCalcular.setOnClickListener {
            try {
                val valorPropiedad = valorPropiedad.text.toString().toDouble()
                val prestamo = montoPrestamo.text.toString().toDouble()
                val plazoMeses = plazo.text.toString().toInt() * 12
                val tasaAnual = tasaInteres.text.toString().toDouble()

                if (valorPropiedad < 70000000) {
                    showToast("El valor de la propiedad debe ser mínimo $70.000.000")
                    return@setOnClickListener
                }
                if (prestamo < 50000000 || prestamo > valorPropiedad * 0.7) {
                    showToast("Ingrese un valor de prestamo permitido.")
                    return@setOnClickListener
                }
                if (plazoMeses / 12 !in 5..20) {
                    showToast("El plazo debe estar entre 5 y 20 años")
                    return@setOnClickListener
                }
                if (tasaAnual !in 12.0..24.9) {
                    showToast("La tasa de interés debe estar entre 12.0% y 24.9%")
                    return@setOnClickListener
                }

                val i = (tasaAnual / 12) / 100
                val cuota = prestamo * (Math.pow(1 + i, plazoMeses.toDouble()) * i) / (Math.pow(1 + i, plazoMeses.toDouble()) - 1)
                val cuotaRedondeada = Math.round(cuota).toInt()

                val cuotaFormateada = cuotaRedondeada.toString().reversed()
                    .chunked(3)
                    .joinToString(".")
                    .reversed()

                resultado.text = "Pagas cuotas de $$cuotaFormateada por mes"

            } catch (e: Exception) {
                showToast("Por favor, ingrese valores válidos")
            }
        }
    }

    private fun showToast(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
    }
