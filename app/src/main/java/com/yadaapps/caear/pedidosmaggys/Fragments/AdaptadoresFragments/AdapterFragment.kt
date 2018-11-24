package com.yadaapps.caear.pedidosmaggys.Fragments.AdaptadoresFragments


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.firebase.database.FirebaseDatabase

import com.yadaapps.caear.pedidosmaggys.BaseDeDatos
import com.yadaapps.caear.pedidosmaggys.R
import kotlinx.android.synthetic.main.datos.view.*

class AdapterFragment(var list: MutableList<BaseDeDatos>): RecyclerView.Adapter<AdapterFragment.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.datos,parent,false)
        return ViewHolder(v)
    }
    override fun getItemCount(): Int {
        return list.size
    }
    override fun onBindViewHolder(holder: AdapterFragment.ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        fun bindItems(data: BaseDeDatos){

            val menu = itemView.tvMenu
            val cant= itemView.tvCant
            val llevar= itemView.tvLlevar
            val btnDelete = itemView.btnEliminar

            menu.text = data.menu
            cant.text = data.cant
            llevar.text = data.llevar
            btnDelete.setOnClickListener {
                deleteInfo(data)
            }
        }
        private fun deleteInfo(data: BaseDeDatos) {
            val myBaseDeDatos = FirebaseDatabase.getInstance().getReference("Pedidos")
            myBaseDeDatos.child(data.id).removeValue()
        }
    }
}