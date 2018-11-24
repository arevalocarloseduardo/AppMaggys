package com.yadaapps.caear.pedidosmaggys.Datos

class BaseDeDatos(val id: String,val cliente: String,val menu: String,val llevar : String,val cant : String,val precio:String,val telefono:String,val estado:String){
    constructor():this("","","","","","","",""){
    }
}