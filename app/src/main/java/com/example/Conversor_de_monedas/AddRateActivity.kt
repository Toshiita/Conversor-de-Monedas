package com.example.Conversor_de_monedas

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class AddRateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_rate)

        //Guarda todo en S Q L
        val db = DatabaseHelper(this)
        //conexion con interfaz grafica
        val spFrom = findViewById<Spinner>(R.id.spFrom)
        val spTo = findViewById<Spinner>(R.id.spTo)
        val etRate = findViewById<EditText>(R.id.etRate)
        val btnSave = findViewById<Button>(R.id.btnSave)

        val currencies = arrayOf("HNL","USD","CRC","GTQ","NIO")

        spFrom.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            currencies
        )

        spTo.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            currencies
        )

        btnSave.setOnClickListener {

            //Obtiene Moneda de Origen y destino
            val from = spFrom.selectedItem.toString()
            val to = spTo.selectedItem.toString()
            //evita error si el usuario escribe texto invalido
            val rate = etRate.text.toString().toDoubleOrNull()

            if (rate == null) {
                Toast.makeText(this,"Ingrese tasa válida",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //agrega las tasas ya personalizadas
            db.insertRate(from,to,rate)

            Toast.makeText(this,"Tasa guardada correctamente",Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}