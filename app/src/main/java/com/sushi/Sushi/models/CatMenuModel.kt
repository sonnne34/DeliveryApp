package com.sushi.Sushi.models

import java.util.HashMap

class CatMenuModel(var CategoryName : String) {
    constructor(): this(CategoryName = String())

    var isHeader = false
//    var CategoryName: String? = null
    var Items: HashMap<String, MenuModel> = HashMap()


}