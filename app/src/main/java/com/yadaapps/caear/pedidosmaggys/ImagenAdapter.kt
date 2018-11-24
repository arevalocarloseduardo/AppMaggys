package com.yadaapps.caear.pedidosmaggys

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.menus.view.*

class ImagenAdapter(val mctx3: Context, val layoutResId3:Int, val datosList3:List<Upload>)
    : ArrayAdapter<Upload>(mctx3,layoutResId3,datosList3){

    //Llamamos a la funcion getView para inflar el layout
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val inflarLayout : LayoutInflater = LayoutInflater.from(mctx3)
        val view3: View = inflarLayout.inflate(layoutResId3,null)
        val referenciaPedidos = FirebaseDatabase.getInstance().getReference("Pedidos")
        val imagen = view3.imagenMenu
        val btn =view3.btn_agregarMenu
        val cant = view3.edCant
        val llevar = view3.cbLLevarMenu


        val datos3 = datosList3[position]
        Picasso.get().load(datos3.mimageUrl).into(imagen)
        //menu2.text = datos2.menu

        btn.setOnClickListener {
                if(cant.text.isNotEmpty()){
                    var paraLlevar: String
                    if (llevar.isChecked){
                        paraLlevar="Para LLevar"}
                    else {
                        paraLlevar = ""
                    }
                    var cantidad=cant.text.toString().trim()

                    val heroId = referenciaPedidos.push().key.toString()

                    val hero = BaseDeDatos(heroId,"","Sopa + Arroz con Pollo",paraLlevar,cantidad,"150","")

                    referenciaPedidos.child(heroId).setValue(hero)
                }
                else{
                    Toast.makeText(view3.context,"el pedido esta vacio, ingrese una cantidad",Toast.LENGTH_LONG).show()
                }
        }
        return view3
    }

    private fun deleteInfo3(datos3: Upload) {

        val myBaseDeDatos = FirebaseDatabase.getInstance().getReference("Confirmado")
        myBaseDeDatos.child(datos3.uid).removeValue()

    }
}