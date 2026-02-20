package com.example.Conversor_de_monedas

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "currency.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {

        // Crear tabla rates
        db.execSQL("""
            CREATE TABLE rates(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                from_code TEXT,
                to_code TEXT,
                rate REAL
            )
        """)

        // Crear tabla conversions
        db.execSQL("""
            CREATE TABLE conversions(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                from_code TEXT,
                to_code TEXT,
                amount REAL,
                result REAL,
                date TEXT
            )
        """)


        db.execSQL("INSERT INTO rates (from_code, to_code, rate) VALUES ('HNL','USD',0.038)")
        db.execSQL("INSERT INTO rates (from_code, to_code, rate) VALUES ('USD','HNL',26.51)")

        db.execSQL("INSERT INTO rates (from_code, to_code, rate) VALUES ('CRC','USD',0.0021)")
        db.execSQL("INSERT INTO rates (from_code, to_code, rate) VALUES ('USD','CRC',480.78)")

        db.execSQL("INSERT INTO rates (from_code, to_code, rate) VALUES ('GTQ','USD',0.130)")
        db.execSQL("INSERT INTO rates (from_code, to_code, rate) VALUES ('USD','GTQ',7.68)")

        db.execSQL("INSERT INTO rates (from_code, to_code, rate) VALUES ('NIO','USD',0.027)")
        db.execSQL("INSERT INTO rates (from_code, to_code, rate) VALUES ('USD','NIO',36.70)")

        db.execSQL("INSERT INTO rates (from_code, to_code, rate) VALUES ('HNL','NIO',1.38)")
        db.execSQL("INSERT INTO rates (from_code, to_code, rate) VALUES ('NIO','HNL',0.72)")

        db.execSQL("INSERT INTO rates (from_code, to_code, rate) VALUES ('HNL','CRC',18.14)")
        db.execSQL("INSERT INTO rates (from_code, to_code, rate) VALUES ('CRC','HNL',0.055)")

        db.execSQL("INSERT INTO rates (from_code, to_code, rate) VALUES ('HNL','GTQ',0.29)")
        db.execSQL("INSERT INTO rates (from_code, to_code, rate) VALUES ('GTQ','HNL',3.45)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS rates")
        db.execSQL("DROP TABLE IF EXISTS conversions")
        onCreate(db)
    }

    // FUNCIÓN PARA OBTENER TASA
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
}
