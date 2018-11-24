package com.yadaapps.caear.pedidosmaggys


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.datos.view.*
//Este adaptador sirve para unir elementos del layout y de la lista de objetos, en este caso uniremos
//el layoutResId y datosList

class DatosAdapter(val mctx:Context,val layoutResId:Int,val datosList:List<BaseDeDatos>)
    :ArrayAdapter<BaseDeDatos>(mctx,layoutResId,datosList){

    //Llamamos a la funcion getView para inflar el layout
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val inflarLayout : LayoutInflater = LayoutInflater.from(mctx)
        val view:View = inflarLayout.inflate(layoutResId,null)

        val menu = view.tvMenu
        val cant= view.tvCant
        val llevar= view.tvLlevar
        val btnDelete = view.btnEliminar

        val datos = datosList[position]

        menu.text = datos.menu
        cant.text = datos.cant
        llevar.text = datos.llevar

        btnDelete.setOnClickListener {
            deleteInfo(datos)

        }

        return view
    }

    private fun deleteInfo(datos: BaseDeDatos) {

        val myBaseDeDatos = FirebaseDatabase.getInstance().getReference("Pedidos")
        myBaseDeDatos.child(datos.id).removeValue()

    }
}