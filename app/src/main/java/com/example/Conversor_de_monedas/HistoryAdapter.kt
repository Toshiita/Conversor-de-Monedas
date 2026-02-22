package com.example.Conversor_de_monedas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// conversion guardada en bd
data class Conversion(
    val id: Int,
    val from: String,
    val to: String,
    val amount: Double,
    val result: Double,
    val date: String,
    var favorite: Int
)

//lista con conversiones y conexion con bd y agrega a favoritos
class HistoryAdapter(
    private val list: MutableList<Conversion>,
    private val db: DatabaseHelper
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvConversion: TextView = view.findViewById(R.id.tvConversion)
        val tvDate: TextView = view.findViewById(R.id.tvDate)
        val tvFavorite: TextView = view.findViewById(R.id.tvFavorite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            //carga historial al xml
            .inflate(R.layout.item_conversion, parent, false)
        return ViewHolder(view)
    }

    //indica cantidad de elementos en lista
    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = list[position]

        // Texto conversión
        holder.tvConversion.text =
            "${item.amount} ${item.from} → ${item.result} ${item.to}"
        //fecha que se hizo la conversion
        holder.tvDate.text = item.date

        // Mostrar estrella
        holder.tvFavorite.text =
            if (item.favorite == 1) "★" else "☆"

        // Click estrella
        holder.tvFavorite.setOnClickListener {

            // Cambiar valor local
            item.favorite = if (item.favorite == 1) 0 else 1

            // Actualizar en BD
            db.toggleFavorite(item.id)

            // Refrescar solo ese item
            notifyItemChanged(position)
        }
    }
}
