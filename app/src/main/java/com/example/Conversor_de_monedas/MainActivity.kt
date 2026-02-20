package com.example.Conversor_de_monedas

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = DatabaseHelper(this)

        val etAmount = findViewById<EditText>(R.id.etAmount)
        val spFrom = findViewById<Spinner>(R.id.spFrom)
        val spTo = findViewById<Spinner>(R.id.spTo)
        val btnConvert = findViewById<Button>(R.id.btnConvert)
        val btnHistory = findViewById<Button>(R.id.btnHistory)

        val currencies = arrayOf("HNL", "USD", "CRC", "GTQ", "NIO")

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

        btnConvert.setOnClickListener {

            val amount = etAmount.text.toString().toDoubleOrNull()

            if (amount == null) {
                Toast.makeText(this, "Ingrese un monto válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val from = spFrom.selectedItem.toString().trim().uppercase()
            val to = spTo.selectedItem.toString().trim().uppercase()

            if (from == to) {
                Toast.makeText(this, "Las monedas son iguales", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val rate = db.getRate(from, to)

            if (rate == 0.0) {
                Toast.makeText(this, "No existe tasa para esa conversión", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val result = amount * rate

            val date = SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss",
                Locale.getDefault()
            ).format(Date())

            val database = db.writableDatabase
            val values = ContentValues()
            values.put("from_code", from)
            values.put("to_code", to)
            values.put("amount", amount)
            values.put("result", result)
            values.put("date", date)

            database.insert("conversions", null, values)

            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("amount", amount)
            intent.putExtra("result", result)
            intent.putExtra("rate", rate)
            intent.putExtra("from", from)
            intent.putExtra("to", to)
            startActivity(intent)
        }

        btnHistory.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }
    }
}
