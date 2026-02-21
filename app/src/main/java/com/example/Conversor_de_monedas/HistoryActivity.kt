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

        val list = mutableListOf<Conversion>()

        while (cursor.moveToNext()) {

            val id = cursor.getInt(0)
            val from = cursor.getString(1)
            val to = cursor.getString(2)
            val amount = cursor.getDouble(3)
            val result = cursor.getDouble(4)
            val date = cursor.getString(5)
            val favorite = cursor.getInt(6)

            list.add(
                Conversion(id, from, to, amount, result, date, favorite)
            )
        }

        cursor.close()

        val adapter = HistoryAdapter(list, db)
        recycler.adapter = adapter
    }
}
