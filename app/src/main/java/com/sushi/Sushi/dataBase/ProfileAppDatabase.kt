package com.sushi.Sushi.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ProfileDataModel::class], version = 1)

abstract class ProfileAppDatabase : RoomDatabase() {

    abstract fun profileDataDao() : ProfileDataDao

}
