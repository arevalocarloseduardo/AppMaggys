package com.yadaapps.caear.pedidosmaggys.Fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.yadaapps.caear.pedidosmaggys.*
import com.yadaapps.caear.pedidosmaggys.Fragments.AdaptadoresFragments.AdapterFragment
import com.yadaapps.caear.pedidosmaggys.R
import kotlinx.android.synthetic.main.fragment_pedir.*
import kotlinx.android.synthetic.main.fragment_pedir.view.*

class PedirFragment : Fragment() {

    lateinit var referenciaImagenes : DatabaseReference
    lateinit var referenciaPedidos : DatabaseReference
    lateinit var referenciaConfirmados : DatabaseReference

    lateinit var pedidosList:MutableList<BaseDeDatos>
    lateinit var imagenList:MutableList<Upload>

    lateinit var recyclerImagenes: RecyclerView
    lateinit var recyclerPedidos: RecyclerView
    lateinit var category: String
    internal lateinit var spiner:Spinner
    internal var listaSpin= arrayOf("Menus","Postres","Bebidas","Platos Frios","Platos a la carta","Combos","Licuados")

    var precioTotal=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val uid = FirebaseAuth.getInstance().uid
        if(uid==null){
            val intent = Intent(activity, RegisterActivity::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        spiner=spinner
        val arrayAdapter = ArrayAdapter(activity, android.R.layout.simple_spinner_dropdown_item, listaSpin)
        spiner?.adapter = arrayAdapter

        spiner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                category = "suarios"
                when (parent?.getItemAtPosition(position)) {
                    "Menus" -> { pasarCategoria("usuarios")
                    }
                    "Postres" -> { pasarCategoria("postres")
                    }
                    "Bebidas" -> { pasarCategoria("bebidas")
                    }
                    "Platos Frios" -> {pasarCategoria("platosFrios")
                    }
                    "Platos a la carta" -> { pasarCategoria("platosCarta")
                    }
                    "Combos" -> { pasarCategoria("combos")
                    }
                    "Licuados" -> { pasarCategoria("licuados")
                    }
                }
            }

        }
    }

    private fun pasarCategoria(s: String) {

        imagenList= mutableListOf()
        referenciaImagenes = FirebaseDatabase.getInstance().getReference(s)
        recyclerImagenes.layoutManager=LinearLayoutManager(activity,LinearLayout.HORIZONTAL,false)
        val miAdapter= RecyclerAdapter(imagenList)
        recyclerImagenes.adapter =miAdapter
        referenciaImagenes.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                imagenList.clear()
                referenciaImagenes = FirebaseDatabase.getInstance().getReference(category)
                if(p0.exists()){
                    for (h in p0.children){
                        val uplo = h.getValue(Upload::class.java)
                        imagenList.add(uplo!!)
                        recyclerImagenes.adapter=miAdapter
                    }
                    for (h in imagenList){
                    }
                }else{
                    referenciaImagenes = FirebaseDatabase.getInstance().getReference(category)
                    imagenList.clear()
                }
            }
        })

        referenciaImagenes = FirebaseDatabase.getInstance().getReference(s)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val v= inflater.inflate(R.layout.fragment_pedir, container, false)
        val uid = FirebaseAuth.getInstance().uid
        if(uid==null){
            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        category="usuarios"
        referenciaImagenes = FirebaseDatabase.getInstance().getReference(category)
    referenciaPedidos = FirebaseDatabase.getInstance().getReference("Pedidos")
    referenciaConfirmados = FirebaseDatabase.getInstance().getReference("Confirmados")
    imagenList= mutableListOf()
    pedidosList= mutableListOf()
    recyclerPedidos=v.listaView
    recyclerImagenes=v.rv_menus

    val precio =v.tvPrecio
    precioTotal = 0


    recyclerImagenes.layoutManager=LinearLayoutManager(activity,LinearLayout.HORIZONTAL,false)
    val miAdapter= RecyclerAdapter(imagenList)
    recyclerImagenes.adapter =miAdapter

    recyclerPedidos.layoutManager=LinearLayoutManager(activity,LinearLayout.VERTICAL,false)
    val mi2Adapter= AdapterFragment(pedidosList)
    recyclerPedidos.adapter =mi2Adapter
//******************************Pasamos todos los atributos de la base de datos a la lista de imagenes******************


//*******************************Pasamos todos los atributos de la base de datos a la lista de pedidos***********************************************
    referenciaPedidos.addValueEventListener(object :ValueEventListener{
        override fun onCancelled(p0: DatabaseError) {
        }
        override fun onDataChange(p0: DataSnapshot) {
            if(p0.exists()){
                precioTotal=0
                pedidosList.clear()
                for (h in p0.children){
                    val hero = h.getValue(BaseDeDatos::class.java)

                    pedidosList.add(hero!!)
                }
                recyclerPedidos.adapter=mi2Adapter
                for (h in pedidosList){
                    precioTotal = precioTotal + (h.cant.toInt() * h.precio.toInt())
                }
                precio.text=precioTotal.toString()
            }
            else{
                pedidosList.clear()
                recyclerPedidos.adapter=mi2Adapter
                precioTotal = 0
                precio.text=precioTotal.toString()
            }
        }
    }
    )


    val btn =v.btnEnviar
    btn.setOnClickListener {
        val frag2 = PedidosFragment()

        fragmentManager
            ?.beginTransaction()
            ?.replace(R.id.contenedorFragments,frag2)
            ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ?.commit()

        for (h in pedidosList){
            val heroId =  FirebaseAuth.getInstance().uid.toString()
            val heros = referenciaPedidos.push().key.toString()
            val hero = BaseDeDatos(heroId,"",h.menu,h.llevar,h.cant,h.precio,heros)
            referenciaConfirmados.child(heros).setValue(hero)
        }
        referenciaPedidos.removeValue()
    }
    return v
}
    companion object {
        fun newInstance(): PedirFragment{
            val fragment=PedirFragment()
            return fragment
        }
    }
}

