package com.sushi.Sushi.singleton

import android.util.Log
import android.widget.Toast
import com.sushi.Sushi.models.MenuModel

object MenuSingleton {
    lateinit var menu: MenuModel
    var position : Int = 0

    fun addPosition(pos : MenuModel) {
        menu = pos
    }

    fun addPos(po : Int){
        position = po

    }

    fun show(){
//        Log.d("YYY","колличество = " + menu.Count)
    }

    
}