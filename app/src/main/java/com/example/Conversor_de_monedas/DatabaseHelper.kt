package com.example.Conversor_de_monedas

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "currency.db", null, 2) {

    override fun onCreate(db: SQLiteDatabase) {

        // TABLA DE TASAS
        db.execSQL("""
            CREATE TABLE rates(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                from_code TEXT,
                to_code TEXT,
                rate REAL
            )
        """)

        // TABLA DE CONVERSIONES
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

        // ================= TASAS =================

        db.execSQL("INSERT INTO rates VALUES (NULL,'HNL','USD',0.0406)")
        db.execSQL("INSERT INTO rates VALUES (NULL,'USD','HNL',24.6)")

        db.execSQL("INSERT INTO rates VALUES (NULL,'CRC','USD',0.021)")
        db.execSQL("INSERT INTO rates VALUES (NULL,'USD','CRC',480.78)")

        db.execSQL("INSERT INTO rates VALUES (NULL,'GTQ','USD',0.130)")
        db.execSQL("INSERT INTO rates VALUES (NULL,'USD','GTQ',7.68)")

        db.execSQL("INSERT INTO rates VALUES (NULL,'NIO','USD',0.027)")
        db.execSQL("INSERT INTO rates VALUES (NULL,'USD','NIO',36.70)")

        db.execSQL("INSERT INTO rates VALUES (NULL,'HNL','NIO',1.38)")
        db.execSQL("INSERT INTO rates VALUES (NULL,'NIO','HNL',0.72)")

        db.execSQL("INSERT INTO rates VALUES (NULL,'HNL','CRC',18.14)")
        db.execSQL("INSERT INTO rates VALUES (NULL,'CRC','HNL',0.055)")

        db.execSQL("INSERT INTO rates VALUES (NULL,'HNL','GTQ',0.29)")
        db.execSQL("INSERT INTO rates VALUES (NULL,'GTQ','HNL',3.45)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS rates")
        db.execSQL("DROP TABLE IF EXISTS conversions")
        onCreate(db)
    }

    // ================= BUSCA TASA =================

    fun getRate(from: String, to: String): Double {

        val db = readableDatabase

        //  Buscar tasa directa
        var cursor = db.rawQuery(
            "SELECT rate FROM rates WHERE UPPER(from_code)=? AND UPPER(to_code)=?",
            arrayOf(from.uppercase(), to.uppercase())
        )

        if (cursor.moveToFirst()) {
            val rate = cursor.getDouble(0)
            cursor.close()
            return rate
        }

        cursor.close()

        //  Buscar tasa inversa automáticamente por si no encuentra alguna conversion
        cursor = db.rawQuery(
            "SELECT rate FROM rates WHERE UPPER(from_code)=? AND UPPER(to_code)=?",
            arrayOf(to.uppercase(), from.uppercase())
        )

        if (cursor.moveToFirst()) {
            val inverseRate = cursor.getDouble(0)
            cursor.close()
            return 1 / inverseRate
        }

        cursor.close()

        return 0.0
    }

    // ================= INSERTAR NUEVA TASA =================

    fun insertRate(from: String, to: String, rate: Double) {
        val db = writableDatabase
        db.execSQL(
            "INSERT INTO rates (from_code, to_code, rate) VALUES (?,?,?)",
            arrayOf(from.uppercase(), to.uppercase(), rate)
        )
    }

    // ================= FAVORITOS =================

    fun toggleFavorite(id: Int) {
        val db = writableDatabase
        db.execSQL("""
            UPDATE conversions
            SET favorite = CASE favorite WHEN 0 THEN 1 ELSE 0 END
            WHERE id = ?
        """, arrayOf(id))
    }
}