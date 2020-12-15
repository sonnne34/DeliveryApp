package com.sushi.Sushi.singleton

object Address {
    var address : String = ""

    fun addAddress(mAddress : String){
        address = mAddress
    }
}