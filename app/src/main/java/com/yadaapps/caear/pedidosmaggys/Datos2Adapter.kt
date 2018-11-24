package com.yadaapps.caear.pedidosmaggys


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.datos2.view.*

//Este adaptador sirve para unir elementos del layout y de la lista de objetos, en este caso uniremos
//el layoutResId y datosList

class Datos2Adapter(val mctx2:Context, val layoutResId2:Int, val datosList2:List<BaseDeDatos>)
    :ArrayAdapter<BaseDeDatos>(mctx2,layoutResId2,datosList2){

    //Llamamos a la funcion getView para inflar el layout
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val inflarLayout : LayoutInflater = LayoutInflater.from(mctx2)
        val view2:View = inflarLayout.inflate(layoutResId2,null)

        val menu2 = view2.tvMenuP
        val cant2= view2.tvCantP
        val tel2= view2.tv_TelC
        val nombreC = view2.tv_NombreC
        val llevar2= view2.tvLlevarP
        val btnDelete2 = view2.btnEliminarP

        val datos2 = datosList2[position]
        menu2.text = datos2.menu
        nombreC.text = datos2.cliente
        tel2.text = datos2.telefono
        cant2.text = datos2.cant
        llevar2.text = datos2.llevar




        btnDelete2.setOnClickListener {
            deleteInfo2(datos2)
        }


        return view2
    }

    private fun deleteInfo2(datos2: BaseDeDatos) {

        val myBaseDeDatos = FirebaseDatabase.getInstance().getReference("Confirmado")
        myBaseDeDatos.child(datos2.id).removeValue()

    }
}