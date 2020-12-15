package com.sushi.Sushi.models

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class CatMenuModel (var CategoryName:String, var Items : HashMap<String,MenuModel> = HashMap()){

    constructor() : this(CategoryName = String(), Items = HashMap())
}