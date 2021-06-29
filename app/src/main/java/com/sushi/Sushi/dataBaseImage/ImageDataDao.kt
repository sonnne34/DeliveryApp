package com.sushi.Sushi.dataBaseProfil

import androidx.room.*


@Dao
interface ImageDataDao {

    @Query("SELECT * FROM imagedatamodel")
    fun getAll(): List<ImageDataModel?>?

    @Insert
    fun insert(imageData: ImageDataModel?)

    @Update
    fun update(imageData: ImageDataModel?)

    @Delete
    fun delete(imageData: ImageDataModel?)

}