package com.sushi.Sushi.models

import android.net.Uri

class MenuModel (var Cost: Long, var NewCost: Long, var Description: String?, var Name: String?, var NameENG: String?, var Picture: String?, var CountDialog: Long, var PictureLoad: String?, var PictureForLoad: Uri?, var Wt: Long, var Delivery: Long, var Stop: Long) {

    constructor(): this(Cost = Long.MAX_VALUE, NewCost = Long.MAX_VALUE, Description = String(), Name = String(), NameENG = String(), Picture = String(),CountDialog = Long.MAX_VALUE, PictureLoad = String() , PictureForLoad = null, Wt = Long.MAX_VALUE, Delivery = Long.MAX_VALUE, Stop = Long.MAX_VALUE)

//    override val entries: Set<Map.Entry<String, MenuModel>>
//        get() = TODO(reason = "Not yet implemented")
//    override val keys: Set<String>
//        get() = TODO(reason = "Not yet implemented")
//    override val size: Int
//        get() = TODO("Not yet implemented")
//    override val values: Collection<MenuModel>
//        get() = TODO("Not yet implemented")
//
//    override fun containsKey(key: String): Boolean {
//        TODO("Not yet implemented")
//    }
//
//    override fun containsValue(value: MenuModel): Boolean {
//        TODO("Not yet implemented")
//    }
//
//    override fun get(key: String): MenuModel? {
//        TODO("Not yet implemented")
//    }
//
//    override fun isEmpty(): Boolean {
//        TODO("Not yet implemented")
//    }

}
