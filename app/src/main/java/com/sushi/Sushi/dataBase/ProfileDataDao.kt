package com.sushi.Sushi.dataBase

import androidx.room.*


@Dao
interface ProfileDataDao {

    @Query("SELECT * FROM profiledata")
    fun getAll(): List<ProfileData?>?

    @Query("SELECT * FROM profiledata WHERE id = :id")
    fun getById(id: Long): ProfileData?

    @Insert
    fun insert(employee: ProfileData?)

    @Update
    fun update(employee: ProfileData?)

    @Delete
    fun delete(employee: ProfileData?)

}