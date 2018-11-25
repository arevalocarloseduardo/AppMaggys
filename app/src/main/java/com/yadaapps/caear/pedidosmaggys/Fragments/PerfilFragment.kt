package com.yadaapps.caear.pedidosmaggys.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.yadaapps.caear.pedidosmaggys.Datos.DatosPedidos
import com.yadaapps.caear.pedidosmaggys.R
import kotlinx.android.synthetic.main.fragment_pedir.view.*


class PerfilFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_perfil, container, false)
        return v
    }
companion object {
    fun newInstance(): PerfilFragment{
        val fragment=PerfilFragment()
        return fragment
    }
}
}
