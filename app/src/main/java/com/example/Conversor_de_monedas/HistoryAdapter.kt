package com.example.Conversor_de_monedas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Conversion(
    val from: String,
    val to: String,
    val amount: Double,
    val result: Double,
    val date: String
)

class HistoryAdapter(private val list: List<Conversion>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvConversion: TextView = view.findViewById(R.id.tvConversion)
        val tvDate: TextView = view.findViewById(R.id.tvDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_conversion, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.tvConversion.text =
            "${item.amount} ${item.from} → ${item.result} ${item.to}"

        holder.tvDate.text = item.date
    }
}
