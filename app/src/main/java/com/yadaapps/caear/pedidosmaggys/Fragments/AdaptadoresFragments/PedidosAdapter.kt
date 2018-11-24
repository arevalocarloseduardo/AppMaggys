package com.yadaapps.caear.pedidosmaggys.Fragments.AdaptadoresFragments

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.FirebaseDatabase
import com.yadaapps.caear.pedidosmaggys.BaseDeDatos
import com.yadaapps.caear.pedidosmaggys.R
import kotlinx.android.synthetic.main.datos_pedidos.view.*

class PedidosAdapter(var list: MutableList<BaseDeDatos>): RecyclerView.Adapter<PedidosAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.datos_pedidos,parent,false)
        return ViewHolder(v)
    }
    override fun getItemCount(): Int {
        return list.size
    }
    override fun onBindViewHolder(holder: PedidosAdapter.ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindItems(data: BaseDeDatos){
            val menu = itemView.tvMenu1
            val cant= itemView.tvCant1
            val llevar= itemView.tvLlevar1
            val btnDelete = itemView.btnEliminar1

            menu.text = data.menu
            cant.text = data.cant
            llevar.text = data.llevar
            btnDelete.setOnClickListener {
                deleteInfo(data)
            }
        }
        private fun deleteInfo(data: BaseDeDatos) {
            val myBaseDeDatos = FirebaseDatabase.getInstance().getReference("Confirmados")
            myBaseDeDatos.child(data.id).removeValue()
        }
    }
}