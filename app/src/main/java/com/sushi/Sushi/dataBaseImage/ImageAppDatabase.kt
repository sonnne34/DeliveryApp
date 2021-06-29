package com.sushi.Sushi.dataBaseProfil

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ImageDataModel::class], version = 1)

abstract class ImageAppDatabase : RoomDatabase() {

    abstract fun profileDataDao() : ImageDataDao

}
