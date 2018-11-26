package com.yadaapps.caear.pedidosmaggys.Fragments


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.yadaapps.caear.pedidosmaggys.Datos.DatosPedidos

import com.yadaapps.caear.pedidosmaggys.Datos.DatosUsuario
import com.yadaapps.caear.pedidosmaggys.R
import com.yadaapps.caear.pedidosmaggys.RegisterActivity
import kotlinx.android.synthetic.main.fragment_pedidos.view.*
import kotlinx.android.synthetic.main.fragment_perfil.view.*


class PerfilFragment : Fragment() {

    lateinit var referenciaUsuarios : DatabaseReference
    lateinit var usuarioLista:MutableList<DatosUsuario>
    lateinit var puntos :TextView
    lateinit var nombre :TextView
    lateinit var correo :TextView
    lateinit var telefono :TextView
    lateinit var direccion :TextView

    lateinit var btnConfigurar:TextView

    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verifivarAuth()

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_perfil, container, false)


        referenciaUsuarios = FirebaseDatabase.getInstance().getReference("Users")
        usuarioLista= mutableListOf()
        puntos=v.tvPerfilPuntos
        nombre=v.tvPerfilName
        correo=v.tvPerfilCorreo
        telefono=v.tvPerfilTelefono
        direccion=v.tvPerfilDireccion


        traerDatos(referenciaUsuarios)
        btnConfigurar

        return v
    }


    private fun traerDatos(referencia: DatabaseReference) {
        referencia.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    for (h in p0.children)
                    {
                        val uid = FirebaseAuth.getInstance().uid
                        val idClienteRegistrado = h.getValue(DatosUsuario::class.java)?.uid
                        val hero = h.getValue(DatosUsuario::class.java)
                        if (uid==idClienteRegistrado){
                            usuarioLista.add(hero!!)
                        }
                        for (h in usuarioLista)
                        {
                            puntos.text=h.puntos
                            nombre.text=h.nombre
                            correo.text=h.correo
                            telefono.text=h.telefono
                            direccion.text=h.direccion
                        }
                    }
                }
            }
        })
    }
    private fun verifivarAuth() {
        val uid = FirebaseAuth.getInstance().uid
        if(uid==null){
            val intent = Intent(activity, RegisterActivity::class.java)
            intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
    companion object {
    fun newInstance(): PerfilFragment{
        val fragment=PerfilFragment()
        return fragment
    }
}
}
