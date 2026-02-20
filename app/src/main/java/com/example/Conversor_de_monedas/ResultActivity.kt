package com.example.Conversor_de_monedas

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val amount = intent.getDoubleExtra("amount", 0.0)
        val result = intent.getDoubleExtra("result", 0.0)
        val rate = intent.getDoubleExtra("rate", 0.0)

        val tv = findViewById<TextView>(R.id.tvResult)

        tv.text = """
            Monto: $amount
            Tasa aplicada: $rate
            Resultado: $result
        """.trimIndent()
    }
}
