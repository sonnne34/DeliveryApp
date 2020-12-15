package com.sushi.Sushi.singleton

import android.util.Log
import com.sushi.Sushi.models.MenuModel

object BasketSingleton {
    var basketItem : ArrayList<MenuModel> = ArrayList()

    fun addBasket(item : MenuModel){


        var boolean = true

        for(i in basketItem){
            if(i.Name == item.Name){
                boolean = false
            }


        }

        if (boolean == true){
            basketItem.add(item)
        }



    }



    fun showBasket(){
        for(i in basketItem){
            var yy = i.Name

            Log.d("Basket", "Items = "  + yy)
        }

    }
}