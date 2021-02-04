package com.example.crudapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crudapp.Database.Antrian
import kotlinx.android.synthetic.main.adapter_antrian.view.*

class AntrianAdapter (private val allAntrian: ArrayList<Antrian>, private val listener: OnAdapterListener) : RecyclerView.Adapter<AntrianAdapter.AntrianViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AntrianViewHolder {
        return AntrianViewHolder(
            LayoutInflater.from(parent.context).inflate( R.layout.adapter_antrian, parent, false)
        )
    }

    override fun getItemCount() = allAntrian.size

    override fun onBindViewHolder(holder: AntrianViewHolder, position: Int) {
        val antrianm = allAntrian[position]
        holder.view.text_nama.text = antrianm.nama
        holder.view.text_nama.setOnClickListener {
            listener.onClick(antrianm)
        }
        holder.view.icon_delete.setOnClickListener {
            listener.onDelete(antrianm)
        }
        holder.view.icon_edit.setOnClickListener {
            listener.onUpdate(antrianm)
        }
    }

    class AntrianViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<Antrian>) {
        allAntrian.clear()
        allAntrian.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(antrian: Antrian)
        fun onDelete(antrian: Antrian)
        fun onUpdate(antrian: Antrian)
    }
}