package com.yadaapps.caear.pedidosmaggys.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.firebase.database.*
import com.yadaapps.caear.pedidosmaggys.BaseDeDatos
import com.yadaapps.caear.pedidosmaggys.Fragments.AdaptadoresFragments.AdapterFragment
import com.yadaapps.caear.pedidosmaggys.Fragments.AdaptadoresFragments.PedidosAdapter
import com.yadaapps.caear.pedidosmaggys.R
import com.yadaapps.caear.pedidosmaggys.Upload
import kotlinx.android.synthetic.main.fragment_pedidos.view.*
import kotlinx.android.synthetic.main.fragment_pedir.view.*
import kotlinx.android.synthetic.main.menus.view.*
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference



class PedidosFragment : Fragment() {
    lateinit var referenciaConfirmados1 : DatabaseReference
    lateinit var pedidosList1:MutableList<BaseDeDatos>
    lateinit var recyclerPedidos1: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val v=inflater.inflate(R.layout.fragment_pedidos, container, false)
        referenciaConfirmados1 = FirebaseDatabase.getInstance().getReference("Confirmados")
        pedidosList1= mutableListOf()
        recyclerPedidos1=v.recyPedidos
        val nombreDeCliente="Carlos"
        val btn_agregar=v.btnFragment



        recyclerPedidos1.layoutManager= LinearLayoutManager(activity, LinearLayout.VERTICAL,false)
        val mi2Adapter= PedidosAdapter(pedidosList1)
        recyclerPedidos1.adapter =mi2Adapter

        referenciaConfirmados1.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot)
            {val progres =v.progressBarPedidos
                if(p0.exists())
                {
                    progres.visibility = View.VISIBLE
                    recyclerPedidos1.visibility = View.INVISIBLE
                    pedidosList1.clear()
                    //referenciaConfirmados1.setValue(ServerValue.TIMESTAMP) para pasar la hora del servidorDatabaseReference ref = FirebaseDatabase.getInstance().getReference(); String key = ref.push().getKey(); // this will create a new unique key Map<String, Object> value = new HashMap<>(); value.put("name", "shesh"); value.put("address", "lucknow"); value.put("timestamp", ServerValue.TIMESTAMP); ref.child(key).setValue(value);
                   // val ref = FirebaseDatabase.getInstance().reference
                   // val key = ref.push()
                       // .key // this will create a new unique key Map<String, Object> value = new HashMap<>(); value.put("name", "shesh"); value.put("address", "lucknow"); value.put("timestamp", ServerValue.TIMESTAMP); ref.child(key).setValue(value);
                    for (h in p0.children)
                    {
                        val cliente = h.getValue(BaseDeDatos::class.java)?.cliente
                        val hero = h.getValue(BaseDeDatos::class.java)
                        if (cliente==nombreDeCliente){
                            pedidosList1.add(hero!!)
                            progres.visibility = View.INVISIBLE
                            recyclerPedidos1.visibility = View.VISIBLE
                            recyclerPedidos1.adapter=mi2Adapter
                        }
                    }
                }else pedidosList1.clear()
                if (pedidosList1.isEmpty()){
                    val btn = v.btnFragment
                    val txt = v.tvError
                    btn.visibility = View.VISIBLE
                    txt.visibility = View.VISIBLE
                    progres.visibility = View.INVISIBLE
                    recyclerPedidos1.visibility = View.INVISIBLE
                }
            }
        })
        btn_agregar.setOnClickListener {
            val frag2 = PedirFragment()
            fragmentManager
                ?.beginTransaction()
                ?.replace(R.id.contenedorFragments,frag2)
                ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                ?.commit()
        }

        return v
    }
    companion object {
        fun newInstance(): PedidosFragment{
            val fragment=PedidosFragment()
            return fragment
        }
    }
}