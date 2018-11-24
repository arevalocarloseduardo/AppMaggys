package com.yadaapps.caear.pedidosmaggys.Datos

class DatosUsuario(val uid:String,val nombre: String,val fotoPerfil: String,val edad:String,val correo:String,val telefono:String,val puntos:String,val lvl:String,val direccion:String){
    constructor():this("","","","","","","","",""){
    }
}