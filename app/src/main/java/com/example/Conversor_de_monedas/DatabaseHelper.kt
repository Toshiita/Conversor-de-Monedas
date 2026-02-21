package com.example.Conversor_de_monedas

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "currency.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL("""
            CREATE TABLE rates(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                from_code TEXT,
                to_code TEXT,
                rate REAL
            )
        """)

        db.execSQL("""
            CREATE TABLE conversions(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                from_code TEXT,
                to_code TEXT,
                amount REAL,
                result REAL,
                date TEXT,
                favorite INTEGER DEFAULT 0
            )
        """)

        // TASAS
        db.execSQL("INSERT INTO rates VALUES (NULL,'HNL','USD',0.0406)")
        db.execSQL("INSERT INTO rates VALUES (NULL,'USD','HNL',24.6)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS rates")
        db.execSQL("DROP TABLE IF EXISTS conversions")
        onCreate(db)
    }

    fun getRate(from: String, to: String): Double {

        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT rate FROM rates WHERE from_code=? AND to_code=?",
            arrayOf(from, to)
        )

        var rate = 0.0

        if (cursor.moveToFirst()) {
            rate = cursor.getDouble(0)
        }

        cursor.close()
        return rate
    }
    fun insertRate(from: String, to: String, rate: Double) {
        val db = writableDatabase
        db.execSQL(
            "INSERT INTO rates (from_code, to_code, rate) VALUES (?,?,?)",
            arrayOf(from, to, rate)
        )
    }
    fun toggleFavorite(id: Int) {
        val db = writableDatabase
        db.execSQL("""
            UPDATE conversions
            SET favorite = CASE favorite WHEN 0 THEN 1 ELSE 0 END
            WHERE id = ?
        """, arrayOf(id))
    }
}
