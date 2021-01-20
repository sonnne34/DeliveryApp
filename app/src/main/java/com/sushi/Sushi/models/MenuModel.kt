package com.sushi.Sushi.models

class MenuModel (var Cost: Long,var Description: String?, var Name: String?, var Picture: String?,var CountDialog: Long, var PictureSale: String?) {

    constructor(): this(Cost = Long.MAX_VALUE, Description = String(), Name = String(),Picture = String(),CountDialog = Long.MAX_VALUE,PictureSale = String())

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
