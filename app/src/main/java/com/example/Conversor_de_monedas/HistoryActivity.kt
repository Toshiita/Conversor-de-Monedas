package com.example.Conversor_de_monedas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val recycler = findViewById<RecyclerView>(R.id.recyclerHistory)
        recycler.layoutManager = LinearLayoutManager(this)

        val db = DatabaseHelper(this)
        val database = db.readableDatabase

        val cursor = database.rawQuery("SELECT * FROM conversions", null)

        val list = ArrayList<Conversion>()

        while (cursor.moveToNext()) {
            val from = cursor.getString(1)
            val to = cursor.getString(2)
            val amount = cursor.getDouble(3)
            val result = cursor.getDouble(4)
            val date = cursor.getString(5)

            list.add(Conversion(from, to, amount, result, date))
        }

        cursor.close()

        recycler.adapter = HistoryAdapter(list)
    }
}
