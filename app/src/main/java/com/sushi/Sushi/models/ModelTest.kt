package com.sushi.Sushi.models

class ModelTest(var Cost: Long,var Description: String?, var Name: String?, var Picture: String?,var CountDialog: Long, var PictureSale: String?) {


    constructor() : this (Cost = Long.MAX_VALUE, Description = String(), Name = String(),Picture = String(),CountDialog = Long.MAX_VALUE,PictureSale = String())

}